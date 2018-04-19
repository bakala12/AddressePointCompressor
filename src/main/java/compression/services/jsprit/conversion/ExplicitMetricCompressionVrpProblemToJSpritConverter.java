package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import compression.graph.branching.TreeBranch;
import compression.model.vrp.Client;
import compression.model.vrp.Vehicle;
import compression.model.vrp.VrpProblem;
import compression.model.vrp.VrpProblemMetric;
import compression.services.compression.nonmap.NonMapCompressionService;
import compression.services.compression.nonmap.graph.LocationVertex;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ExplicitMetricCompressionVrpProblemToJSpritConverter
        extends BaseProblemToJSpritConverter
        implements IVrpProblemToJSpritConverter {

    private final NonMapCompressionService compressionService;

    @Override
    protected Location convertLocation(Client client) {
        return null;
    }

    @Override
    public VehicleRoutingProblem convertToJsprit(VrpProblem problem) {
        if(problem.getProblemMetric() != VrpProblemMetric.Explicit){
            throw new ProblemConversionException("Metric type must be explicit for this converter");
        }
        if(problem.getDistanceMatrix() == null){
            throw new ProblemConversionException("Distance matrix cannot be null");
        }
        List<TreeBranch<LocationVertex>> branches = compressionService.getAggregatedClients(problem);
        VehicleRoutingProblem.Builder problemBuilder = VehicleRoutingProblem.Builder.newInstance();
        Location depotLocation = Location.newInstance(problem.getDepot().getId().toString());
        addVehicles(problemBuilder, problem, depotLocation);
        
        return problemBuilder.build();
    }
}
