package compression.output.result;

import compression.model.jsprit.SolutionInfo;

/**
 * Defines a way to write general solution info to a file.
 */
public interface ISolutionInfoWriter {
    /**
     * Writes solution information to a file.
     * @param path File path.
     * @param solutionInfo Solution information
     */
    void writeSolution(String path, SolutionInfo solutionInfo);
}
