"""
A module for doing heap stuff.
"""

class heap:
    def __init__(self):
        self.x = []
        self.indices = {x[v]:v for v in range(0, len(self.x))}
        self.values = {}

    # return the index of the left child of the n'th element
    def left(self, n):
        return 2*n

    def right(self, n):
        return 2*n + 1

    def parent(self, n):
        return n//2

    def heapsize(self):
        return len(self.x) - 1


    # Compare element at index n with its children
    # Swap, if necessary, with smallest, and iterate
    # on location it was swapped to.
    def reheapDown(self, n):
        if n > self.heapsize()//2:
            for v in range(0, len(self.x)):
                self.indices[self.x[v]] = v
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
        for v in range(0, len(self.x)):
                self.indices[self.x[v]] = v

    def insertQ(self, name, value):
        self.x.append(value)
        self.minHeapify()

    def decreaseKeyQ(self, name, newValue):
        self.minHeapify()
        self.x[self.indices[name]] = newValue
        self.minHeapify()
        
        
    #def deleteMinQ():


# When you 'run' this module, the code below will
# automatically run, saving the keystrokes to build
# the heap and construct the array.
h = heap()
h.x = [None, 6, 11, 2, 3, 7, 4, 5, 8, 1]
h.heapsort()
