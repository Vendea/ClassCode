fibs = [0, 1]
def fib(n):
    #base cases
    if n == 0:
        return 0
    if n == 1:
        return 1
    return fib(n-1) + fib(n-2)
def fib2(n):
    if len(fibs) > n:
        return fibs[n]
    fibs.append(fib2(n-2) + fib2(n-1))
    return fibs[n]
