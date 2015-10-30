"""
A module for doing heap stuff.
"""
 
class heap:
    def __init__(self):
        self.x = []
        self.names = {}
        self.indices = {}
 
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
            return
        if self.x[self.left(n)] < self.x[n]:
            least = self.left(n)
        else:
            least = n
        if self.right(n) <= self.heapsize():
            if self.x[self.right(n)] < self.x[least]:
                least = self.right(n)
        self.x[n], self.x[least] = self.x[least], self.x[n]
        self.indices[self.x[n]], self.indices[self.x[least]] = n, least
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
                self.indices[self.x[1]] = 1
                self.reheapDown(1)
            else:
                break
        self.x = sorted

    def insertQ(self, name, value):
        self.x.append(value)
        self.names[name] = value
        if value not in self.indices:
            self.indices[value] = self.heapsize()
        self.heapsort()

    def decreaseKeyQ(self, name, newValue):
        oldval = self.names[name]
        if newValue not in self.indices:
            tindex = self.indices[oldval]
            self.x[tindex] = newValue
            self.indices[newValue] = tindex
        self.names[name] = newValue
        if oldval not in self.names.values():
            self.indices.pop(oldval, None)
        self.heapsort()

    def deleteminQ(self):
        if self.heapsize() == 0:
            return None
        self.heapsort()
        rval = self.x[1]
        newtemproot = self.x[self.heapsize()]
        self.x[1] = newtemproot
        self.indices[newtemproot] = 1
        for k in self.names:
            if self.names[k] == rval:
                self.names.pop(k)
        return rval
        
        
        
 
# When you 'run' this module, the code below will
# automatically run, saving the keystrokes to build
# the heap and construct the array.
h = heap()
#h.x = [None, 6, 9, 2, 3, 7, 4, 5, 8, 1]
#h.heapsort()
