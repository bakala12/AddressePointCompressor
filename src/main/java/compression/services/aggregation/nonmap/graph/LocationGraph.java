package compression.services.aggregation.nonmap.graph;

import compression.graph.IGraph;
import compression.graph.simple.SimpleListGraph;
import compression.graph.simple.SimpleMatrixGraph;
import compression.model.vrp.Location;

import java.util.*;

public class LocationGraph extends SimpleMatrixGraph<LocationVertex, LocationEdge>{

    private static Collection<LocationVertex> convert(Collection<Location> locations){
        List<LocationVertex> loc = new LinkedList<>();
        for(Location l : locations){
            loc.add(new LocationVertex(l));
        }
        return loc;
    }

    public LocationGraph(Collection<Location> items) {
        super(convert(items));
    }

    @Override
    public IGraph<LocationVertex, LocationEdge> emptyGraph() {
        return new SimpleListGraph<>(items);
    }

    public void addExplicitEdge(Location from, Location to, Double distance){
        addEdge(new LocationEdge(from, to, distance));
    }

    public void addEuclideanEdge(Location from, Location to){
        addEdge(new LocationEdge(from, to));
    }
}
