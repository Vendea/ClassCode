from mpl_toolkits.mplot3d import Axes3D
import matplotlib.pyplot as plt
import csv
import numpy

fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')
X = []
Y = []
Z = []
temps = []

with open('watersim.csv', 'rb') as csvfile:
    cr = csv.reader(csvfile, delimiter='\t')
    for row in cr:
        X.append(row[0])
        Y.append(row[1])
        Z.append(row[2])
        temps.append(row[3])

ax.scatter(X, Y, Z, c='r', marker='o')

ax.set_xlabel('x axis')
ax.set_ylabel('y axis')
ax.set_zlabel('z axis')

plt.show()
