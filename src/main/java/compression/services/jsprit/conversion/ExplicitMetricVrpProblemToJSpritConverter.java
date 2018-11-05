package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.util.Coordinate;
import com.graphhopper.jsprit.core.util.VehicleRoutingTransportCostsMatrix;
import compression.model.vrp.*;
import compression.services.distance.IDistanceService;

/**
 * Converts full problem with explicit distance matrix to JSprit problem - no compression algorithm used.
 */
public class ExplicitMetricVrpProblemToJSpritConverter
        extends BaseProblemToJSpritConverter
        implements IVrpProblemToJSpritConverter {

    /**
     * Initializes a new instance of ExplicitMetricVrpProblemToJSpritConverter.
     * @param distanceService Distance service.
     */
    public ExplicitMetricVrpProblemToJSpritConverter(IDistanceService distanceService){
        super(distanceService);
    }

    /**
     * Converts VRP problem to JSprit problem. This converts full problem - no compression used.
     * @param problem VRP problem.
     * @return Conversion phase result.
     */
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
        return new ConversionResult(problemBuilder.build(), null, null);
    }

    /**
     * Converts location to JSprit location.
     * @param client Client location
     * @return JSprit location.
     */
    @Override
    protected Location convertLocation(Client client) {
        Location.Builder b = Location.Builder.newInstance();
        b.setCoordinate(new Coordinate(client.getLocation().getLongitude(), client.getLocation().getLatitude()));
        b.setId(client.getId().toString());
        return b.build();
    }
}
