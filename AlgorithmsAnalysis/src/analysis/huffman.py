# Huffman encoding
import dumbQueue as dQ

f = open('bible.txt', 'r')
c = 1
wordcounts = {}
q = dQ.dumbQueue();

for line in f:
    line = line.lower().translate(None, '.,?!;:')
    x = line.split()
    for w in x:
        if w in wordcounts:
            wordcounts[w] += 1
        else:
            wordcounts[w] = 1

    c += 1
    if c > 100000000:
        break
    
for w in wordcounts:
    q.insertQ(w, wordcounts[w])



while(q.len() > 0):
    x = q.deleteMinQ()
    print x, wordcounts[x]
