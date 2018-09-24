import pandas as pd
import matplotlib.pyplot as plt

def plot(inputFile):
	data = pd.read_csv(inputFile)
	plt.figure(figsize=(15,15))
	cmap = plt.cm.jet
	cmaplist = [cmap(i) for i in range(cmap.N)]
	cmaplist[0] = (.5,.5,.5,1.0)
	cm = cmap.from_list('Custom cmap', cmaplist, cmap.N)
	routeNum = data["routeId"].max()
	for idx, node in data.iterrows():
		r = node["routeId"]/routeNum
		plt.plot(node["lon"], node["lat"], "ko-", markersize=10, color=cm(r))
		plt.annotate(idx, [node["lon"], node["lat"]])