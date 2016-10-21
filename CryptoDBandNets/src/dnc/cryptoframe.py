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


#expands dict outside of rsa encode
#n is alSize from findChunkSize
def expandDict(n):
    for i in range(27, n+10):
        edoc[i] = chr(i+70) # start with a, b, c, ...
        code[edoc[i]] = i

# Encodes a phrase p using RSA with encryption key (n, e)
def rsaEncode(p, n, e):
    global code, edoc
    chunkSize, alSize = findChunksize(n)
    print chunkSize, alSize
    
    # Add extra symbols to the encoding alphabet, as needed
    '''for i in range(27, alSize+10):
        edoc[i] = chr(i+70) # start with a, b, c, ...
        code[edoc[i]] = i'''
    expandDict(alSize)
    
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


# Takes a message M and a polynomial (bitstring) p and creates the codeword
# x^3 + x + 1 = 0b1011
def makeCodeword(M, p):
    d = p << (M.bit_length() - 1)
    M = M << (p.bit_length() - 1)
    Morig = M

    while M.bit_length() >= p.bit_length():
        if M.bit_length() == d.bit_length():
            M = M ^ d
        d >>= 1
        
    print bin(Morig | M)
    return Morig | M


# Divides the polynomial (bitstring) M by the polynomial (bitstring) p
# and produces the remainder (as a bitstring)
def findRemainder(M, p):
    if M.bit_length() < p.bit_length(): return M
    d = p << (M.bit_length() - p.bit_length())
    
    while M.bit_length() >= p.bit_length():
        if M.bit_length() == d.bit_length():
            M = M ^ d
        d >>= 1
        
    return M


# Converts one of our ciphertexts to a bitstring, with error correction bits added
# M is the text, packetSize is the network packet size, and p is the polynomial
def createECCtext(M, packetSize, p):
    blockSize = packetSize - p.bit_length() + 1 # Message chunk size
    bitstring = 1
    # Create the bit string. It will have a leading 1 which is not really part of the encoding
    while len(M) > 0:
        bitstring = (bitstring << 6) + code[M[0]]
        M = M[1:]
        
    # pad the bit string so that it's length is a multiple of blockSize
    numPadBits = (blockSize - ((bitstring.bit_length() - 1) % blockSize)) % blockSize
    bitstring <<= numPadBits
   
    # encode one block at a time to create the encoded bitstring
    eccBitstring = 1
    bitmask = (1 << blockSize) - 1
    bitmask <<= (bitstring.bit_length() - bitmask.bit_length() - 1)
    bitstring -= (1 << (bitstring.bit_length() - 1)) # get rid of the leading 1
    while bitmask > 0:
        block = (bitstring & bitmask) >> (bitmask.bit_length() - blockSize)
        block <<= (p.bit_length() - 1) # shift to make room to append check bits
        rem = findRemainder(block, p)  # Find the check bits
        block |= rem                   # tack on the remainder (check bits)
        eccBitstring = (eccBitstring << packetSize) + block
        bitmask >>= blockSize             # move the mast one block to the right

    print "E:", bin(eccBitstring)
 
# M is the encoded message, packetSize, d is the degree of the lcm polynomial
def decodeECCtext(M, packetSize, d):
    retString = ""
    M = M[3:]
    Mp = ""
    while len(M) > 0:
        Mp = Mp + M[0:packetSize-d]
        while len(Mp) > 5:
            let = Mp[0:6]
            val = int(let ,2)
            retString = retString + edoc[val]
            Mp = Mp[6:]
        M = M[packetSize:]
    return retString

def decodeErrsECCtext(M, packetSize, d, l):
    babyMp = ""
    retString = ""
    M = M[3:]
    Mp = ""
    while len(M) > 0:
        Mp = M[0:packetSize-d]
        Mpi = int(Mp, 2)
        check = int(M[packetSize-d:packetSize], 2)
        print Mpi
        print check
        print findRemainder(Mpi << d, int(l, 2))
        if findRemainder(Mpi << d, int(l, 2)) != check:
            for i in range(len(Mp)):
                istr = Mpi ^ (1 << i)
                print "on i", i
                for j in range(i, len(Mp)):
                    jstr = istr ^ (1 << j)
                    for k in range(j, len(Mp)):
                        kstr = jstr ^ (1 << k)
                        rem = findRemainder(kstr << d, int(l, 2))
                        if rem == check:
                            print rem
                            print check
                            Mpi = kstr
                            Mp = bin(Mpi)[2:]
                            break
                    if findRemainder(Mpi << d, int(l, 2)) == check:
                        break
                if findRemainder(Mpi << d, int(l, 2)) == check:
                    break

        print "getting here"
        Mp = babyMp + Mp
        print Mp
        while len(Mp) > 5:
            let = Mp[0:6]
            val = int(let, 2)
            retString = retString + edoc[val]
            Mp = Mp[6:]

        babyMp = Mp
        print babyMp
        print retString
        M = M[packetSize:]
    return retString

expandDict(60)    
    
