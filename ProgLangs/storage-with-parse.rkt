#lang plai-typed

; Core language
(define-type ExprC
  [numC (n : number)]
  [idC (s : symbol)]
  [appC (fun : ExprC) (arg : ExprC)]
  [plusC (l : ExprC) (r : ExprC)]
  [multC (l : ExprC) (r : ExprC)]
  [lamC (arg : symbol) (body : ExprC)]
  [beginC (f : ExprC) (s : ExprC)]
  [boxC (v : ExprC)]
  [unboxC (b : ExprC)]
  [setboxC (b : ExprC) (v : ExprC)]
  [commitC]
  [discardC]
  [letC (s : symbol) (v : ExprC) (e : ExprC)]
  [ifC (c : ExprC) (t : ExprC) (f : ExprC)]
  )

; Core values
(define-type Value
  [numV (n : number)]
  [closV (arg : symbol) (body : ExprC) (env : Env)]
  [boxV (l : Location)]
  [voidV])

; Results returned from interpreting
(define-type Result
  [v+s (v : Value) (j : Store) (s : Store)])

; Store definitions
(define-type-alias Location number)
(define-type Storage
  [cell (location : Location) (val : Value)])
(define-type-alias Store (listof Storage))
(define mt-store empty)
(define override-store cons)

; Keep track of the next storage location
(define new-loc
  (let ([n (box 0)])
    (lambda ()
      (begin
        (set-box! n (add1 (unbox n)))
        (unbox n)))))

; Environment definitions
(define-type Binding
  [bind (name : symbol) (val : Location)])
(define-type-alias Env (listof Binding))
(define mt-env empty)
(define extend-env cons)

