# Perform's Dijkstra's algorithm on a weighted directed (or undirected) graph

import dumbQueue

G1 = {'A':{'B':1, 'C':4},
      'B':{'A':1, 'D':1},
      'C':{'A':4, 'D':1, 'E':1},
      'D':{'B':1, 'C':1, 'F':5},
      'E':{'C':1, 'F':1},
      'F':{'D':5, 'E':1}}

# Dijkstra's algorithm
def Dijkstra(g, src, dest):
    q = dumbQueue.dumbQueue()
    q.insertQ(src, 0)
    finished = {src:0}
    maybeDist = {src:0}
    
    while(q.len() > 0):
        v = q.deleteMinQ()
        print v, finished, maybeDist
        finished[v] = maybeDist[v]
        for w in g[v]:
            if w not in finished.keys():
                md = finished[v] + g[v][w]
                if w not in maybeDist.keys():
                    maybeDist[w] = md
                    q.insertQ(w, md)
                else:
                    if md < maybeDist[w]:
                        maybeDist[w] = md
                        q.decreaseKeyQ(w, md)
    print finished[dest]

Dijkstra(G1, 'A', 'F')
