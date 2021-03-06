package compression.services.resolving;

import compression.model.jsprit.SolutionInfo;
import compression.model.vrp.VrpProblem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Represents resolved VRP solution.
 */
@AllArgsConstructor
public class ResolvedSolution {
    @Getter
    private VrpProblem originalProblem;
    @Getter
    private Double cost;
    @Getter
    private List<VrpSolutionRoute> routes;
}
