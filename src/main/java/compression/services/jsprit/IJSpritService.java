package compression.services.jsprit;

import compression.model.jsprit.DecompressionMethod;
import compression.model.jsprit.VrpProblemSolution;
import compression.model.vrp.VrpProblem;

/**
 * An interface for solving VRP problems with JSprit library.
 */
public interface IJSpritService {
    /**
     * Sets number of iterations for JSprit.
     * @param maxNumberOfIterations Number of iterations.
     */
    void setMaxNumberOfIterations(int maxNumberOfIterations);
    /**
     * Sets random seed for JSprit.
     * @param seed Random seed.
     */
    void setRandomSeed(Long seed);
    /**
     * Solves full VRP problem - no compression algorithm used.
     * @param problem Original problem
     * @return VRP problem solution.
     */
    VrpProblemSolution solve(VrpProblem problem);
    /**
     * Solves full VRP problem - no compression algorithm used.
     * @param problem Original problem
     * @param dataFolderPath Folder for algorithm data files.
     * @return VRP problem solution.
     */
    VrpProblemSolution solve(VrpProblem problem, String dataFolderPath);
    /**
     * Solves full VRP problem - no compression algorithm used.
     * @param problem Original problem
     * @param dataFolderPath Folder for algorithm data files.
     * @param solutionRoutePath Path for solution file.
     * @return VRP problem solution.
     */
    VrpProblemSolution solve(VrpProblem problem, String dataFolderPath, String solutionRoutePath);
    /**
     * Compresses VRP problem, solves it by JSprit and then decompresses the result.
     * @param problem Original problem
     * @return VRP problem solution.
     */
    VrpProblemSolution compressAndSolve(VrpProblem problem);
    /**
     * Compresses VRP problem, solves it by JSprit and then decompresses the result.
     * @param problem Original problem
     * @param dataFolderPath Folder for algorithm data files.
     * @return VRP problem solution.
     */
    VrpProblemSolution compressAndSolve(VrpProblem problem, String dataFolderPath);
    /**
     * Compresses VRP problem, solves it by JSprit and then decompresses the result.
     * @param problem Original problem
     * @param dataFolderPath Folder for algorithm data files.
     * @param solutionRoutePath Path for solution file.
     * @return VRP problem solution.
     */
    VrpProblemSolution compressAndSolve(VrpProblem problem, String dataFolderPath, String solutionRoutePath);
    /**
     * Compresses VRP problem, solves it by JSprit and then decompresses the result.
     * @param problem Original problem
     * @param decompressionMethod Decompression method.
     * @return VRP problem solution.
     */
    VrpProblemSolution compressAndSolve(VrpProblem problem, DecompressionMethod decompressionMethod);
    /**
     * Compresses VRP problem, solves it by JSprit and then decompresses the result.
     * @param problem Original problem
     * @param dataFolderPath Folder for algorithm data files.
     * @param decompressionMethod Decompression method.
     * @return VRP problem solution.
     */
    VrpProblemSolution compressAndSolve(VrpProblem problem, String dataFolderPath, DecompressionMethod decompressionMethod);
    /**
     * Compresses VRP problem, solves it by JSprit and then decompresses the result.
     * @param problem Original problem
     * @param dataFolderPath Folder for algorithm data files.
     * @param solutionRoutePath Path for solution file.
     * @param decompressionMethod Decompression method.
     * @return VRP problem solution.
     */
    VrpProblemSolution compressAndSolve(VrpProblem problem, String dataFolderPath, String solutionRoutePath, DecompressionMethod decompressionMethod);
}
