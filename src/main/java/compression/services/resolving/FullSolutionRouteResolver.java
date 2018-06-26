package compression.services.resolving;

import com.graphhopper.jsprit.core.problem.Location;
import compression.model.vrp.VrpProblem;

import java.util.ArrayList;
import java.util.List;

public class FullSolutionRouteResolver extends BaseSolutionRouteResolver implements ISolutionRouteResolver{

    @Override
    protected List<VrpSolutionRouteNode> convertLocationToNodes(VrpProblem problem, Location location) {
        List<VrpSolutionRouteNode> nodes = new ArrayList<>();
        Long locationId = Long.parseLong(location.getId());
        VrpSolutionRouteNode n = new VrpSolutionRouteNode(locationId, location);
        nodes.add(n);
        return nodes;
    }
}
