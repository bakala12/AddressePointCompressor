package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.util.VehicleRoutingTransportCostsMatrix;
import compression.model.vrp.VrpProblem;
import compression.model.vrp.VrpProblemMetric;
import compression.services.distance.IDistanceService;

/**
 * Converts full problem with Euclidean distances to JSprit problem - no compression algorithm used.
 */
public class EuclideanMetricVrpProblemToJSpritConverter
        extends ExplicitMetricVrpProblemToJSpritConverter
        implements IVrpProblemToJSpritConverter {

    /**
     * Initializes a new instance of EuclideanMetricVrpProblemToJSpritConverter.
     * @param distanceService Distance service.
     */
    public EuclideanMetricVrpProblemToJSpritConverter(IDistanceService distanceService) {
        super(distanceService);
    }

    /**
     * Converts VRP problem to JSprit problem. This converts full problem - no compression used.
     * @param problem VRP problem.
     * @return Conversion phase result.
     */
    @Override
    public ConversionResult convertToJsprit(VrpProblem problem) {
        if(problem.getProblemMetric() != VrpProblemMetric.Euclidean){
            throw new ProblemConversionException("Metrics must be Euclidean for that converter");
        }
        problem.setDistanceMatrix(createDistanceMatrix(problem));
        problem.setProblemMetric(VrpProblemMetric.Explicit);
        return super.convertToJsprit(problem);
    }
}
