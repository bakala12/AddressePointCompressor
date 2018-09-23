package compression.services.compression;

import com.graphhopper.jsprit.core.util.StopWatch;
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
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
public class CompressionService implements ICompressionService{

    private final IProblemToGraphConverter<LocationVertex> problemConverter;
    private final IMinimumSpanningArborescenceFinder<LocationVertex, Edge> minimumSpanningArborescenceFinder;
    private final ITreeBranchFinder<LocationVertex> treeBranchFinder;

    @Override
    public CompressionResult getAggregatedClients(VrpProblem problem) {
        StopWatch watch = new StopWatch();
        RoutedGraph<LocationVertex, Edge> graph = problemConverter.convert(problem);
        watch.reset();
        watch.start();
        IMinimumSpanningArborescence<LocationVertex, Edge> spanningArborescence = minimumSpanningArborescenceFinder.getSpanningArborescence(graph.getGraph(), graph.getRoot());
        List<TreeBranch<LocationVertex>> branches = treeBranchFinder.findBranches(spanningArborescence);
        branches.sort((o1, o2) -> (int)(o1.getVertices().get(1).getId()-o2.getVertices().get(1).getId()));
        List<TreeBranch<LocationVertex>> finalBranches = new ArrayList<>();
        Double maxCapacity = getMexCapavity(problem);
        for (TreeBranch<LocationVertex> branch : branches) {
            splitBranchIfNeeded(branch, maxCapacity, finalBranches);
        }
        watch.stop();
        Double time = watch.getCurrTimeInSeconds();
        List<AggregatedService> list = new ArrayList<>();
        Long id = 2L;
        for(TreeBranch<LocationVertex> v : finalBranches){
            Double dist = 0.0;
            v.getVertices().remove(0);
            LocationVertex prev = v.getVertices().get(0);
            Double cost = prev.getDemand();
            Double backwardDist = 0.0;
            for(LocationVertex vv : v.getVertices()){
                if(vv==prev) continue;
                cost += vv.getDemand();
                dist += problem.getDistanceMatrix().getDistance(prev.getId(), vv.getId());
                backwardDist += problem.getDistanceMatrix().getDistance(vv.getId(), prev.getId());
                prev = vv;
            }
            AggregatedService s = new AggregatedService(v.getVertices(), v.getVertices().get(0), v.getEndVertex(), cost, id, dist, backwardDist);
            id = id + 1;
            list.add(s);
        }
        return new CompressionResult(list, time);
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
