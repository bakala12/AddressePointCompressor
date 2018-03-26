package compression.services.jsprit;

import compression.model.jsprit.VrpProblemSolution;
import compression.model.vrp.VrpProblem;

public interface IJSpritService {
    VrpProblemSolution solve(VrpProblem problem);
}
