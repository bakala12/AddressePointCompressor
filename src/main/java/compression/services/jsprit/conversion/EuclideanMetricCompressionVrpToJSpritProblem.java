package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import compression.graph.branching.TreeBranch;
import compression.model.vrp.Client;
import compression.model.vrp.Vehicle;
import compression.model.vrp.VrpProblem;
import compression.services.compression.nonmap.NonMapCompressionService;
import compression.services.compression.nonmap.graph.LocationVertex;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class EuclideanMetricCompressionVrpToJSpritProblem
        extends BaseProblemToJSpritConverter
        implements IVrpProblemToJSpritConverter {

    private final NonMapCompressionService compressionService;

    @Override
    public VehicleRoutingProblem convertToJsprit(VrpProblem problem) {
        List<TreeBranch<LocationVertex>> branches = compressionService.getAggregatedClients(problem);
        return null;
    }

    @Override
    protected Location convertLocation(Client client) {
        return null;
    }
}