(define (interp [ a : ExprC ] [ e : Env ] [ j : Store ] [ sto : Store ]) : Result
  (type-case ExprC a
    [numC (n) (v+s (numV n) j sto)]
    [idC (s) (v+s (fetch (lookup s e) j sto) j sto)]
    [appC (f arg) (local ([define thisFun (interp f e j sto)]
                          [define fun (v+s-v thisFun)]
                          [define fjo (v+s-j thisFun)]
                          [define fsto (v+s-s thisFun)])
                    (cond
                      [(not (closV? fun)) (error 'apply "found a non-function used as a function")]
                      [else (local ([define argR (interp arg e fjo fsto)]
                                    [define aarg (v+s-v argR)]
                                    [define ajo (v+s-j argR)]
                                    [define asto (v+s-s argR)]
                                    [define where (new-loc)])
                              (interp (closV-body fun)
                                      (extend-env (bind (closV-arg fun) where) (closV-env fun))
                                      (override-store (cell where aarg) ajo) asto))]))]	
    [plusC (l r) (local ([define lr (interp l e j sto)]
                         [define rr (interp r e (v+s-j lr) (v+s-s lr))])
                   (v+s (plusV (v+s-v lr) (v+s-v rr)) (v+s-j rr) (v+s-s rr)))]
    [multC (l r) (local ([define lr (interp l e j sto)]
                         [define rr (interp r e (v+s-j lr) (v+s-s lr))])
                   (v+s (multV (v+s-v lr) (v+s-v rr)) (v+s-j rr) (v+s-s rr)))]
    [lamC (arg b) (v+s (closV arg b e) j sto)]
    [beginC (f s) (local ([define res (interp f e j sto)])
                    (interp s e (v+s-j res) (v+s-s res)))]
    [boxC (v) (local ([define loc (new-loc)]
                      [define valR (interp v e j sto)])
                (v+s (boxV loc) (override-store (cell loc (v+s-v valR)) (v+s-j valR)) sto))]
    [unboxC (b) (local ([define res (interp b e j sto)])
                  (v+s (fetch (boxV-l (v+s-v res)) (v+s-j res) (v+s-s res)) (v+s-j res) (v+s-s res)))]
    [setboxC (b v) (local ([define br (interp b e j sto)]
                           [define vr (interp v e (v+s-j br) (v+s-s br))])
                     (v+s (voidV) (override-store (cell (boxV-l (v+s-v br)) (v+s-v vr)) (delsto (boxV-l (v+s-v br)) (v+s-j vr))) (v+s-s vr)))]
    [commitC () (v+s (voidV) mt-store (commitfull j sto))]
    [discardC () (v+s (voidV) mt-store sto)]
    [letC (s v exp) (local ([define val (interp v e j sto)]
                            [define where (new-loc)])
                      (interp exp
                              (extend-env (bind s where) e)
                              (override-store (cell where (v+s-v val)) (v+s-j val)) (v+s-s val)))]
    [ifC (c t f) (local ([define condR (interp c e j sto)]
                         [define cv (numV-n (v+s-v condR))])
                   (if (= cv 0)
                       (interp f e (v+s-j condR) (v+s-s condR))
                       (interp t e (v+s-j condR) (v+s-s condR))))]
    ))
  
; commit the journal
(define (commitfull [ j : Store ] [ sto : Store ]) : Store
  (cond 
    [(empty? j) sto]
    [else (commitfull (rest j) (override-store (first j) (delsto (cell-location (first j)) sto)))]))



; Arithmetic Operations
(define (plusV [ l : Value ] [ r : Value ]) : Value
  (cond
    [(not (numV? l)) (error '+ "first value is not number")]
    [(not (numV? r)) (error '+ "second value is not number")]
    [else (numV (+ (numV-n l) (numV-n r)))]))

(define (multV [ l : Value ] [ r : Value ]) : Value
  (cond
    [(not (numV? l)) (error '* "first value is not number")]
    [(not (numV? r)) (error '* "second value is not number")]
    [else (numV (* (numV-n l) (numV-n r)))]))

; Look in environment
(define (lookup [ s : symbol ] [ e : Env ]) : Location
  (cond
    [(empty? e) (error s "Symbol not defined")]
    [(symbol=? s (bind-name (first e))) (bind-val (first e))]
    [else (lookup s (rest e))]))

; Look in store
(define (fetchfull [ l : Location ] [ sto : Store ]) : Value
  (cond
    [(empty? sto) (error 'fetch ": segfault --- location not found")]
    [(= l (cell-location (first sto))) (cell-val (first sto))]
    [else (fetchfull l (rest sto))]))

; Look in store
(define (fetch [ l : Location ] [ j : Store ] [ sto : Store ]) : Value
  (cond
    [(empty? j) (fetchfull l sto)]
    [(= l (cell-location (first j))) (cell-val (first j))]
    [else (fetch l (rest j) sto)]))

; find and delete in store
(define (delsto [ l : Location ] [ sto : Store ]) : Store
  (cond
    [(empty? sto) sto]
    [(= l (cell-location (first sto))) (rest sto)]
    [else (override-store (first sto) (delsto l (rest sto)))]))

; Surface language
(define-type ArithS
  [numS (n : number)]
  [plusS (l : ArithS) (r : ArithS)]
  [multS (l : ArithS) (r : ArithS)]
  [bminusS (l : ArithS) (r : ArithS)]
  [uminusS (e : ArithS)]
  [idS (s : symbol)]
  [lamS (p : symbol) (b : ArithS)]
  [beginS (f : ArithS) (s : ArithS)]
  [boxS (e : ArithS)]
  [unboxS (b : ArithS)]
  [setboxS (b : ArithS) (v : ArithS)]
  [commitS]
  [discardS]
  [appS (l : ArithS) (p : ArithS)]
  [letS (s : symbol) (v : ArithS) (e : ArithS)]
  [ifS (c : ArithS) (t : ArithS) (f : ArithS)]
  )

(define (desugar [ as : ArithS ]) : ExprC
  (type-case ArithS as
    [numS (n) (numC n)]
    [plusS (l r) (plusC (desugar l) (desugar r))]
    [multS (l r) (multC (desugar l) (desugar r))]
    [bminusS (l r) (plusC (desugar l) (multC (numC -1) (desugar r)))]
    [uminusS (e) (multC (numC -1) (desugar e))]
    [idS (s) (idC s)]
    [lamS (p b) (lamC p (desugar b))]
    [beginS (f s) (beginC (desugar f) (desugar s))]
    [boxS (e) (boxC (desugar e))]
    [unboxS (b) (unboxC (desugar b))]
    [setboxS (b v) (setboxC (desugar b) (desugar v))]
    [commitS () (commitC)]
    [discardS () (discardC)]
    [appS (l p) (appC (desugar l) (desugar p))]
    [letS (s v e) (letC s (desugar v) (desugar e))]
    [ifS (c t f) (ifC (desugar c) (desugar t) (desugar f))]
    ))

(define (parse [ s : s-expression ]) : ArithS
  (cond
    [(s-exp-number? s) (numS (s-exp->number s))]
    [(s-exp-symbol? s) (idS (s-exp->symbol s))]
    [(s-exp-list? s)
     (let ((sl (s-exp->list s)))
       (cond
         [(s-exp-symbol? (first sl))
          (case (s-exp->symbol (first sl))
            [(+) (plusS (parse (second sl)) (parse (third sl)))]
            [(*) (multS (parse (second sl)) (parse (third sl)))]
            [(-) (if (= (length (rest sl)) 1)
                     (uminusS (parse (second sl)))
                     (bminusS (parse (second sl)) (parse (third sl))))]
            [(lambda) (lamS (s-exp->symbol (first (s-exp->list (second sl)))) (parse (third sl)))]
            [(begin) (beginS (parse (second sl)) (parse (third sl)))]
            [(box) (boxS (parse (second sl)))]
            [(unbox) (unboxS (parse (second sl)))]
            [(set-box!) (setboxS (parse (second sl)) (parse (third sl)))]
            [(commit) (commitS)]
            [(discard) (discardS)]
            [(let) (local ([define ve (s-exp->list (second sl))])
                     (letS (s-exp->symbol (first ve)) (parse (second ve)) (parse (third sl))))]
            [(if) (ifS (parse (second sl)) (parse (third sl)) (parse (fourth sl)))]
            [else (appS (idS (s-exp->symbol (first sl))) (parse (second sl)))]
            )]
         [(s-exp-list? (first sl)) (appS (parse (first sl)) (parse (second sl)))]
         [else (error 'parse "invalid input")]))]))

(interp (appC
         (lamC 'b
               (beginC
                 (beginC (setboxC (idC 'b) (plusC (numC 1) (unboxC (idC 'b))))
                         (setboxC (idC 'b) (plusC (numC 1) (unboxC (idC 'b)))))
                 (unboxC (idC 'b)))) (boxC (numC 0))) mt-env mt-store mt-store)