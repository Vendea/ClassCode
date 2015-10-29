# priority queue implementation that doesn't try to work quickly

class dumbQueue:
    def __init__(self):
        self.x = {}

    def insertQ(self, name, value):
        self.x[name] = value

    def decreaseKeyQ(self, name, newValue):
        self.x[name] = newValue

    def deleteMinQ(self):
        if len(self.x.keys()) == 0:
            return None
        minkey = self.x.keys()[0]
        min = self.x[minkey]
        for k in self.x:
            if self.x[k] < min:
                min = self.x[k]
                minkey = k
        del self.x[minkey]
        return minkey
    
    def len(self):
        return len(self.x)
