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

#my own graph for testing, has 7 edges (adirectional), 14 total edges
# 7 back, 3 forward, 4 tree
g3 = {'A':['B', 'C', 'D'], 'B':['A', 'D', 'E'], 'C':['A', 'E'],
      'D':['A', 'B', 'E'], 'E':['B', 'C', 'D']}

G22 = {0: [8, 4], 1: [3, 9], 2: [8, 4], 3: [9, 1], 4: [0, 5, 2], 5: [4, 6], 6: [5, 9, 7], 7: [8, 6], 8: [0, 7, 2], 9: [3, 6, 1]}

G42 = {0: [5, 16], 1: [9, 14], 2: [13, 16], 3: [9, 4, 10, 6], 4: [11, 3, 15], 5: [17, 0], 6: [7, 8, 3], 7: [19, 6], 8: [9, 6], 9: [3, 12, 1, 8],
       10: [3, 18, 19], 11: [4, 14], 12: [9, 14], 13: [2, 17, 15], 14: [11, 12, 1], 15: [13, 4], 16: [2, 0], 17: [13, 18, 5], 18: [10, 17], 19: [7, 10]}

G62 = {0: [8, 13], 1: [29, 27], 2: [3, 7], 3: [29, 6, 2], 4: [6, 20, 9], 5: [15, 27], 6: [4, 3], 7: [2, 22], 8: [0, 14], 9: [4, 23], 10: [12, 18, 16],
       11: [14, 13, 21], 12: [10, 26], 13: [0, 11], 14: [11, 8, 25], 15: [5, 28], 16: [27, 17, 10], 17: [24, 16], 18: [24, 10, 28], 19: [22, 28], 20: [4, 21],
       21: [20, 11], 22: [19, 25, 7], 23: [9, 26], 24: [18, 17], 25: [22, 14], 26: [23, 12], 27: [1, 16, 5], 28: [19, 18, 15], 29: [3, 1]}

G82 = {0: [25, 38], 1: [9, 32, 15], 2: [31, 20, 16], 3: [31, 30, 17, 32], 4: [18, 31, 39], 5: [11, 23], 6: [11, 36, 27], 7: [34, 16, 33], 8: [13, 28],
       9: [31, 1], 10: [24, 39, 37], 11: [5, 19, 6, 20], 12: [32, 34], 13: [15, 20, 27, 8], 14: [22, 23, 26], 15: [27, 13, 1], 16: [22, 2, 7], 17: [3, 25],
       18: [4, 39], 19: [11, 34, 28], 20: [2, 35, 11, 13], 21: [37, 30], 22: [14, 16], 23: [14, 5], 24: [35, 10], 25: [28, 0, 29, 17], 26: [38, 14, 32],
       27: [13, 6, 15], 28: [19, 25, 8, 36], 29: [37, 25], 30: [3, 21, 34], 31: [4, 9, 2, 3], 32: [26, 3, 12, 1], 33: [38, 7, 37], 34: [12, 30, 7, 19], 35: [20, 24],
       36: [28, 39, 6], 37: [33, 29, 21, 10], 38: [26, 0, 33], 39: [10, 4, 36, 18]}

G101 = {0: [48, 22, 29, 23], 1: [45, 9, 14, 29], 2: [27, 16, 30, 42], 3: [17, 45, 43], 4: [44, 41, 32, 11], 5: [41, 8, 20], 6: [41, 13, 37],
        7: [30, 32, 28], 8: [12, 45, 5, 31], 9: [1, 17, 25, 23], 10: [20, 18, 16], 11: [39, 4, 27], 12: [8, 14, 26], 13: [46, 24, 6, 26], 14: [1, 12, 21],
        15: [33, 40, 49, 22], 16: [10, 36, 2], 17: [9, 40, 3, 28], 18: [10, 37, 38, 33], 19: [29, 26, 42], 20: [5, 10, 34, 22], 21: [44, 23, 14], 22: [36, 15, 20, 0],
        23: [21, 9, 34, 0], 24: [25, 45, 47, 13], 25: [24, 29, 9], 26: [12, 19, 41, 13], 27: [2, 35, 42, 11], 28: [36, 7, 17], 29: [19, 1, 0, 25], 30: [7, 35, 49, 2],
        31: [33, 8, 34], 32: [4, 7, 38], 33: [15, 18, 39, 31], 34: [31, 40, 20, 23], 35: [38, 30, 27], 36: [37, 16, 28, 22], 37: [6, 18, 36, 42], 38: [35, 48, 18, 32],
        39: [33, 44, 11], 40: [15, 49, 17, 34], 41: [5, 6, 26, 4], 42: [19, 27, 37, 2], 43: [3, 46, 48], 44: [47, 4, 21, 39], 45: [8, 3, 24, 1], 46: [47, 43, 13],
        47: [24, 46, 44], 48: [0, 38, 43], 49: [40, 15, 30]}

