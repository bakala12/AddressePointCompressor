package compression.services.jsprit;

import compression.model.VRPProblem;
import compression.model.VRPResult;

public interface IJSpritService {
    VRPResult solveProblem(VRPProblem problem);
}
