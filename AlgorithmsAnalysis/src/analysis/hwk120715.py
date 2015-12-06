#test list
L1 = [5, 15, -30, 10, -5, 40, 10]

#dictionary to keep track of 
#starting point and value
#of each subsequence in a tuple
#mapped to i, the ending point
#of each subsequence
dc = {}

#finds maxvalued subsequence of lis
def subsequence(lis):
    rList = []
    start = 0
    end = 0
    maxSeq = 0
    for i in range(0, len(lis)):
        dc[i] = sbsqs(lis, i)
        if dc[i][1] < 0:
            dc[i] = (i, lis[i])
        if dc[i][1] > maxSeq:
            maxSeq = dc[i][1]
            start = dc[i][0]
            end = i
    for i in range(start, end+1):
        rList.append(lis[i])
    print rList,  "=", maxSeq

def sbsqs(lis, i):
    if i == 0:
        return (0, lis[i])
    if dc[i - 1] < 0:
        return (i, lis[i])
    return (dc[i - 1][0], dc[i - 1][1] + lis[i])

subsequence(L1)
