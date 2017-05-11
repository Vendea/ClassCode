from sys import *

cases = int(stdin.readline().strip())
def det(x, y):
    return x[0]*y[1] - x[1]*y[0]

for i in range(cases):
    vertices = map(int, stdin.readline().split())
    area = 0
    first = (vertices[1], vertices[2])
    v2 = (0,0)
    for i in range(0, vertices[0]-1):
        v1 = (vertices[2*i+1], vertices[2*(i+1)])
        v2 = (vertices[2*i+1 + 2], vertices[2*(i+1) + 2])
        area += det(v1, v2)
    area += det(v2, first)

    print area/2.0
    
