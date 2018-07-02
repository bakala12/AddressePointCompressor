package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.util.VehicleRoutingTransportCostsMatrix;
import compression.model.vrp.VrpProblem;
import compression.model.vrp.VrpProblemMetric;
import compression.services.distance.IDistanceService;

public class EuclideanMetricVrpProblemToJSpritConverter
        extends ExplicitMetricVrpProblemToJSpritConverter
        implements IVrpProblemToJSpritConverter {

    public EuclideanMetricVrpProblemToJSpritConverter(IDistanceService distanceService) {
        super(distanceService);
    }

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
