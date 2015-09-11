def EuclidGCD (a, b):
    if (b == 0):
        return a;
    r = a%b;
    return EuclidGCD(b, r);     

import random;
def primality2 (N):
    rval = 1;
    t = 2*N/3;
    while(t>0):
        k = random.randint(1, N-1);
        if((k**(N-1))%N != 1%N):
            rval = 0;
        t-=1;
    if(rval == 1):
        return "yes";
    return "no";
