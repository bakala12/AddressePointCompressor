package compression.services.resolving;

import com.graphhopper.jsprit.core.problem.Location;
import compression.model.vrp.VrpProblem;

import java.util.ArrayList;
import java.util.List;

public class CompressedSolutionRouteResolver extends BaseSolutionRouteResolver implements ISolutionRouteResolver {
    @Override
    protected List<VrpSolutionRouteNode> convertLocationToNodes(VrpProblem problem, Location location) {
        List<VrpSolutionRouteNode> nodes = new ArrayList<>();
        Long locationId = Long.parseLong(location.getId());
        //todo map id -> aggregatedService
        return nodes;
    }
}
