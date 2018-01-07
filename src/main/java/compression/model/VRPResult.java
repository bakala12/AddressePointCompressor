package compression.model;

import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class VRPResult {
    @Getter
    private final VehicleRoutingProblem problem;
    @Getter
    private final VehicleRoutingProblemSolution bestSolution;
}
