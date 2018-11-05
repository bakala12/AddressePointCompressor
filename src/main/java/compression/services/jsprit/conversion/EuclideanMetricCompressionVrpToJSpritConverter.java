package compression.services.jsprit.conversion;

import compression.model.vrp.*;
import compression.services.compression.ICompressionService;
import compression.services.distance.IDistanceService;

/**
 * Converts VRP problem with Euclidean distances to JSprit problem using compression algorithm.
 */
public class EuclideanMetricCompressionVrpToJSpritConverter
        extends ExplicitMetricCompressionVrpProblemToJSpritConverter
        implements IVrpProblemToJSpritConverter {

    /**
     * Initializes a new instance of EuclideanMetricCompressionVrpToJSpritConverter.
     * @param compressionService Compression service.
     * @param distanceService Distance service.
     */
    public EuclideanMetricCompressionVrpToJSpritConverter(ICompressionService compressionService, IDistanceService distanceService){
        super(compressionService, distanceService);
    }

    /**
     * Converts VRP problem to JSprit problem. This uses compression and convert compressed problem to be solved by JSprit.
     * @param problem VRP problem.
     * @return Conversion phase result.
     */
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
