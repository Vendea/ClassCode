"""
A module for doing heap stuff.
"""

G1 = {'A':{'B':1, 'C':4},
      'B':{'A':1, 'D':1},
      'C':{'A':4, 'D':1, 'E':1},
      'D':{'B':1, 'C':1, 'F':5},
      'E':{'C':1, 'F':1},
      'F':{'D':5, 'E':1}}
test = ('v','u')

class heap:
    def __init__(self, g):
        self.x = [None]
        self.graph = g
        self.values = {}
        self.indices = {}
        self.edges = [v+u for v in g for u in g[v]]
        for v in g:
            self.x.append(v)

    # return the index of the left child of the n'th element
    def left(self, n):
        return 2*n

    def right(self, n):
        return 2*n + 1

    def parent(self, n):
        return n//2

    def heapsize(self):
        return len(self.x) - 1

    def setIndices(self):
        for v in range(0, len(self.x)):
            self.indices[self.x[v]] = v

    def setValues(self):
        for v in self.x:
            self.values[v]

    # Compare element at index n with its children
    # Swap, if necessary, with smallest, and iterate
    # on location it was swapped to.
    def reheapDown(self, n):
        if n > self.heapsize()//2:
            return
        if self.x[self.left(n)] < self.x[n]:
            least = self.left(n)
        else:
            least = n
        if self.right(n) <= self.heapsize():
            if self.x[self.right(n)] < self.x[least]:
                least = self.right(n)
        self.x[n], self.x[least] = self.x[least], self.x[n]
        if least != n:
            self.reheapDown(least)

    def minHeapify(self):
        for i in range(self.heapsize(), 0, -1):
            self.reheapDown(i)

    def heapsort(self):
        sorted = [None]
        self.minHeapify()
        while self.heapsize() > 0:
            sorted.append(self.x[1])
            if self.heapsize() > 1:
                self.x[1] = self.x.pop()
                self.reheapDown(1)
            else:
                break
        self.x = sorted

    def insertQ(self, name, value):
        self.x.append(value)
        self.minHeapify()


# When you 'run' this module, the code below will
# automatically run, saving the keystrokes to build
# the heap and construct the array.
newh = heap(G1)
#newh.x = [None, ['a', 1], ['b', 2], ['c', 3], ['d', 4]]
newh.heapsort()
#h = heap(None)
#h.x = [None, 6, 9, 2, 3, 7, 4, 5, 8, 1]
#h.heapsort()
