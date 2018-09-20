package compression.model.jsprit;

import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import compression.model.vrp.helpers.AggregatedService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;
import java.util.Map;

@AllArgsConstructor
public class VrpProblemSolution {
    @Getter
    private VehicleRoutingProblem problem;
    @Getter
    private Collection<VehicleRoutingProblemSolution> solutions; //to be removed
    @Getter
    private SolutionInfo solutionInfo;
    @Getter
    private Map<Long, AggregatedService> compressionMap; //to be removed
}