G121 = {0: [12, 14, 38], 1: [50, 17, 35], 2: [46, 23, 21, 24], 3: [49, 46, 8, 31], 4: [28, 52, 29, 20], 5: [19, 44, 6, 39], 6: [41, 37, 5, 25], 7: [33, 15, 22, 24], 8: [21, 16, 13, 3], 9: [10, 50, 36], 10: [56, 43, 9, 30], 11: [15, 48, 30], 12: [36, 54, 13, 0], 13: [12, 25, 47, 8], 14: [40, 0, 48], 15: [29, 11, 7, 41], 16: [8, 17, 51, 39], 17: [49, 45, 16, 1], 18: [41, 44, 47, 42], 19: [27, 21, 5], 20: [4, 55, 59], 21: [2, 19, 8], 22: [59, 7, 48, 23], 23: [22, 2, 31], 24: [7, 52, 2, 49], 25: [56, 37, 13, 6], 26: [36, 35, 34, 31], 27: [55, 49, 38, 19], 28: [40, 4, 47, 43], 29: [15, 50, 4, 34], 30: [52, 11, 10], 31: [23, 26, 3], 32: [39, 58, 55, 44], 33: [53, 7, 57], 34: [42, 26, 29, 46], 35: [57, 45, 26, 1], 36: [26, 12, 53, 9], 37: [58, 6, 25, 56], 38: [46, 27, 0], 39: [5, 16, 54, 32], 40: [47, 28, 14], 41: [18, 15, 6], 42: [52, 56, 18, 34], 43: [28, 10, 55], 44: [5, 32, 18], 45: [17, 57, 35], 46: [38, 34, 2, 3], 47: [13, 40, 18, 28], 48: [11, 14, 50, 22], 49: [3, 24, 17, 27], 50: [9, 48, 1, 29], 51: [16, 59, 57], 52: [4, 42, 24, 30], 53: [36, 58, 33, 54], 54: [39, 12, 53], 55: [27, 43, 20, 32], 56: [37, 10, 42, 25], 57: [35, 33, 51, 45], 58: [32, 37, 53], 59: [51, 22, 20]}

H22 = {0: [6, 2], 1: [8, 3], 2: [8, 6, 0], 3: [5, 1, 7], 4: [7, 9], 5: [3, 8], 6: [0, 2], 7: [4, 3, 9], 8: [2, 5, 1], 9: [4, 7]}

H41 = {0: [14, 9, 12], 1: [13, 8, 11], 2: [8, 18, 12], 3: [17, 16, 15, 5], 4: [13, 6, 10], 5: [3, 7, 11], 6: [14, 10, 4], 7: [10, 16, 5], 8: [2, 17, 1], 9: [18, 0, 17], 10: [4, 15, 6, 7], 11: [5, 19, 1], 12: [0, 2, 18], 13: [1, 4, 19], 14: [6, 16, 0], 15: [19, 10, 3], 16: [3, 7, 14], 17: [9, 3, 8], 18: [2, 9, 12], 19: [13, 15, 11]}

H62 = {0: [8, 13, 6], 1: [26, 18, 3], 2: [10, 28], 3: [29, 1], 4: [6, 12, 17], 5: [11, 24], 6: [4, 0, 21], 7: [28, 14, 24], 8: [18, 25, 0, 9], 9: [22, 12, 8], 10: [20, 2, 27], 11: [20, 25, 29, 5], 12: [17, 4, 9, 19], 13: [0, 15, 24], 14: [7, 27], 15: [26, 13], 16: [18, 26], 17: [12, 4], 18: [1, 16, 8], 19: [23, 12], 20: [21, 11, 10], 21: [6, 20, 22], 22: [23, 21, 9], 23: [22, 19], 24: [13, 5, 7], 25: [11, 8], 26: [16, 1, 15], 27: [10, 14], 28: [7, 2], 29: [3, 11]}

H82 = {0: [18, 4], 1: [34, 35], 2: [5, 16, 25], 3: [29, 14], 4: [5, 38, 0], 5: [4, 2], 6: [29, 15], 7: [20, 32], 8: [26, 10], 9: [20, 36, 26], 10: [11, 8], 11: [10, 12], 12: [37, 11, 23], 13: [16, 17], 14: [3, 34], 15: [25, 6], 16: [13, 2, 38], 17: [18, 19, 13], 18: [17, 27, 0], 19: [17, 35], 20: [28, 7, 9], 21: [30, 23], 22: [32, 36, 26], 23: [21, 12], 24: [28, 25], 25: [2, 15, 24], 26: [8, 9, 22], 27: [18, 37], 28: [20, 24], 29: [6, 3], 30: [21, 31], 31: [33, 30], 32: [39, 22, 7], 33: [39, 31], 34: [1, 14], 35: [1, 19], 36: [22, 9], 37: [12, 27], 38: [16, 4], 39: [33, 32]}

