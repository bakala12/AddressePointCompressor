package compression.output.result;

import compression.model.jsprit.SolutionInfo;

public interface ISolutionInfoWriter {
    void writeSolution(String path, SolutionInfo solutionInfo);
}
