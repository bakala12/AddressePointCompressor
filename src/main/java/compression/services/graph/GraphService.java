package compression.services.graph;

import compression.model.graph.LocationGraph;
import compression.model.vrp.Location;
import compression.model.vrp.VrpProblem;
import compression.services.distance.IDistanceService;
import compression.services.graphhopper.IGraphHopperService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GraphService implements IGraphService{
    private final IGraphHopperService graphHopperService;
    private final IDistanceService distanceService;

    private LocationGraph.GraphBuilder getLocationGraphBuilder(VrpProblem problem){
        LocationGraph.GraphBuilder builder = LocationGraph.GraphBuilder.newInstance();
        List<? extends Location> locations = problem.getClients();
        builder.specifyLocations(locations);
        return builder;
    }

    @Override
    public LocationGraph minimalSpanningTree(VrpProblem problem){
        LocationGraph.GraphBuilder builder = getLocationGraphBuilder(problem);
        return null;
    }
}
