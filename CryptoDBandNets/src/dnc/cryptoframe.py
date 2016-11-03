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

def ECCtexttoletters(corrected):
    retString = ""
    while len(corrected) > 0: # watch this break
        let = corrected[0:6]
        val = int(let ,2)
        retString = retString + edoc[val]
        corrected = corrected[6:]
    for i in range(0, len(retString), (255-24)/6):
        print retString[i:i+(255-24)/6], "******************************************"
    return retString

# M is the encoded message, packetSize, d is the degree of the lcm polynomial
def compressECCtext(M, packetSize, d):
    retString = ""
    M = M[3:]
    Mp = ""
    corrected = ""
    while len(M) > 0:
        Mp = M[0:packetSize-d]
        corrected += Mp
        M = M[packetSize:]
    return corrected

def removeErrs(M, packetSize, d, l):
    M = M[3:]
    corr = ""
    while len(M) > 0:
        Msub = M[0:packetSize-d]
        checkbits = M[packetSize-d:packetSize]
        if findRemainder(int(Msub, 2)<<d, int(l, 2)) == int(checkbits, 2):
            corr += Msub
            M = M[packetSize:]
            print Msub, "no errors"
            continue

        flipone = -99
        fliptwo = -99
        flipthree = -99
        
        # try one
        for i in range(len(Msub)):
            if findRemainder((int(Msub, 2) ^ (1<<i))<<d, int(l, 2)) == int(checkbits, 2):
                flipone = len(Msub)-i-1
                print "1e"
                break
            
        if flipone != -99:
            replace = "0" if Msub[flipone] == "1" else "1"
            corr += (Msub[0:flipone] + replace + Msub[flipone:][1:])
            print (Msub[0:flipone] + replace + Msub[flipone:][1:]), "one error corrected"
            M = M[packetSize:]
            continue

        # try two
        for i in range(len(Msub)):
            print "2e", i
            for j in range(i+1, len(Msub)):
                if findRemainder((int(Msub, 2) ^ ((1<<j)^(1<<i)))<<d, int(l, 2)) == int(checkbits, 2):
                    fliptwo = len(Msub)-i-1
                    flipone = len(Msub)-j-1
                    break
            if flipone != -99 and fliptwo != -99:
                break

        if flipone != -99 and fliptwo != -99:
            replace1 = "0" if Msub[flipone] == "1" else "1"
            replace2 = "0" if Msub[fliptwo] == "1" else "1"
            corr += (Msub[0:flipone] + replace1 + Msub[flipone:fliptwo][1:] + replace2 + Msub[fliptwo:][1:])
            M = M[packetSize:]
            print (Msub[0:flipone] + replace1 + Msub[flipone:fliptwo][1:] + replace2 + Msub[fliptwo:][1:]), "two errors corrected"
            continue
        
        # try three
        for i in range(len(Msub)):
            print "3e", i
            for j in range(i+1, len(Msub)):
                for k in range(j+1, len(Msub)):
                    if findRemainder((int(Msub, 2) ^ ((1<<j)^(1<<i)^(1<<k)))<<d, int(l, 2)) == int(checkbits, 2):
                        flipthree = len(Msub)-i-1
                        fliptwo = len(Msub)-j-1
                        flipone = len(Msub)-k-1
                        break
                if flipone != -99 and fliptwo != -99:
                    break
            if flipone != -99 and fliptwo != -99 and flipthree != -99:
                break

        if flipone != -99 and fliptwo != -99 and flipthree != -99:
            replace1 = "0" if Msub[flipone] == "1" else "1"
            replace2 = "0" if Msub[fliptwo] == "1" else "1"
            replace3 = "0" if Msub[flipthree] == "1" else "1"
            corr += (Msub[0:flipone] + replace1 + Msub[flipone:fliptwo][1:] + replace2 + Msub[fliptwo:flipthree][1:] + replace3 + Msub[flipthree:][1:])
            M = M[packetSize:]
            print (Msub[0:flipone] + replace1 + Msub[flipone:fliptwo][1:] + replace2 + Msub[fliptwo:flipthree][1:] + replace3 + Msub[flipthree:][1:]), "three errors corrected"
            continue
    return corr

def ECCcorrecterrors(M, packetSize, d, l):
    return ECCtexttoletters(removeErrs(M, packetSize, d, l))

def ECCcorrect(M, packetSize, d, l):
    return ECCtexttoletters(compressECCtext(M, packetSize, d))
    

expandDict(60)    
    
