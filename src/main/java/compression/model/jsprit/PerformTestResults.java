package compression.model.jsprit;

import compression.model.vrp.VrpProblem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public class PerformTestResults {
    @Getter
    private VrpProblem problem;
    @Getter
    private Integer[] iterations;
    @Getter
    private Map<Integer, Double> problemCostsPerIteration;
    @Getter
    private Map<Integer, VrpProblemSolution> solutionPerIteration;
    @Getter
    private Map<Integer, Double> timePerIteration;
}
