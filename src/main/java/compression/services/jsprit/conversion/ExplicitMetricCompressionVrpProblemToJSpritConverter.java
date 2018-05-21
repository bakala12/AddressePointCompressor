package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.util.VehicleRoutingTransportCostsMatrix;
import compression.graph.branching.TreeBranch;
import compression.model.vrp.*;
import compression.services.compression.CompressionService;
import compression.services.compression.graph.LocationVertex;
import compression.services.jsprit.extensions.AggregatedService;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class ExplicitMetricCompressionVrpProblemToJSpritConverter
        extends BaseProblemToJSpritConverter
        implements IVrpProblemToJSpritConverter {

    private final CompressionService compressionService;

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
        List<AggregatedService> services = aggregateServices(branches, problem.getDistanceMatrix());
        DistanceMatrix matrix = compressMatrix(services, problem.getDepot(), problem.getDistanceMatrix());
        VehicleRoutingTransportCostsMatrix.Builder matrixBuilder = VehicleRoutingTransportCostsMatrix.Builder.newInstance(false);
        copyDistanceMatrix(matrix, matrixBuilder);
        for(AggregatedService s : services){
            Service service = Service.Builder.newInstance(s.getId().toString())
                    .setLocation(Location.newInstance(s.getId().toString()))
                    .addSizeDimension(0, s.getInternalCost().intValue())
                    .build();
            problemBuilder.addJob(service);
        }
        problemBuilder.setRoutingCost(matrixBuilder.build());
        return problemBuilder.build();
    }

    private List<AggregatedService> aggregateServices(List<TreeBranch<LocationVertex>> branches, DistanceMatrix distanceMatrix){
        List<AggregatedService> services = new LinkedList<>();
        Long id = 2l;
        for(TreeBranch<LocationVertex> branch : branches){
            Double cost = 0.0;
            Double internalDistance = 0.0;
            LocationVertex prev = null;
            branch.getVertices().remove(branch.getStartVertex());
            for(LocationVertex v : branch.getVertices()){
                if(prev != null){
                    internalDistance += distanceMatrix.getDistance(prev.getId(), v.getId());
                }
                cost += v.getDemand();
                prev = v;
            }
            AggregatedService service = new AggregatedService(branch.getVertices(), branch.getVertices().get(0), branch.getEndVertex(), cost, id, internalDistance);
            id++;
            services.add(service);
        }
        return services;
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
        for(Long from = 1l; from <= matrix.getDimensions(); from++){
            for(Long to = 1l; to <= matrix.getDimensions(); to++){
                if(from != to) {
                    matrixCostBuilder.addTransportDistance(from.toString(), to.toString(), matrix.getDistance(from, to));
                }
            }
        }
    }
}
