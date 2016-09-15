# The code dictionary maps 'A'->0, 'B'->1, ..., 'Z'->25, ' '->26
# The edoc dictionary is the inverse of that map
import math

code = {}
edoc = {}
N = 27
for i in range(N-1):
    code[chr(i+65)] = i
    edoc[i] = chr(i+65)
    code[' '] = N-1
    edoc[N-1] = ' '
 
# This function prints out the frequency of each character in s
def stats(s):
    c = {}
    for i in "ABCDEFGHIJKLMNOPQRSTUVWXYZ ":
        c[i] = 0
    for i in s:
        c[i] += 1
    for i in "ABCDEFGHIJKLMNOPQRSTUVWXYZ ":
        print i,"->",c[i]


# Shifts the string p by b characters to the right.
# capital letters and blanks only
def shiftEncode(p, b):
    crypt = {}  
    for i in range(N):
        crypt[i] = (i+b) % N
    
    c = ""
    for i in p:
        c += edoc[crypt[code[i]]]
    return c


# Multiplies by a and shifts by b characters to the right.
# capital letters and blanks only
def affineEncode(p, a, b):
    crypt = {}  
    for i in range(N):
        crypt[i] = (a*i+b) % N
    
    c = ""
    for i in p:
        c += edoc[crypt[code[i]]]
    return c

def findChunksize(n):
    # Largest size C such that 27^C < n
    chunkSize = int(math.floor(math.log(n)/math.log(27)))
    # Smallest alphabet size A such that A^C > n
    alSize = int(math.ceil(2**(math.log(n, 2)/chunkSize)))

    # We need original text to evaluate to numbers < n still
    # Shrink chunksize until a suitable one is found
    while 27 * (alSize ** (chunkSize - 1)) > n:
        chunkSize -= 1
        alSize = int(math.ceil(2**(math.log(n, 2)/chunkSize)))
        if alSize > 52:
            print "This value of n won't work. Try again."
            sys.exit(-1)
    return chunkSize, alSize


    

# Encodes a phrase p using RSA with encryption key (n, e)
def rsaEncode(p, n, e):
    global code, edoc
    chunkSize, alSize = findChunksize(n)
    print chunkSize, alSize
    
    # Add extra symbols to the encoding alphabet, as needed
    for i in range(27, alSize+10):
        edoc[i] = chr(i+70) # start with a, b, c, ...
        code[edoc[i]] = i
    
    deficit = (chunkSize - (len(p) % chunkSize)) % chunkSize
    p = p + " "*deficit

    crypt = ""
    while len(p) > 0:
        val = 0
        for i in range(chunkSize):
            val = val * alSize + code[p[i]]
        newval = pow(val, e, n)
        chunkCrypt = ""
        for i in range(chunkSize):
            chunkCrypt = edoc[newval % alSize] + chunkCrypt
            newval = newval / alSize
        
        p = p[chunkSize:]
        crypt = crypt + chunkCrypt
    return crypt