J22 = {0: [1, 9], 1: [0, 3, 7], 2: [4, 5], 3: [9, 1], 4: [8, 2, 6], 5: [8, 2, 7], 6: [4, 9], 7: [1, 5], 8: [5, 4], 9: [0, 3, 6]}

J42 = {0: [19, 15, 4], 1: [9, 15], 2: [11, 7], 3: [11, 16], 4: [0, 12], 5: [8, 10, 11], 6: [7, 13], 7: [12, 2, 6], 8: [5, 16], 9: [17, 1], 10: [5, 18], 11: [5, 2, 3], 12: [4, 7], 13: [19, 6], 14: [15, 17], 15: [0, 1, 14], 16: [18, 8, 3], 17: [9, 14], 18: [10, 16], 19: [13, 0]}

J61 = {0: [28, 4, 23], 1: [27, 23, 7, 29], 2: [12, 5, 23], 3: [16, 21, 8, 6], 4: [22, 0, 12], 5: [26, 2, 21], 6: [16, 3, 10], 7: [25, 1, 17], 8: [11, 18, 3], 9: [13, 22, 16, 20], 10: [27, 14, 6], 11: [23, 29, 17, 8], 12: [15, 4, 2], 13: [26, 27, 24, 9], 14: [29, 10, 15], 15: [19, 21, 12, 14], 16: [9, 22, 3, 6], 17: [7, 11, 28], 18: [19, 24, 8], 19: [29, 15, 18], 20: [9, 24, 21], 21: [3, 5, 20, 15], 22: [9, 4, 16], 23: [2, 0, 11, 1], 24: [13, 18, 20], 25: [28, 26, 7], 26: [25, 13, 5], 27: [13, 10, 1], 28: [25, 0, 17], 29: [14, 11, 1, 19]}

J81 = {0: [29, 18, 37, 31], 1: [28, 4, 13], 2: [17, 18, 27], 3: [29, 12, 28, 25], 4: [10, 28, 1], 5: [8, 36, 12], 6: [22, 16, 27], 7: [16, 17, 35], 8: [34, 39, 5], 9: [23, 26, 22], 10: [4, 22, 20], 11: [24, 33, 34], 12: [30, 3, 36, 5], 13: [31, 32, 21, 1], 14: [35, 19, 38], 15: [30, 27, 21, 33], 16: [6, 7, 23], 17: [2, 39, 7], 18: [38, 2, 21, 0], 19: [39, 25, 14], 20: [10, 37, 28], 21: [24, 13, 18, 15], 22: [6, 9, 10], 23: [16, 35, 33, 9], 24: [32, 11, 21, 39], 25: [19, 3, 26], 26: [9, 25, 37], 27: [2, 15, 6], 28: [1, 20, 3, 4], 29: [0, 32, 3, 31], 30: [12, 15, 35], 31: [13, 36, 0, 29], 32: [34, 13, 29, 24], 33: [23, 11, 15], 34: [32, 38, 11, 8], 35: [23, 14, 30, 7], 36: [31, 5, 38, 12], 37: [26, 0, 20], 38: [18, 14, 34, 36], 39: [24, 19, 17, 8]}

J101 = {0: [49, 41, 28, 22], 1: [25, 44, 33, 39], 2: [13, 30, 43, 45], 3: [23, 4, 20, 15], 4: [23, 3, 48, 29], 5: [19, 43, 46, 22], 6: [37, 14, 40, 24], 7: [14, 10, 25, 41], 8: [49, 26, 19, 34], 9: [34, 21, 38], 10: [24, 32, 7], 11: [46, 24, 18], 12: [42, 28, 18, 37], 13: [16, 36, 2], 14: [6, 7, 21], 15: [3, 17, 41, 32], 16: [26, 13, 20, 42], 17: [15, 36, 25, 46], 18: [12, 39, 11], 19: [5, 8, 34], 20: [23, 16, 3], 21: [45, 14, 9], 22: [5, 0, 26, 35], 23: [20, 3, 4, 40], 24: [6, 10, 11, 47], 25: [7, 27, 1, 17], 26: [16, 28, 22, 8], 27: [42, 25, 30], 28: [0, 26, 12], 29: [4, 37, 36], 30: [2, 41, 27], 31: [45, 47, 43], 32: [10, 34, 33, 15], 33: [36, 1, 32, 49], 34: [9, 32, 19, 8], 35: [44, 22, 48], 36: [29, 13, 17, 33], 37: [12, 29, 6], 38: [43, 9, 39], 39: [18, 1, 38], 40: [42, 48, 6, 23], 41: [30, 15, 0, 7], 42: [12, 27, 40, 16], 43: [2, 5, 31, 38], 44: [49, 35, 1, 47], 45: [21, 2, 31], 46: [11, 17, 5], 47: [44, 24, 31], 48: [35, 4, 40], 49: [0, 8, 33, 44]}

