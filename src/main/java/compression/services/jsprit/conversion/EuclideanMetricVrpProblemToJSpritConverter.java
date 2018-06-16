package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import compression.model.vrp.VrpProblem;
import compression.model.vrp.VrpProblemMetric;

public class EuclideanMetricVrpProblemToJSpritConverter
        extends BaseProblemToJSpritConverter
        implements IVrpProblemToJSpritConverter {

    @Override
    public ConversionResult convertToJsprit(VrpProblem problem) {
        if(problem.getProblemMetric() != VrpProblemMetric.Euclidean){
            throw new ProblemConversionException("Metrics must be Euclidean for that converter");
        }
        VehicleRoutingProblem.Builder problemBuilder = VehicleRoutingProblem.Builder.newInstance();
        Location depot = Location.newInstance(problem.getDepot().getLocation().getLatitude(), problem.getDepot().getLocation().getLongitude());
        addVehicles(problemBuilder, problem, depot);
        addClients(problemBuilder, problem);
        return new ConversionResult(problemBuilder.build(), null);
    }

    @Override
    protected Location convertLocation(compression.model.vrp.Client client){
        return Location.newInstance(client.getLocation().getLatitude(), client.getLocation().getLongitude());
    }
}
