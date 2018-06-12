package compression.services.compression;

import compression.model.graph.*;
import compression.model.vrp.Vehicle;
import compression.model.vrp.VrpProblem;
import compression.model.vrp.helpers.LocationVertex;
import compression.services.IProblemToGraphConverter;
import compression.services.branching.ITreeBranchFinder;
import compression.model.vrp.helpers.AggregatedService;
import compression.spanning.IMinimumSpanningArborescenceFinder;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CompressionService implements ICompressionService{

    private final IProblemToGraphConverter<LocationVertex> problemConverter;
    private final IMinimumSpanningArborescenceFinder<LocationVertex, Edge> minimumSpanningArborescenceFinder;
    private final ITreeBranchFinder<LocationVertex> treeBranchFinder;

    @Override
    public List<AggregatedService> getAggregatedClients(VrpProblem problem) {
        RoutedGraph<LocationVertex, Edge> graph = problemConverter.convert(problem);
        IMinimumSpanningArborescence<LocationVertex, Edge> spanningArborescence = minimumSpanningArborescenceFinder.getSpanningArborescence(graph.getGraph(), graph.getRoot());
        List<TreeBranch<LocationVertex>> branches = treeBranchFinder.findBranches(spanningArborescence);
        List<TreeBranch<LocationVertex>> finalBranches = new ArrayList<>();
        Double maxCapacity = getMexCapavity(problem);
        for (TreeBranch<LocationVertex> branch : branches) {
            splitBranchIfNeeded(branch, maxCapacity, finalBranches);
        }
        List<AggregatedService> list = new ArrayList<>();
        return null;
    }

    private Double getMexCapavity(VrpProblem problem){
        Double maxCapacity = 0.0;
        for(Vehicle vehicle : problem.getVehicles()){
            if(maxCapacity < vehicle.getCapacity())
                maxCapacity = vehicle.getCapacity().doubleValue();
        }
        return maxCapacity;
    }

    private void splitBranchIfNeeded(TreeBranch<LocationVertex> branch, Double maxCapacity, List<TreeBranch<LocationVertex>> finalBrances){
        Double capacity = 0.0;
        List<LocationVertex> current = new ArrayList<>();
        LocationVertex prev = null;
        for(LocationVertex v : branch.getVertices()){
            if(v.getDemand()+capacity <= maxCapacity){
                current.add(v);
                if(prev != null)
                    capacity += v.getDemand();
            } else{
                TreeBranch<LocationVertex> b = new TreeBranch<>(current.get(0), current.get(current.size()-1), current);
                finalBrances.add(b);
                current = new ArrayList<>();
                if(prev==null)
                    throw new RuntimeException();
                current.add(prev);
                current.add(v);
                capacity = v.getDemand();
            }
            prev = v;
        }
        if(current.size()>0){
            TreeBranch<LocationVertex> b = new TreeBranch<>(current.get(0), current.get(current.size()-1), current);
            finalBrances.add(b);
        }
    }
}
