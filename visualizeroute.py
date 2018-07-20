#import sys
import pandas as pd
import matplotlib.pyplot as plt
import time

#inputFile = sys.argv[1]
#outputFile = sys.argv[2]

def plot(data):
    fig = plt.figure(figsize=(15, 15))
    #cm = plt.get_cmap('autumn')
    for idx, node in data.iterrows():
		route = node['class']
		plt.plot(node['lon'], node['lat'], 'o', markersize=10, color = route)
		plt.annotate(idx, [node['lon'], node['lat']])
    fig.savefig("foo.png")

	
print "hello"
time.sleep(10)
routes = pd.read_csv('testroutes.route')
plot(routes)
