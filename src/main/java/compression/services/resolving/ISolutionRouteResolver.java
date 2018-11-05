package compression.services.resolving;

import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import compression.model.jsprit.VrpProblemSolution;
import compression.model.vrp.VrpProblem;
import compression.model.vrp.helpers.AggregatedService;

import java.util.Map;

/**
 * Defines a method for decompressing compressed VRP instance solution.
 */
public interface ISolutionRouteResolver {
    /**
     * Resolves routes of compressed problem solution and decompresses them.
     * @param originalProblem Original VRP problem.
     * @param solution Compressed problem solution.
     * @param compressionMap Compression map
     * @return Resolved original problem solution.
     */
    ResolvedSolution resolveRoutes(VrpProblem originalProblem, VehicleRoutingProblemSolution solution, Map<Long, AggregatedService> compressionMap);
}
