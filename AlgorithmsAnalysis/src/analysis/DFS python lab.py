"""
Design a graph data structure for use in Python

We'll use a dictionary. (Same as a hash set)

The vertices of the graph will be the dictionary keys
The "edges" will be implied, since the value associated with each
vertex of the graph will be the list of edges adjacent to that vertex.
"""
"""
A-------B
|\      |    G
| \     |   / \
|  \    |  /   \
C---D---E F-----H

encodes as {'A':['C', 'D', 'B'],
            'B':['A', 'E'],
            'C':['A', 'D'],
            'D':['A', 'C', 'E'],
            'E':['D', 'B'],
            'F':['G', 'H'],
            'G':['F', 'H'],
            'H':['F', 'G']}
"""
# g1 is the left component of the figure above
g1 = {'A':['C', 'D', 'B'],
            'B':['A', 'E'],
            'C':['A', 'D'],
            'D':['A', 'C', 'E'],
            'E':['D', 'B']}

# g2 is the whole graph in the figure above
g2 = {'A':['C', 'D', 'B'],
            'F':['G', 'H'],
            'C':['A', 'D'],
            'D':['A', 'C', 'E'],
            'G':['F', 'H'],
            'E':['D', 'B'],
            'B':['A', 'E'],
            'H':['F', 'G']}


WHITE = 0
GRAY = 1
BLACK = 2

# returns the number of vertices in the graph
def numVertices(g):
    return len(g)
assert numVertices(g1) == 5
assert numVertices(g2) == 8

# returns the number of edges in the graph
def numEdges(g):
    sum = 0
    for i in g:
        sum += len(g[i])
    return sum/2
assert numEdges(g1) == 6
assert numEdges(g2) == 9

# returns the largest degree among the vertices of g
# maxDegree(g1) = 3. maxDegree(g2) = 3.
def maxDegree(g): # degree = number of edges at that vertex
    maxNum = 0 
    for i in g:
        if len(g[i]) > maxNum:
            maxNum = len(g[i])
    return maxNum
assert maxDegree(g1) == 3
assert maxDegree(g2) == 3


# Takes a graph (dictionary) g and returns True if
# the graph is connected, False otherwise
# isConnected(g1) = True. isConnected(g2) = False.
# isConnected using DFS
def isConnected(g):
    stack = []
    visited = {}
    for v in g:
        visited[v] = False
    
    # put a vertex onto the stack to start the DFS
    startVertex = g.keys()[0]
    stack.append(startVertex)
    visited[startVertex] = True
    
    # Do the DFS by popping last element off, then adding neighbors
    while len(stack) > 0:
        x = stack.pop()
        # put unvisited neighbors onto stack
        for v in g[x]:
            if not visited[v]:
                # mark v as visited and put on stack
                visited[v] = True
                stack.append(v)
                
    return not False in visited.values()
assert isConnected(g1) == True
assert isConnected(g2) == False
            

# Takes a graph (dictionary) g and returns a list
# of lists. Each list gives the vertices of a
# connected component
# Running time is O(n + E) where n is the number of vertices
#                            and E is the number of edges
def connectedComponentsDFS(g):
    # your work here
    stack = []
    visited = {}
    unvisited = {}
    rv = []
    InnerRv = {}
    for v in g:
        visited[v] = False
    
    # put a vertex onto the stack to start the DFS
    startVertex = g.keys()[0]
    stack.append(startVertex)
    visited[startVertex] = True
    
    # Do the DFS
    while len(stack) > 0:
        x = stack.pop()
        # put unvisited neighbors onto stack
        for v in g[x]:
            if not visited[v]:
                # mark v as visited and put on stack
                visited[v] = True
                stack.append(v)
       
    
    for i in g:
        if not visited[i]:
            unvisited.update({i:g[i]})
            #print visited
        if visited[i]:
            InnerRv.update({i:g[i]})
    rv.append(InnerRv)
    #print InnerRv
    if len(unvisited) > 0:
        print "UV ",unvisited
        rv.append(connectedComponentsDFS(unvisited)[0])

    return rv # two components, both empty. 

a = connectedComponentsDFS(g1)
assert len(a) == 1
assert len(a[0])==5
b = connectedComponentsDFS(g2)
assert len(b) == 2
assert (len(b[0]) == 3 and len(b[1]) == 5) or (len(b[0]) == 5 and len(b[1]) == 3)


#explore DFS algorithm
def exploreDFS(g):
    tree_edges = 0
    back_edges = 0
    forward_edges = 0
    cross_edges = 0

    timer = 1
    startclock = {}
    endclock = {}
    visited = {}

    for u in g:
        visited[u] = WHITE
        startclock[u] = 0
        endclock[u] = 0

    stack = []
    intstack = []
    sv = g.keys()[0]
    stack.append(sv)
    startclock[sv] = timer
    timer++
    
    while len(stack) > 0:
        x = stack.pop()
        
        if len(x) == 0:
            visited[x] = BLACK
            endclock[x] = timer
            timer++
        else:
            for y in x:
                if visited[y] != BLACK:
                    visited[x] = GRAY
                    intstack.append(x)
                else:
                    visited[x] = BLACK
                    endclock[x] = timer
                    timer++
       
def hamiltonianPath(g):
    rv = False
    
    for u in g:
        if bruteForceDFS(g, u):
            rv = True
        else:
            continue
    return rv



def bruteForceDFS(g, v):
    visited = {}
    stack = []

    for u in g:
        visited[g] = False

    stack.append(v)
    visited[v] = True

    while len(stack) > 0:
        x = stack.pop()

colored = {}
globaltimekeeper = 0

def newplainDFS(g):
    for u in g:
        colored[u] = WHITE
    for u in g:
        if colored[u] == WHITE:
            DFS-visit(g, u)


previsit = {}
postvisit = {}
def DFS-visit(g, u)
    
    previsit[u] = ++globaltimekeeper
    colored[u] = GRAY
    for v in u:
        if colored[v] == WHITE:
            DFS-visit(g, v)
    colored[u] = BLACK
    postvisit[u] = ++globaltimekeeper
