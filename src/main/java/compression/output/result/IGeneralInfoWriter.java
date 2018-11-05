package compression.output.result;

import compression.model.jsprit.RunResult;

import java.util.List;

/**
 * Defines a way to write information about several solutions to a file.
 */
public interface IGeneralInfoWriter {

    /**
     * Writes information about several solutions to a file.
     * @param path File path.
     * @param runResults A list of solutions.
     */
    void writeGeneralSolution(String path, List<RunResult> runResults);
}
