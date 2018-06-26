package compression.services.resolving;

import compression.model.jsprit.VrpProblemSolution;
import compression.model.vrp.VrpProblem;

public interface ISolutionRouteResolver {
    ResolvedSolution resolveRoutes(VrpProblem originalProblem, VrpProblemSolution problemSolution);
}
