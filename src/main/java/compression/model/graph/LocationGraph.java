package compression.model.graph;

import compression.model.vrp.Location;

import java.util.List;

public class LocationGraph extends Graph<Location, RouteEdge> {
    public LocationGraph(Location[] locations, List<RouteEdge>[] neighbours) {
        super(locations, neighbours);
    }
}
