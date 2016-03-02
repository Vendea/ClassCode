#lang plai-typed

(define new-loc-broken
  (lambda ()
    (let ([n (box 0)])
      (begin
        (set-box! n (add1 (unbox n)))
        (unbox n)))))

(define new-loc
  (let ([n (box 0)])
    (lambda ()
      (begin 
        (set-box! n (add1 (unbox n)))
        (unbox n)))))

((lambda (n)
   (begin
     (set-box! n (add1 (unbox n)))
     (unbox n))) (box 0))

(let ([b (box 0)])
  (begin
    (begin (set-box! b (+ 1 (unbox b)))
           (set-box! b (+ 1 (unbox b))))
    (unbox b)))