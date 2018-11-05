package compression.services.resolving;

import com.graphhopper.jsprit.core.problem.Location;
import compression.model.vrp.VrpProblem;
import compression.model.vrp.helpers.AggregatedService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Serves decompression for full problem where decompression phase is not needed.
 */
public class FullSolutionRouteResolver extends BaseSolutionRouteResolver implements ISolutionRouteResolver{

    /**
     * Converts a solution of full problem to common form. Convert location to simple route node.
     * @param problem Original VRP problem
     * @param location Compressed problem vertex.
     * @param compressionMap Not used.
     * @return Converted node.
     */
    @Override
    protected List<VrpSolutionRouteNode> convertLocationToNodes(VrpProblem problem, Location location, Map<Long, AggregatedService> compressionMap) {
        List<VrpSolutionRouteNode> nodes = new ArrayList<>();
        Long locationId = Long.parseLong(location.getId());
        VrpSolutionRouteNode n = new VrpSolutionRouteNode(locationId, location);
        nodes.add(n);
        return nodes;
    }
}
