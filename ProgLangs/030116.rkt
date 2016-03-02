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
  )

; Core values
(define-type Value
  [numV (n : number)]
  [closV (arg : symbol) (body : ExprC) (e : Env)]
  [boxV (v : Value)])

(define-type FunDefC
  [fdC (name : symbol) (arg  : symbol) (body : ExprC)])

(define-type Binding
  [bind (name : symbol) (val : Value)])
(define-type-alias Env (listof Binding))
(define extend-env cons)
(define empty-env empty)

(define (interp [ a : ExprC ] [ e : Env ] ) : Value
  (type-case ExprC a
    [numC (n) (numV n)]
    [idC (s) (lookup s e)]
    [appC (f a) (local ((define thisFun (interp f e)))
                        (cond
                          [(not (closV? thisFun)) (error 'apply "found a non-function used as a function")]
                          [else (interp (closV-body thisFun) (extend-env (bind (closV-arg thisFun) (interp a e)) (closV-env thisFun)))]))]	
    [plusC (l r) (plusV (interp l e) (interp r e))]
    [multC (l r) (multV (interp l e) (interp r e))]
    [lamC (a b) (closV a b e)]
    [beginC (f s) (begin (interp f e) (interp s e))]))

(define (plusV [ l : Value ] [ r : Value ]) : Value
  (cond
    [(not (numV? l)) (error '+ "first value is not number")]
    [(not (numV? r)) (error '+ "second value is not number")]
    [else (numV (+ (numV-n l) (numV-n r)))]))

(define (multV [ l : Value ] [ r : Value ]) : Value
  (cond
    [(not (numV? l)) (error '+ "first value is not number")]
    [(not (numV? r)) (error '+ "second value is not number")]
    [else (numV (* (numV-n l) (numV-n r)))]))

(define (lookup [ s : symbol ] [ e : Env ]) : Value
  (cond
    [(empty? e) (error s "Symbol not defined")]
    [(symbol=? s (bind-name (first e))) (bind-val (first e))]
    [else (lookup s (rest e))]))

; Surface language
(define-type ArithS
  [numS (n : number)]
  [plusS (l : ArithS) (r : ArithS)]
  [multS (l : ArithS) (r : ArithS)]
  [bminusS (l : ArithS) (r : ArithS)]
  [uminusS (e : ArithS)]
  )

(define (desugar [ as : ArithS ]) : ExprC
  (type-case ArithS as
    [numS (n) (numC n)]
    [plusS (l r) (plusC (desugar l) (desugar r))]
    [multS (l r) (multC (desugar l) (desugar r))]
    [bminusS (l r) (plusC (desugar l) (multC (numC -1) (desugar r)))]
    [uminusS (e) (multC (numC -1) (desugar e))]
    ))


(define (parse [ s : s-expression ]) : ArithS
  (cond
    [(s-exp-number? s) (numS (s-exp->number s))]
    [(s-exp-list? s)
     (let ((sl (s-exp->list s)))
       (case (s-exp->symbol (first sl))
         [(+) (plusS (parse (second sl)) (parse (third sl)))]
         [(*) (multS (parse (second sl)) (parse (third sl)))]
         [(-) (if (= (length (rest sl)) 1)
                  (uminusS (parse (second sl)))
                  (bminusS (parse (second sl)) (parse (third sl))))]
         [else (error 'parse "invalid list input")]))]
    [else (error 'parse "invalid input")]))