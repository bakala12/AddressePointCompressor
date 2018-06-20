package compression.services.jsprit.conversion;

import compression.model.vrp.*;
import compression.services.compression.ICompressionService;
import compression.services.distance.IDistanceService;


public class EuclideanMetricCompressionVrpToJSpritConverter
        extends ExplicitMetricCompressionVrpProblemToJSpritConverter
        implements IVrpProblemToJSpritConverter {

    public EuclideanMetricCompressionVrpToJSpritConverter(ICompressionService compressionService, IDistanceService distanceService){
        super(compressionService, distanceService);
    }

    @Override
    public ConversionResult convertToJsprit(VrpProblem problem) {
        if(problem.getProblemMetric() != VrpProblemMetric.Euclidean){
            throw new ProblemConversionException("Metrics must be Euclidean for that converter");
        }
        problem.setProblemMetric(VrpProblemMetric.Explicit);
        problem.setDistanceMatrix(createDistanceMatrix(problem));
        return super.convertToJsprit(problem);
    }
}