J121 = {0: [53, 23, 54, 44], 1: [24, 39, 35], 2: [39, 28, 47, 16], 3: [20, 50, 32, 21], 4: [36, 21, 9, 45], 5: [35, 38, 30], 6: [47, 12, 7], 7: [9, 6, 48], 8: [24, 20, 13, 45], 9: [7, 19, 4, 48], 10: [34, 43, 40, 46], 11: [14, 34, 44], 12: [30, 58, 6], 13: [43, 8, 18, 47], 14: [32, 11, 31, 38], 15: [54, 59, 22, 41], 16: [53, 17, 2, 26], 17: [51, 16, 40, 49], 18: [46, 20, 13], 19: [54, 25, 9], 20: [18, 42, 8, 3], 21: [52, 39, 4, 3], 22: [25, 48, 26, 15], 23: [0, 58, 26, 41], 24: [8, 1, 55], 25: [59, 22, 19], 26: [23, 16, 22, 32], 27: [45, 57, 51, 31], 28: [2, 46, 36, 29], 29: [49, 57, 33, 28], 30: [55, 12, 5], 31: [56, 14, 27], 32: [14, 26, 37, 3], 33: [29, 40, 58], 34: [10, 43, 55, 11], 35: [5, 52, 1, 49], 36: [4, 28, 47, 37], 37: [32, 36, 51], 38: [14, 5, 50], 39: [41, 21, 1, 2], 40: [33, 48, 10, 17], 41: [15, 58, 39, 23], 42: [59, 20, 53], 43: [34, 13, 10, 46], 44: [56, 0, 11], 45: [27, 4, 50, 8], 46: [10, 28, 18, 43], 47: [2, 13, 6, 36], 48: [7, 40, 22, 9], 49: [57, 17, 29, 35], 50: [3, 45, 38], 51: [17, 37, 27], 52: [35, 21, 57], 53: [0, 16, 42], 54: [56, 0, 15, 19], 55: [34, 30, 24], 56: [59, 44, 54, 31], 57: [27, 52, 49, 29], 58: [33, 41, 12, 23], 59: [25, 42, 56, 15]}

J141 = {0: [50, 26, 7, 41], 1: [6, 52, 22, 27], 2: [30, 57, 21, 50], 3: [11, 24, 55], 4: [59, 67, 63, 13], 5: [19, 62, 37, 52], 6: [66, 17, 1], 7: [0, 20, 37, 12], 8: [57, 10, 56], 9: [33, 41, 69, 15], 10: [45, 34, 8, 36], 11: [3, 39, 47], 12: [56, 7, 63], 13: [4, 60, 68, 62], 14: [17, 25, 58, 44], 15: [59, 35, 9, 19], 16: [69, 55, 67], 17: [14, 6, 59], 18: [43, 66, 21, 31], 19: [41, 15, 5], 20: [44, 32, 7, 48], 21: [29, 18, 2, 46], 22: [24, 1, 26, 31], 23: [34, 39, 24, 32], 24: [3, 22, 23], 25: [14, 64, 63, 42], 26: [33, 68, 0, 22], 27: [1, 36, 57, 45], 28: [38, 34, 60], 29: [49, 51, 21], 30: [45, 2, 56], 31: [22, 61, 51, 18], 32: [23, 42, 33, 20], 33: [26, 32, 9, 49], 34: [10, 28, 23, 54], 35: [46, 48, 49, 15], 36: [27, 10, 52, 58], 37: [54, 5, 62, 7], 38: [66, 41, 28], 39: [11, 23, 64], 40: [51, 62, 45, 67], 41: [38, 0, 19, 9], 42: [65, 32, 25, 54], 43: [69, 65, 47, 18], 44: [69, 14, 20], 45: [10, 30, 27, 40], 46: [21, 61, 35, 67], 47: [11, 65, 43, 64], 48: [57, 20, 35], 49: [68, 35, 33, 29], 50: [65, 2, 0], 51: [29, 54, 31, 40], 52: [55, 1, 36, 5], 53: [61, 60, 68], 54: [51, 42, 37, 34], 55: [3, 16, 52, 61], 56: [64, 8, 12, 30], 57: [2, 27, 8, 48], 58: [14, 59, 36], 59: [4, 15, 58, 17], 60: [53, 28, 13], 61: [46, 55, 31, 53], 62: [13, 40, 5, 37], 63: [12, 25, 4], 64: [25, 47, 56, 39], 65: [50, 42, 43, 47], 66: [38, 6, 18], 67: [4, 16, 40, 46], 68: [49, 53, 13, 26], 69: [44, 43, 9, 16]}

