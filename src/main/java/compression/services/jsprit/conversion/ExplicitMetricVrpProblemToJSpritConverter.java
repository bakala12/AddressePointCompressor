package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.util.VehicleRoutingTransportCostsMatrix;
import compression.model.vrp.*;
import compression.services.distance.IDistanceService;

public class ExplicitMetricVrpProblemToJSpritConverter
        extends BaseProblemToJSpritConverter
        implements IVrpProblemToJSpritConverter {

    public ExplicitMetricVrpProblemToJSpritConverter(IDistanceService distanceService){
        super(distanceService);
    }

    @Override
    public ConversionResult convertToJsprit(VrpProblem problem) {
        if(problem.getProblemMetric() != VrpProblemMetric.Explicit){
            throw new ProblemConversionException("Metric must be explicit for this converter");
        }
        VehicleRoutingProblem.Builder problemBuilder = VehicleRoutingProblem.Builder.newInstance();
        addVehicles(problemBuilder, problem, Location.newInstance(problem.getDepot().getId().toString()));
        addClients(problemBuilder, problem);
        VehicleRoutingTransportCostsMatrix.Builder matrixCostBuilder = VehicleRoutingTransportCostsMatrix.Builder.newInstance(false);
        copyDistanceMatrix(problem, matrixCostBuilder);
        problemBuilder.setRoutingCost(matrixCostBuilder.build());
        return new ConversionResult(problemBuilder.build(), null);
    }

    @Override
    protected Location convertLocation(compression.model.vrp.Client client){
        return Location.newInstance(client.getId().toString());
    }
}
