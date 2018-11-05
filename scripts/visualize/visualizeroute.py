import routes
import mplleaflet
import time
import sys

#Parameters
#Input file (*.route)
#Output file (*.html)
if(len(sys.argv) != 2):
	print "Niepoprawna liczba parametrow"

routes.plot(sys.argv[1])
mplleaflet.show(path=sys.argv[2])