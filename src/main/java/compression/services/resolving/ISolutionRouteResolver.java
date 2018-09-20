package compression.services.resolving;

import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import compression.model.jsprit.VrpProblemSolution;
import compression.model.vrp.VrpProblem;
import compression.model.vrp.helpers.AggregatedService;

import java.util.Map;

public interface ISolutionRouteResolver {
    ResolvedSolution resolveRoutes(VrpProblem originalProblem, VehicleRoutingProblemSolution solution, Map<Long, AggregatedService> compressionMap);
}
