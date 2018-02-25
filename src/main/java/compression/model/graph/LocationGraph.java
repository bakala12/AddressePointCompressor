package compression.model.graph;

import compression.model.vrp.Location;
import compression.model.vrp.Route;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class LocationGraph {
    @Getter
    private final Map<Integer, Location> locationMap;
    @Getter
    private final List<RouteEdge>[] neighbours;

    public int getVerticesCount(){
        return locationMap.size();
    }

    public Location getLocationFromId(int locationId){
        return locationMap.get(Integer.valueOf(locationId));
    }

    public List<RouteEdge> getNeighbours(int locationId){
        return neighbours[locationId];
    }

    public static class LocationGraphBuilder{
        private Map<Integer, Location> locationMap;
        private List<RouteEdge>[] edges;

        public LocationGraphBuilder(){
            locationMap = new HashMap<>();
        }

        public void specifyLocations(List<? extends Location> locations){
            Integer i=0;
            edges = (List<RouteEdge>[]) new Object[locations.size()];
            for(Location l : locations){
                locationMap.put(i, l);
                edges[i] = new LinkedList<>();
                i++;
            }
        }

        public void addEdge(RouteEdge edge){
            edges[edge.getFromLocationId()].add(edge);
        }

        public LocationGraph build(){
            return new LocationGraph(locationMap, edges);
        }

        public static LocationGraphBuilder newInstance(){
            return new LocationGraphBuilder();
        }
    }
}
