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
#
# Strategy is to use BFS
#
# isConnected(g1) = True. isConnected(g2) = False.
def isConnected(g):
    queue = []
    visited = {}
    for v in g:
        visited[v] = False
    
    # put a vertex onto the queue to start the BFS
    startVertex = g.keys()[0]
    queue.append(startVertex)
    visited[startVertex] = True
    
    # Do the BFS
    while len(queue) > 0:
        x = queue.pop(0)
        # put unvisited neighbors onto queue
        for v in g[x]:
            if not visited[v]:
                # mark v as visited and put on queue
                visited[v] = True
                queue.append(v)

    # Return True if all vertices have been visited
    # "in" is like "is an element of huh?"
    # "in" asks a question, just like "==" does
    # print visited # for debugging
    return not False in visited.values()
assert isConnected(g1) == True
assert isConnected(g2) == False
            

# Takes a graph (dictionary) g and returns a list
# of lists. Each list gives the vertices of a
# connected component
#
# Strategy is to use BFS
#
# run on g2, it might give [['A', 'C', 'B', 'E', 'D'], ['E','F', 'G']]
#
# Running time is O(n + E) where n is the number of vertices
#                            and E is the number of edges
def connectedComponents(g):
    # your work here
    queue = []
    visited = {}
    unvisited = {}
    rv = []
    InnerRv = {}
    for v in g:
        visited[v] = False
    
    # put a vertex onto the queue to start the BFS
    startVertex = g.keys()[0]
    queue.append(startVertex)
    visited[startVertex] = True
    
    # Do the BFS
    while len(queue) > 0:
        x = queue.pop(0)
        # put unvisited neighbors onto queue
        for v in g[x]:
            if not visited[v]:
                # mark v as visited and put on queue
                visited[v] = True
                queue.append(v)
       
    
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
        rv.append(connectedComponents(unvisited)[0])
    
        

    return rv # two components, both empty. 

a = connectedComponents(g1)
assert len(a) == 1
assert len(a[0])==5
b = connectedComponents(g2)
assert len(b) == 2
assert (len(b[0]) == 3 and len(b[1]) == 5) or (len(b[0]) == 5 and len(b[1]) == 3)

def connectedComponentsDFS(g):
    queue = []
    visited = {}
    unvisited = {}
    rv = []
    InnerRv = {}
    for v in g:
        visited[v] = False
    
    # put a vertex onto the queue to start the BFS
    startVertex = g.keys()[0]
    queue.append(startVertex)
    visited[startVertex] = True
    
    # Do the BFS
    while len(queue) > 0:
        x = queue.pop(0)
        # put unvisited neighbors onto queue
        for v in g[x]:
            if not visited[v]:
                # mark v as visited and put on queue
                visited[v] = True
                queue.append(v)
       
    
    for i in g:
        if not visited[i]:
            unvisited.update({i:g[i]})
            #print visited
        if visited[i]:
            InnerRv.update({i:g[i]})
    rv.append(InnerRv)
    #print InnerRv
    if len(unvisited) > 0:
        print "UV "
        #for v in unvisited:
         #   print v, 
        rv.append(connectedComponents(unvisited)[0])
    
        

    return rv # two components, both empty.
