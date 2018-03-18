package compression.services.graph;

import compression.model.graph.LocationGraph;
import compression.model.graph.RouteEdge;
import compression.model.vrp.Client;
import compression.model.vrp.Location;
import compression.model.vrp.Vehicle;
import compression.model.vrp.VrpProblem;
import compression.services.graphhopper.IGraphHopperService;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class BuildRouteGraphService {
    private final IGraphHopperService graphHopperService;

    public LocationGraph buildLocationGraph(VrpProblem problem){
        LocationGraph.LocationGraphBuilder builder = LocationGraph.LocationGraphBuilder.newInstance();
        List<Location> vertices = new LinkedList<>();
        builder.specifyLocations(vertices);
        return (LocationGraph) builder.build();
    }

    private List<Location> getVertices(VrpProblem problem){
        Set<Location> set = new HashSet<>();
        for(Client cl : problem.getClients()){
            set.add(cl);
        }
        for(Vehicle v : problem.getVehicles()){
            set.add(v.getStartLocation());
            set.add(v.getEndLocation());
        }
        List<Location> loc = new LinkedList<>();
        for(Location l : set){
            loc.add(l);
        }
        return loc;
    }

    private List<RouteEdge> getEdges(LocationGraph.LocationGraphBuilder builder){
        return null;
    }
}
