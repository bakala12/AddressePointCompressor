package compression.services.jsprit;

import compression.model.jsprit.VrpSolution;
import compression.model.vrp.VrpProblem;

import java.util.Collection;

public interface IJSpritService {
    Collection<VrpSolution> solve(VrpProblem problem);
}
