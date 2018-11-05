package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.util.VehicleRoutingTransportCostsMatrix;
import compression.model.vrp.*;
import compression.model.vrp.helpers.AggregatedService;
import compression.services.compression.CompressionResult;
import compression.services.compression.ICompressionService;
import compression.services.distance.IDistanceService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Converts VRP problem with explicit distance matrix to JSprit problem using compression algorithm.
 */
public class ExplicitMetricCompressionVrpProblemToJSpritConverter
        extends BaseProblemToJSpritConverter
        implements IVrpProblemToJSpritConverter {

    private final ICompressionService compressionService;

    /**
     * Initializes a new instance of ExplicitMetricCompressionVrpProblemToJSpritConverter
     * @param compressionService Compression service.
     * @param distanceService Distance service.
     */
    public ExplicitMetricCompressionVrpProblemToJSpritConverter(ICompressionService compressionService, IDistanceService distanceService){
        super(distanceService);
        this.compressionService = compressionService;
    }

    /**
     * Converts location to JSprit location.
     * @param client Client location
     * @return JSprit location.
     */
    @Override
    protected Location convertLocation(Client client) {
        return Location.newInstance(client.getId().toString());
    }

    /**
     * Converts VRP problem to JSprit problem. This uses compression and convert compressed problem to be solved by JSprit.
     * @param problem VRP problem.
     * @return Conversion phase result.
     */
    @Override
    public ConversionResult convertToJsprit(VrpProblem problem) {
        if(problem.getProblemMetric() != VrpProblemMetric.Explicit){
            throw new ProblemConversionException("Metric type must be explicit for this converter");
        }
        if(problem.getDistanceMatrix() == null){
            throw new ProblemConversionException("Distance matrix cannot be null");
        }
        CompressionResult compressionResult = compressionService.getAggregatedClients(problem);
        List<AggregatedService> services = compressionResult.getAggregatedServices();
        VehicleRoutingProblem.Builder problemBuilder = VehicleRoutingProblem.Builder.newInstance();
        Location depotLocation = Location.newInstance(problem.getDepot().getId().toString());
        addVehicles(problemBuilder, problem, depotLocation);
        DistanceMatrix matrix = compressMatrix(services, problem.getDepot(), problem.getDistanceMatrix());
        VehicleRoutingTransportCostsMatrix.Builder matrixBuilder = VehicleRoutingTransportCostsMatrix.Builder.newInstance(false);
        copyDistanceMatrix(matrix, matrixBuilder);
        Map<Long, AggregatedService> compressionMap = new HashMap<>();
        for(AggregatedService s : services){
            Service service = Service.Builder.newInstance(s.getId().toString())
                    .setLocation(Location.newInstance(s.getId().toString()))
                    .addSizeDimension(0, s.getInternalCost().intValue())
                    .build();
            problemBuilder.addJob(service);
            compressionMap.put(s.getId(), s);
        }
        problemBuilder.setRoutingCost(matrixBuilder.build());
        return new ConversionResult(problemBuilder.build(), compressionResult, compressionMap);
    }

    private DistanceMatrix compressMatrix(List<AggregatedService> services, Depot depot, DistanceMatrix distances){
        DistanceMatrix matrix = new DistanceMatrix(services.size()+1);
        for(AggregatedService from : services){
            for(AggregatedService to : services){
                if(from != to){
                    Double fromTo = distances.getDistance(from.getOutputVertex().getId(), to.getInputVertex().getId()) + to.getInternalDistance();
                    matrix.setDistance(from.getId(), to.getId(), fromTo);
                    Double toFrom = distances.getDistance(to.getOutputVertex().getId(), from.getInputVertex().getId()) + from.getInternalDistance();
                    matrix.setDistance(to.getId(), from.getId(), toFrom);
                }
            }
            Double fromDepot = distances.getDistance(depot.getId(), from.getInputVertex().getId())+from.getInternalDistance();
            matrix.setDistance(depot.getId(), from.getId(), fromDepot);
            Double toDepot = distances.getDistance(from.getOutputVertex().getId(), depot.getId());
            matrix.setDistance(from.getId(), depot.getId(), toDepot);
        }
        return matrix;
    }

    private void copyDistanceMatrix(DistanceMatrix matrix, VehicleRoutingTransportCostsMatrix.Builder matrixCostBuilder){
        for(Long from = 1L; from <= matrix.getDimensions(); from++){
            for(Long to = 1L; to <= matrix.getDimensions(); to++){
                if(!from.equals(to)) {
                    matrixCostBuilder.addTransportDistance(from.toString(), to.toString(), matrix.getDistance(from, to));
                }
            }
        }
    }
}
