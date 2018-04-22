package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import compression.graph.branching.TreeBranch;
import compression.model.disjointset.DisjointSet;
import compression.model.vrp.*;
import compression.services.compression.nonmap.NonMapCompressionService;
import compression.services.compression.nonmap.graph.LocationVertex;
import compression.services.jsprit.extensions.nonmap.AggregatedService;
import lombok.RequiredArgsConstructor;

import java.rmi.activation.ActivationGroup;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class ExplicitMetricCompressionVrpProblemToJSpritConverter
        extends BaseProblemToJSpritConverter
        implements IVrpProblemToJSpritConverter {

    private final NonMapCompressionService compressionService;

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

        return problemBuilder.build();
    }

    private List<AggregatedService> aggregateServices(List<TreeBranch<LocationVertex>> branches, DistanceMatrix distanceMatrix){
        List<AggregatedService> services = new LinkedList<>();
        for(TreeBranch<LocationVertex> branch : branches){
            Double cost = 0.0;
            LocationVertex prev = null;
            for(LocationVertex v : branch.getVertices()){
                if(prev != null){
                    cost += distanceMatrix.getDistance(prev.getId(), v.getId());
                }
                prev = v;
            }
            branch.getVertices().remove(branch.getStartVertex());
            AggregatedService service = new AggregatedService(branch.getVertices(), branch.getVertices().get(0), branch.getEndVertex(), cost);
            services.add(service);
        }
        return services;
    }

    private DistanceMatrix compressMatrix(){
        DistanceMatrix matrix = new DistanceMatrix(0);
        return matrix;
    }
}
