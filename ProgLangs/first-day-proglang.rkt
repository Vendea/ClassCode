#lang racket

(define GE empty)

(define (eval pt)
  (cond
    [(or (number? pt) (string? pt) (boolean? pt)) pt]
    [(symbol? pt) (symbol-lookup pt)]
    [(symbol=? (first pt) '+) (+ (eval (second pt)) (eval (third pt)))]
    [(symbol=? (first pt) '*) (* (eval (second pt)) (eval (third pt)))]
    [(symbol=? (first pt) '-) (- (eval (second pt)) (eval (third pt)))]
    [(symbol=? (first pt) '/) (/ (eval (second pt)) (eval (third pt)))]
    [(symbol=? (first pt) 'concat) (string-append (eval (second pt)) (eval (third pt)))]
    [(symbol=? (first pt) 'or) (or (eval (second pt)) (eval (third pt)))]
    [(symbol=? (first pt) 'define) (set! GE (cons (list (second pt) (eval (third pt))) GE))]
    ))

(define (symbol-lookup s)
  (local ((define (s-l env)
            (cond
              [(empty? env) (error "not a symbol" s)]
              [(symbol=? (first (first env)) s) (second (first env))]
              [else (s-l (rest env))])))
    (s-l GE)))

(define (repl)
  (begin
    (print (eval (read)))
    (display "\n> ")
    (repl)))