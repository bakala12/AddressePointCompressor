package compression.services.compression;

//import compression.graphimpl.IGraph;
//import compression.services.branching.ITreeBranchFinder;
//import compression.graphimpl.mst.IMinimalArborescenceFinder;
//import compression.model.vrp.Vehicle;
//import compression.model.vrp.VrpProblem;
//import compression.services.IProblemToGraphConverter;
//import compression.services.compression.graph.LocationEdge;
//import compression.services.compression.graph.LocationGraph;
//import compression.services.compression.graph.LocationVertex;
//import compression.model.graph.TreeBranch;
//import lombok.RequiredArgsConstructor;
//import java.util.LinkedList;
//import java.util.List;
//
//@RequiredArgsConstructor
//public class CompressionService {
//
//    private final IProblemToGraphConverter<LocationVertex, LocationEdge, LocationGraph> graphConverter;
//    private final IMinimalArborescenceFinder minimalArborescenceFinder;
//    private final ITreeBranchFinder<LocationVertex, LocationEdge> treeBranchFinder;
//
//    public List<TreeBranch<LocationVertex>> getAggregatedClients(VrpProblem problem) {
//        ProblemGraph<LocationVertex, LocationEdge, LocationGraph> problemGraph = graphConverter.convert(problem);
//        LocationGraph graph = problemGraph.getGraph();
//        IGraph<LocationVertex, LocationEdge> tree = minimalArborescenceFinder.findMinimalArborescence(graph, problemGraph.getDepotVertex());
//        List<TreeBranch<LocationVertex>> treeBranches = treeBranchFinder.findBranches(tree, problemGraph.getDepotVertex());
//        List<TreeBranch<LocationVertex>> finalBranches = new LinkedList<>();
//        Double maxCapacity = getMexCapavity(problem);
//        for (TreeBranch<LocationVertex> branch : treeBranches) {
//            splitBranchIfNeeded(branch, maxCapacity, finalBranches);
//        }
//        return finalBranches;
//    }
//
//    private Double getMexCapavity(VrpProblem problem){
//        Double maxCapacity = 0.0;
//        for(Vehicle vehicle : problem.getVehicles()){
//            if(maxCapacity < vehicle.getCapacity())
//                maxCapacity = vehicle.getCapacity().doubleValue();
//        }
//        return maxCapacity;
//    }
//
//    private void splitBranchIfNeeded(TreeBranch<LocationVertex> branch, Double maxCapacity, List<TreeBranch<LocationVertex>> finalBrances){
//        Double capacity = 0.0;
//        List<LocationVertex> current = new LinkedList<>();
//        LocationVertex prev = null;
//        for(LocationVertex v : branch.getVertices()){
//            if(v.getDemand()+capacity <= maxCapacity){
//                current.add(v);
//                if(prev != null)
//                    capacity += v.getDemand();
//            } else{
//                TreeBranch<LocationVertex> b = new TreeBranch<>(current.get(0), current.get(current.size()-1), current);
//                finalBrances.add(b);
//                current = new LinkedList<>();
//                if(prev==null)
//                    throw new RuntimeException();
//                current.add(prev);
//                current.add(v);
//                capacity = v.getDemand();
//            }
//            prev = v;
//        }
//        if(current.size()>0){
//            TreeBranch<LocationVertex> b = new TreeBranch<>(current.get(0), current.get(current.size()-1), current);
//            finalBrances.add(b);
//        }
//    }
//}
