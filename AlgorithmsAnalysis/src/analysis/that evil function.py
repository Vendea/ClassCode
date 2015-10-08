import math
x = input('pick a real number: ')

def doFunction(x):
    intlist = []
    zvals = [[x]]
    z = math.floor(x)

    if x - z == 0:
        return [z]

    xtwo = 1 / (x - z)

    test1 = xtwo
    test2 = 1 / (xtwo - math.floor(xtwo))
    if test1 == test2:
        return "non rational starting point"
    
    try:
        intlist = doFunction(xtwo)
        for i in intlist:
            zvals.append(intlist[i])
    except:
        return "Aggghh too long"
    return zvals

print doFunction(x)

    
    


