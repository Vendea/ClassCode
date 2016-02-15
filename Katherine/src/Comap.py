'''
we treat the tub as a thermal conductor
between the water and air.  
k, the thermal condtuctivity of the tub, is 
assumed to be similar to that of acrylic glass,
which is given as about 0.19 W/m*K
'''
# f(x) gives heat transfer per second

# constant for temp of surroundings
AIRTEMP = 15.56 # degrees Celsius
kTUB = 0.19 # Watts per meter Kelvins
LENGTH = 0.10 #thickness of the tub, assumed constant
RELHUMIDITY = 0.50 # average comfortable relative humidity
MAXHUMIDITY = 0.019826 # maximum saturation kg/kg of air at 25 Celsius
HUMIDRATIO = 0.0098 # kg/kg - from Mollier diagram
SATPA = 7297 # Pa saturation pressure of water at 40 
EVAPHEAT = 2257 # kJ / kg evaporation heat of water

# C nought is assumed to be higher than TEMP
def heatpersecondtub(x, y, z, To):
	surfaceArea = 2*x*y + 2*y*z + x*z
	deltaT = To - AIRTEMP
	return kTUB * surfaceArea * deltaT / LENGTH

def heatpersecondtubpersa(To, air):
	deltaT = To - air
	return kTUB * deltaT / LENGTH

def heatpersecondair(x, y, z, To):
        return 0
	#neglecting this 