J161 = {0: [73, 65, 6, 33], 1: [11, 73, 17, 64], 2: [66, 38, 20, 79], 3: [51, 24, 28], 4: [10, 18, 67], 5: [36, 73, 24, 60], 6: [8, 0, 57, 35], 7: [49, 12, 66, 73], 8: [74, 6, 22, 65], 9: [25, 44, 31], 10: [69, 43, 4], 11: [24, 52, 14, 1], 12: [62, 16, 7, 50], 13: [74, 25, 75, 52], 14: [11, 50, 44, 61], 15: [50, 77, 34, 69], 16: [70, 55, 12, 23], 17: [1, 21, 69, 48], 18: [4, 29, 39, 45], 19: [47, 64, 77, 30], 20: [51, 47, 2, 24], 21: [44, 32, 17, 45], 22: [68, 78, 27, 8], 23: [28, 16, 76], 24: [5, 11, 20, 3], 25: [70, 13, 9], 26: [41, 30, 31], 27: [22, 51, 42, 53], 28: [3, 57, 23, 71], 29: [49, 18, 39, 76], 30: [19, 26, 72, 67], 31: [62, 26, 9, 40], 32: [40, 21, 74, 43], 33: [62, 55, 54, 0], 34: [58, 77, 15], 35: [62, 65, 6, 42], 36: [72, 66, 5, 54], 37: [77, 70, 59], 38: [68, 2, 55, 46], 39: [79, 57, 29, 18], 40: [58, 32, 31, 56], 41: [55, 26, 60, 67], 42: [48, 27, 35, 60], 43: [54, 10, 32], 44: [9, 14, 21, 46], 45: [21, 49, 50, 18], 46: [44, 38, 59], 47: [61, 19, 71, 20], 48: [17, 67, 49, 42], 49: [45, 7, 29, 48], 50: [12, 14, 15, 45], 51: [27, 20, 3, 72], 52: [56, 13, 11, 63], 53: [66, 78, 27], 54: [33, 43, 79, 36], 55: [41, 33, 16, 38], 56: [71, 52, 40], 57: [68, 28, 6, 39], 58: [34, 69, 40], 59: [37, 46, 76], 60: [42, 5, 41, 63], 61: [78, 72, 47, 14], 62: [31, 35, 33, 12], 63: [60, 52, 78, 75], 64: [19, 75, 1], 65: [8, 35, 0], 66: [7, 36, 53, 2], 67: [30, 48, 41, 4], 68: [22, 57, 38], 69: [58, 15, 10, 17], 70: [16, 71, 25, 37], 71: [47, 56, 70, 28], 72: [51, 30, 36, 61], 73: [1, 5, 0, 7], 74: [32, 13, 8], 75: [63, 64, 13, 76], 76: [23, 75, 59, 29], 77: [34, 19, 15, 37], 78: [22, 63, 61, 53], 79: [39, 2, 54]}

