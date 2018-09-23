package compression.output.result;

import compression.model.jsprit.RunResult;

import java.util.List;

public interface IGeneralInfoWriter {
    void writeGeneralSolution(String path, List<RunResult> runResults);
}
