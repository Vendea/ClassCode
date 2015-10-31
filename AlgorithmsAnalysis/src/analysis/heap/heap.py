"""
A module for doing heap stuff.
""" 
class heap:
    def __init__(self):
        self.x = [None]
        self.inverse = {}
 
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
        if self.x[self.left(n)][1] < self.x[n][1]:
            least = self.left(n)
        else:
            least = n
        if self.right(n) <= self.heapsize():
            if self.x[self.right(n)][1] < self.x[least][1]:
                least = self.right(n)
        self.x[n], self.x[least] = self.x[least], self.x[n]
        self.inverse[self.x[n][0]] = n
        self.inverse[self.x[least][0]] = least
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
                self.inverse[self.x[1][0]] = 1
                self.reheapDown(1)
            else:
                break
        self.x = sorted
        for i in range(1, self.heapsize()):
            self.inverse[self.x[i][0]] = i
        
    def bubbleup(self, thing, ind):
        p = self.parent(ind)
        while ind != 1 and self.x[p][1] > thing[1]:
            print (ind)
            self.x[ind], self.x[p] = self.x[p], self.x[ind]
            self.inverse[self.x[p][0]] = p
            self.inverse[self.x[ind][0]] = ind
            ind = p
            p = self.parent(ind)
        print (ind, self.x)
        self.x[ind] = thing
        self.inverse[thing[0]] = ind
            
    def insertQ(self, name, value):
        self.inverse[name] = self.heapsize() + 1
        self.x.append((name, value))
        self.bubbleup((name, value), self.heapsize() ) #, self.heapsize()+1)
        
    def decreaseKeyQ(self, name, value):
        i = self.inverse[name]
        self.bubbleup((name, value), i)
        
    def deleteMinQ(self):
        if self.heapsize() == 0:
            return None
        rval = self.x[1]
        self.x[1] = self.x[self.heapsize()]
        self.inverse[self.x[1][0]] = 1
        self.inverse.pop(rval[0])
        self.x.pop()
        self.reheapDown(1)
        return rval[0]
        
 
# When you 'run' this module, the code below will
# automatically run, saving the keystrokes to build
# the heap and construct the array.
h = heap()
h.x = [None, ('a', 6),('b', 9), ('c', 2), ('d', 3), 
       ('e', 7), ('f', 4), ('g', 5), ('h', 8), ('i', 1)]
h.heapsort()
h.deleteMinQ()
