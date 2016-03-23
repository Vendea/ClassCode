import random
w2 = 24
bb = 0
ww = 0
bw = 0
for i in range(0, 10):
    a = random.randint(1, 40)
    b = random.randint(1, 40)
    if a <= w2:
        if b <= w2:
            bb = bb+1
        else:
            bw = bw + 1
    else:
        if b <= w2:
            bw = bw +1
        else:
            ww = ww + 1
print " ww = "
print ww
print " bb = "
print bb
print " bw = "
print bw
