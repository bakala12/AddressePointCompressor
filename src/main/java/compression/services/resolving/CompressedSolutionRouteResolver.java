package compression.services.resolving;

import com.graphhopper.jsprit.core.problem.Location;
import compression.model.vrp.Client;
import compression.model.vrp.VrpProblem;
import compression.model.vrp.helpers.AggregatedService;
import compression.model.vrp.helpers.LocationVertex;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CompressedSolutionRouteResolver extends BaseSolutionRouteResolver implements ISolutionRouteResolver {
    @Override
    protected List<VrpSolutionRouteNode> convertLocationToNodes(VrpProblem problem, Location location, Map<Long, AggregatedService> compressionMap) {
        List<VrpSolutionRouteNode> nodes = new ArrayList<>();
        Long locationId = Long.parseLong(location.getId());
        AggregatedService aggr = compressionMap.getOrDefault(locationId, null);
        if(aggr != null){
            for(LocationVertex v : aggr.getVertices()){
                VrpSolutionRouteNode n = new VrpSolutionRouteNode(v.getId(), changeLocation(v.getLocation()));
                nodes.add(n);
            }
        }
        else{
            if(locationId.equals(1L)){
                Location loc = changeLocation(problem.getDepot().getLocation());
                nodes.add(new VrpSolutionRouteNode(1L, loc));
            }
            throw new RuntimeException("Invalid aggregated service");
        }
        return nodes;
    }

    private Location changeLocation(compression.model.vrp.Location location){
        return Location.newInstance(location.getLongitude(), location.getLatitude());
    }
}
