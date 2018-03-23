package compression.services.jsprit;

import compression.model.vrp.VrpProblem;

public interface IJSpritService {
    void solve(VrpProblem problem);
}
