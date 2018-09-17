package compression.services.jsprit;

import compression.model.jsprit.VrpProblemSolution;
import compression.model.vrp.VrpProblem;

public interface IJSpritService {
    void setMaxNumberOfIterations(int maxNumberOfIterations);
    VrpProblemSolution solve(VrpProblem problem);
    VrpProblemSolution solve(VrpProblem problem, String dataFolderPath);
    VrpProblemSolution solve(VrpProblem problem, String dataFolderPath, String solutionRoutePath);
    VrpProblemSolution compressAndSolve(VrpProblem problem);
    VrpProblemSolution compressAndSolve(VrpProblem problem, String dataFolderPath);
    VrpProblemSolution compressAndSolve(VrpProblem problem, String dataFolderPath, String solutionRoutePath);
}
