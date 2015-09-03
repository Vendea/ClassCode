def Add(m, n):
    if n == 0:
        return m;
    return Add(m+1, n-1);
def Mult(m, n):
    if n == 1:
        return m;
    if n%2 == 1:
        return m + Mult(n>>1, m<<1)
    return Mult(n>>1, m<<1)
