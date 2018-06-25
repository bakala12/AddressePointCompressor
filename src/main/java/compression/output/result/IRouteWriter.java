package compression.output.result;

import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;

public interface IRouteWriter {
    void writeRoute(VehicleRoutingProblemSolution solution, String path);
}
