package compression.services.jsprit;

import compression.model.jsprit.PerformTestParameters;
import compression.model.jsprit.PerformTestResults;
import compression.model.vrp.VrpProblem;

public interface IJSpritPerformTestService {
    PerformTestResults solveWithResults(VrpProblem problem, PerformTestParameters parameters);
    PerformTestResults compressSolveWithResults(VrpProblem problem, PerformTestParameters parameters);
}