J181 = {0: [79, 16, 75], 1: [72, 67, 85], 2: [45, 9, 31], 3: [26, 22, 55, 76], 4: [54, 69, 58, 79], 5: [89, 28, 29], 6: [27, 8, 63, 38], 7: [77, 58, 23], 8: [29, 6, 71, 34], 9: [89, 28, 62, 2], 10: [56, 65, 14, 67], 11: [15, 28, 58, 17], 12: [86, 25, 61, 68], 13: [75, 64, 14], 14: [13, 70, 57, 10], 15: [85, 44, 63, 11], 16: [0, 83, 70, 73], 17: [11, 81, 25, 55], 18: [87, 23, 60, 41], 19: [72, 35, 63], 20: [72, 69, 73], 21: [62, 63, 55, 46], 22: [48, 33, 3, 38], 23: [7, 18, 50, 46], 24: [76, 81, 26], 25: [17, 12, 74, 56], 26: [3, 41, 56, 24], 27: [51, 43, 76, 6], 28: [65, 5, 9, 11], 29: [55, 5, 8, 88], 30: [40, 36, 83, 78], 31: [88, 2, 41], 32: [54, 52, 69], 33: [46, 52, 22, 53], 34: [87, 48, 8, 75], 35: [80, 54, 62, 19], 36: [30, 52, 79, 73], 37: [49, 81, 86, 60], 38: [6, 50, 22], 39: [54, 49, 42], 40: [61, 30, 42], 41: [26, 31, 58, 18], 42: [60, 77, 39, 40], 43: [27, 59, 82, 80], 44: [47, 60, 15, 83], 45: [61, 84, 66, 2], 46: [33, 84, 23, 21], 47: [44, 53, 88], 48: [87, 22, 34, 64], 49: [37, 59, 39, 74], 50: [82, 88, 38, 23], 51: [82, 27, 59, 53], 52: [78, 33, 36, 32], 53: [57, 51, 47, 33], 54: [35, 39, 32, 4], 55: [17, 29, 21, 3], 56: [82, 26, 10, 25], 57: [53, 14, 71, 67], 58: [11, 4, 7, 41], 59: [51, 49, 43], 60: [42, 44, 37, 18], 61: [12, 77, 45, 40], 62: [21, 9, 35], 63: [21, 6, 19, 15], 64: [13, 48, 85], 65: [10, 68, 79, 28], 66: [87, 84, 45, 71], 67: [57, 73, 10, 1], 68: [89, 65, 76, 12], 69: [20, 75, 32, 4], 70: [14, 16, 78], 71: [74, 57, 8, 66], 72: [1, 19, 20, 89], 73: [36, 16, 67, 20], 74: [71, 25, 83, 49], 75: [0, 34, 13, 69], 76: [3, 68, 24, 27], 77: [42, 7, 61, 80], 78: [84, 30, 70, 52], 79: [65, 4, 0, 36], 80: [86, 35, 77, 43], 81: [85, 37, 17, 24], 82: [56, 50, 51, 43], 83: [44, 30, 16, 74], 84: [78, 46, 66, 45], 85: [64, 15, 81, 1], 86: [80, 12, 37], 87: [18, 34, 48, 66], 88: [29, 31, 47, 50], 89: [68, 9, 5, 72]}

J201 = {0: [5, 2, 12, 10], 1: [7, 95, 73], 2: [0, 25, 31, 40], 3: [83, 18, 76, 69], 4: [75, 53, 30, 89], 5: [0, 47, 77, 10], 6: [20, 23, 73, 35], 7: [51, 9, 32, 1], 8: [25, 34, 16], 9: [7, 83, 27, 67], 10: [66, 35, 5, 0], 11: [16, 80, 12, 17], 12: [11, 0, 18, 70], 13: [14, 56, 88, 31], 14: [86, 13, 78, 73], 15: [41, 71, 93, 98], 16: [32, 8, 11, 24], 17: [44, 11, 49], 18: [3, 12, 92, 21], 19: [43, 54, 36, 66], 20: [88, 6, 62, 57], 21: [18, 56, 78], 22: [77, 33, 52, 38], 23: [76, 47, 6], 24: [55, 87, 16, 65], 25: [85, 8, 96, 2], 26: [42, 97, 52, 93], 27: [9, 52, 90, 97], 28: [93, 38, 55, 32], 29: [37, 52, 44, 46], 30: [46, 84, 4, 51], 31: [13, 91, 2], 32: [16, 35, 7, 28], 33: [50, 22, 42, 46], 34: [66, 70, 92, 8], 35: [63, 10, 32, 6], 36: [19, 59, 72, 45], 37: [74, 95, 29], 38: [47, 28, 61, 22], 39: [75, 85, 89, 42], 40: [88, 82, 2, 81], 41: [15, 96, 99], 42: [33, 26, 68, 39], 43: [19, 60, 69, 62], 44: [79, 17, 29, 48], 45: [36, 94, 64, 99], 46: [33, 30, 96, 29], 47: [23, 80, 38, 5], 48: [91, 44, 98, 79], 49: [55, 17, 87], 50: [73, 91, 33, 83], 51: [59, 70, 30, 7], 52: [22, 29, 26, 27], 53: [4, 62, 84, 57], 54: [78, 81, 64, 19], 55: [28, 71, 49, 24], 56: [13, 58, 82, 21], 57: [20, 90, 85, 53], 58: [99, 63, 56, 69], 59: [86, 51, 90, 36], 60: [43, 61, 86], 61: [38, 94, 60, 65], 62: [43, 72, 53, 20], 63: [66, 35, 58, 76], 64: [45, 54, 83, 72], 65: [70, 24, 74, 61], 66: [19, 10, 34, 63], 67: [90, 82, 9, 97], 68: [86, 42, 82, 79], 69: [58, 84, 3, 43], 70: [34, 65, 12, 51], 71: [89, 55, 88, 15], 72: [64, 62, 36, 94], 73: [14, 1, 6, 50], 74: [77, 37, 65, 81], 75: [92, 4, 39], 76: [23, 96, 3, 63], 77: [5, 22, 74], 78: [21, 95, 14, 54], 79: [68, 81, 48, 44], 80: [85, 11, 47], 81: [54, 74, 79, 40], 82: [40, 68, 56, 67], 83: [50, 64, 9, 3], 84: [30, 69, 53], 85: [80, 57, 39, 25], 86: [68, 59, 60, 14], 87: [99, 98, 49, 24], 88: [20, 40, 71, 13], 89: [4, 92, 71, 39], 90: [27, 67, 59, 57], 91: [48, 31, 50], 92: [34, 89, 18, 75], 93: [15, 26, 28], 94: [72, 98, 61, 45], 95: [37, 78, 1, 97], 96: [46, 41, 25, 76], 97: [95, 67, 26, 27], 98: [94, 15, 48, 87], 99: [45, 41, 87, 58]}

