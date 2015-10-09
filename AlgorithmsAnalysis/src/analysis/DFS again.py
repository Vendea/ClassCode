previsit = {}
postvisit = {}
colored = {}
time = 0
WHITE = 0
GRAY = 1
BLACK = 2
tree_edges = 0
forward_edges = 0
back_edges = 0
cross_edges = 0

def exploreDFS(g):
    stack = []
    colors = {}
    sv = g.keys()[0]
    stack.append(sv)
    
    while len(stack) > 0:
        x = stack.pop()
        previsit[x] = ++time
        for v in g[x]:
            if colors[v] == WHITE:
                stack.append(v)
                colors[x] = GRAY                
        colors[x] = BLACK
        postvisit[x] = ++time
        
    for a in previsit:
        for b in postvisit:
            if previsit[a] < previsit[b]:
                if postvisit[b] < postvisit[a]:
                    tree_edges++
                    forward_edges++
                else:
                    cross_edges++
            else:
                back_edges++

    print "Forward and tree: ", forward_edges, ", cross: ", cross_edges, ", back: ", back_edges

def coloringDFS(g func):
    for u in g:
        colored[u] = WHITE
    for u in g:
        if colored[u] == WHITE:
            DFS-color3(g, u)

def DFS-color3(g, u):    
    previsit[u] = ++time
    colored[u] = GRAY
    for v in u:
        if colored[v] == WHITE:
            DFS-color3(g, v)
    colored[u] = BLACK
    postvisit[u] = ++time

def DFS-color2(g, u):    
    previsit[u] = ++time
    colored[u] = GRAY
    for v in u:
        if colored[v] == WHITE:
            DFS-color2(g, v)
    colored[u] = WHITE
    postvisit[u] = ++time

def hamiltonianDFS(g):
    visited = {}
    stack = []

    for u in g:
        findallPaths(g, u)

def findAllPaths(g, u):
    print "null"

#just checks connectedness
def DFSplain(g): 
    visited = {}
    stack = []
    for u in g:
        visited[u] = False
    sv = g.keys()[0]
    stack.append(sv)
    visited[sv] = True
    while len(stack) > 0:
        x = stack.pop()
        for u in g[x]:
            if not visited[u]:
                visited[u] = True
                stack.append(u)
    return not False in visited.values()
    

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
