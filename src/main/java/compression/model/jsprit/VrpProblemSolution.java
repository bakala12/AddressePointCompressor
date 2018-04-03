package compression.model.jsprit;

import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@AllArgsConstructor
public class VrpProblemSolution {
    @Getter
    private VehicleRoutingProblem problem;
    @Getter
    private Collection<VehicleRoutingProblemSolution> solutions;
    @Getter
    private Integer numberOfIterations;
    @Getter
    private Double time;
}