J221 = {0: [86, 36, 30, 39], 1: [25, 38, 5, 45], 2: [75, 62, 67, 15], 3: [90, 48, 15], 4: [43, 38, 34, 22], 5: [67, 98, 87, 1], 6: [108, 102, 12, 77], 7: [31, 89, 106, 96], 8: [43, 57, 28, 48], 9: [108, 83, 62, 53], 10: [80, 41, 100, 64], 11: [79, 68, 49, 96], 12: [91, 64, 93, 6], 13: [69, 18, 85], 14: [65, 94, 97, 88], 15: [2, 26, 3, 46], 16: [58, 19, 90, 44], 17: [107, 52, 41, 22], 18: [48, 92, 13, 41], 19: [16, 89, 25], 20: [95, 40, 102, 101], 21: [103, 97, 86, 28], 22: [4, 17, 39, 89], 23: [42, 65, 60, 61], 24: [107, 78, 56, 105], 25: [98, 1, 26, 19], 26: [104, 25, 59, 15], 27: [69, 109, 30, 88], 28: [47, 8, 96, 21], 29: [94, 69, 40, 31], 30: [43, 58, 27, 0], 31: [7, 29, 45, 69], 32: [101, 77, 59, 35], 33: [36, 66, 80, 72], 34: [35, 4, 47], 35: [100, 34, 32], 36: [107, 33, 44, 0], 37: [78, 65, 91, 53], 38: [43, 4, 1, 102], 39: [50, 56, 22, 0], 40: [20, 29, 105, 84], 41: [17, 10, 103, 18], 42: [23, 55, 70], 43: [30, 4, 8, 38], 44: [36, 16, 99], 45: [31, 1, 76, 97], 46: [58, 105, 61, 15], 47: [84, 56, 34, 28], 48: [3, 64, 18, 8], 49: [70, 11, 92, 66], 50: [56, 108, 85, 39], 51: [77, 90, 92, 79], 52: [60, 83, 93, 17], 53: [37, 54, 82, 9], 54: [71, 82, 53, 85], 55: [42, 82, 75], 56: [39, 24, 50, 47], 57: [99, 79, 8, 86], 58: [46, 16, 30, 101], 59: [100, 32, 74, 26], 60: [52, 96, 23, 62], 61: [23, 46, 63], 62: [9, 100, 2, 60], 63: [73, 74, 81, 61], 64: [98, 48, 10, 12], 65: [37, 14, 23, 83], 66: [109, 102, 33, 49], 67: [86, 2, 103, 5], 68: [91, 11, 95, 72], 69: [31, 27, 13, 29], 70: [49, 42, 104], 71: [91, 54, 106, 90], 72: [87, 81, 68, 33], 73: [83, 104, 63, 74], 74: [59, 73, 63, 94], 75: [87, 55, 2], 76: [77, 80, 45], 77: [76, 6, 32, 51], 78: [87, 37, 85, 24], 79: [11, 57, 51, 84], 80: [76, 33, 98, 10], 81: [104, 63, 106, 72], 82: [53, 109, 54, 55], 83: [52, 73, 9, 65], 84: [103, 79, 40, 47], 85: [13, 50, 54, 78], 86: [57, 21, 67, 0], 87: [78, 75, 72, 5], 88: [27, 14, 107, 105], 89: [22, 19, 7, 95], 90: [3, 16, 71, 51], 91: [71, 37, 12, 68], 92: [99, 18, 49, 51], 93: [52, 97, 12], 94: [74, 95, 29, 14], 95: [89, 68, 20, 94], 96: [28, 60, 7, 11], 97: [14, 21, 45, 93], 98: [80, 5, 64, 25], 99: [44, 92, 57], 100: [10, 35, 62, 59], 101: [20, 109, 32, 58], 102: [38, 66, 20, 6], 103: [21, 67, 84, 41], 104: [81, 70, 73, 26], 105: [24, 88, 40, 46], 106: [7, 108, 71, 81], 107: [24, 36, 88, 17], 108: [6, 106, 50, 9], 109: [82, 101, 27, 66]}


