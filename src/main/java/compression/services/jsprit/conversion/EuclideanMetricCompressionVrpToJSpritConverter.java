package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import compression.model.vrp.*;
import compression.services.compression.CompressionService;
import compression.services.distance.IDistanceService;


public class EuclideanMetricCompressionVrpToJSpritConverter
        extends ExplicitMetricCompressionVrpProblemToJSpritConverter
        implements IVrpProblemToJSpritConverter {

    private final IDistanceService distanceService;

    public EuclideanMetricCompressionVrpToJSpritConverter(CompressionService compressionService, IDistanceService distanceService){
        super(compressionService);
        this.distanceService = distanceService;
    }

    @Override
    public VehicleRoutingProblem convertToJsprit(VrpProblem problem) {
        if(problem.getProblemMetric() != VrpProblemMetric.Euclidean){
            throw new ProblemConversionException("Metrics must be Euclidean for that converter");
        }
        problem.setProblemMetric(VrpProblemMetric.Explicit);
        problem.setDistanceMatrix(createDistanceMatrix(problem));
        return super.convertToJsprit(problem);
    }

    private DistanceMatrix createDistanceMatrix(VrpProblem problem){
        int dimensions = problem.getDimensions();
        SymmetricalDistanceMatrix matrix = new SymmetricalDistanceMatrix(dimensions);
        for(Client c: problem.getClients()){
            for(Client c1 : problem.getClients()){
                if(c.getId() != c1.getId()){
                    matrix.setDistance(c.getId(), c1.getId(), distanceService.getEuclideanDistance(c.getLocation(), c1.getLocation()));
                }
            }
            matrix.setDistance(c.getId(), problem.getDepot().getId(), distanceService.getEuclideanDistance(c.getLocation(), problem.getDepot().getLocation()));
        }
        return matrix;
    }
}
