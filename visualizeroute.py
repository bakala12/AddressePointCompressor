import sys
import csv
import matplotlib.pyplot as plt
from mpl_toolkits.basemap import Basemap

inputFile = sys.argv[1]
outputFile = sys.argv[2]

with open(inputFile, 'rb') as csvfile:
	csvreader = csv.reader(csvfile, delimiter=' ', quotechar='|')

	figure1 = plt.figure()
	ax=fig.add_axes([0.1,0.1,0.8,0.8])
	m = Basemap(llcrnrlon=-100.,llcrnrlat=20.,urcrnrlon=20.,urcrnrlat=60.,\
            rsphere=(6378137.00,6356752.3142),\
            resolution='l',projection='merc',\
            lat_0=40.,lon_0=-20.,lat_ts=20.)
	for row in csvreader:
		plt.plot(row[0], row[1], 'ro')
	m.drawcoastlines()
	m.fillcontinents()
	figure1.savefig(outputFile)
	plt.close(figure1)



# setup mercator map projection.

# nylat, nylon are lat/lon of New York
nylat = 40.78; nylon = -73.98
# lonlat, lonlon are lat/lon of London.
lonlat = 51.53; lonlon = 0.08
# draw great circle route between NY and London
m.drawgreatcircle(nylon,nylat,lonlon,lonlat,linewidth=2,color='b')

# draw parallels
m.drawparallels(np.arange(10,90,20),labels=[1,1,0,1])
# draw meridians
m.drawmeridians(np.arange(-180,180,30),labels=[1,1,0,1])
ax.set_title('Great Circle from New York to London')
plt.show()