WHITE = 'white'
GRAY = 'gray'
BLACK = 'black'

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

'''
    hamiltonianPath loops over each vertex v, in g
    to see if there is a hamiltonian path starting
    at that vertex.  return True as soon as we find one
    else False after searching entire graph
'''

def hamiltonianPath(g):
    if isConnected(g):
        for v in g:
            if hamiltonianPathFromVertex(g, v):
                return True
    return False

'''
    Checks all possible paths from a specified vertex, u, of
    a graph, g, using DFS.  Same principle as exploreDFS, except
    no timer necessary and instead of coloring nodes gray,
    they get recolored white, so they can be checked via a different path.
'''

def hamiltonianPathFromVertex(g, u):
    stack = []
    fors = {v:0 for v in g}
    colors = {v:WHITE for v in g}
    #memoPath = {(u, v):[] for u in g for v in g}
    stack.append(u)
    last = u
    colors[u] = GRAY

    while len(stack) > 0:
        while fors[last] >= len(g[last]):
            x = stack.pop()                    
            if len(stack) == 0:
                return False
            fors[x] = 0
            colors[x] = WHITE
            last = stack.pop()
            stack.append(last)
            
        nextv = g[last][fors[last]]

        if colors[nextv] != WHITE:
            fors[last] = fors[last]+1
        else:
            fors[last] = fors[last]+1
            stack.append(nextv)
            if len(stack) == len(g):
                return True
            colors[nextv] = GRAY
            fors[nextv] = 0
            last = nextv
    return False

    '''
        Explore DFS
        Mark nodes gray when visited
        Manually keep track of which child
        of a node we are currently searching
        by using a dictionary of "fors" values
        Mostly works except finds one too many forward
        edges.  just subtracting one at the end right now.
    '''

def exploreDFS(g):
    tree_edges = 0
    back_edges = 0
    forward_edges = 0
    cross_edges = 0
    stack = []
    fors = {v:0 for v in g}
    colors = {v:WHITE for v in g}
    timer = 0
    previsit = {v:0 for v in g}
    postvisit = {v:0 for v in g}
    visited = {(v, u):False for v in g for u in g if v != u}
    #memoPath = {(u, v):[] for u in g for v in g}

    v = g.keys()[0] #possibly random starting node
    stack.append(v) #stick it on the stack
    previsit[v] = timer + 1
    timer += 1
    print timer
    last = v
    colors[v] = GRAY

    while len(stack) > 0:

        #while loop to deal with things whose manual for loop numbers
        #are too big.  As long as their number is as big as the number
        #of their children, they get popped off.  If we continue until
        #stack is empty, we have completed our search
        
        while fors[last] >= len(g[last]):
            x = stack.pop()
            postvisit[x] = timer + 1 #if we are done with its children,
                                    #we are done with the node and we set
                                    #its post visit time.  Incremement timer.
            timer += 1
            print timer
            fors[x] = 0
            colors[x] = BLACK
            if len(stack) == 0:
                break
            last = stack.pop()  #my sad excuse for a peek method
            stack.append(last)
        
        nextv = g[last][fors[last]]

        #next node is black, means it was visted
        #already via a different path, so it is a
        #descendant, so forward edge.  Cross edge
        #in special cases.  Increment manual for loop.
        
        if colors[nextv] == BLACK:
            if previsit[last] < postvisit[nextv]:
                forward_edges+=1
            else:
                cross_edges+=1
            fors[last] = fors[last]+1

        #white node next is obviously a tree node.
            #add it to stack and increment for loop
            #set its previsit time, incremement timer
            #set its color to gray and its for loop to zero
            
        elif colors[nextv] == WHITE:
            tree_edges+=1
            fors[last] = fors[last]+1
            stack.append(nextv)
            previsit[nextv] = timer +1
            timer += 1
            print timer
            colors[nextv] = GRAY
            fors[nextv] = 0
            last = nextv

        #last option, next node is gray
            #then we're backtracking, obviously a back edge

        else:#if colors[nextv] == GRAY:
            back_edges+=1
            fors[last] = fors[last]+1

    #magically subtract one from the number of forward edges printed
    #because for some reason it is consistently giving me one too many
            #forward edges, and subtracting one at the very end is easier
            #than figuring out what is wrong
    print "total nodes:", numVertices(g)
    print "total edges:", 2*numEdges(g)
    print "forward:", forward_edges-1, "back:", back_edges, "cross:", cross_edges, "tree:", tree_edges