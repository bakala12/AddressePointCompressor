package compression.branching;

import compression.model.graph.Edge;
import compression.model.graph.IMinimumSpanningArborescence;
import compression.model.graph.TreeBranch;

import java.util.List;

public class TreeBranchFinder<V>
        implements ITreeBranchFinder<V>{
    @Override
    public List<TreeBranch<V>> findBranches(IMinimumSpanningArborescence<V,Edge> arborescence) {
        return null;
    }

//    @Override
//    public List<TreeBranch<TVertex>> findBranches(IGraph<TVertex, TEdge> spanningArborescence, TVertex root) {
//        Map<TVertex, Boolean> visited = new HashMap<>();
//        for(TVertex v : spanningArborescence.getAllVertices()){
//            visited.put(v, false);
//        }
//        List<List<TVertex>> allBranches = new LinkedList<>();
//        List<TVertex> currentBranch = new LinkedList<>();
//        findBranches(spanningArborescence, root, visited, currentBranch, allBranches);
//        List<TreeBranch<TVertex>> treeBranches = new LinkedList<>();
//        for(List<TVertex> l : allBranches){
//            treeBranches.add(new TreeBranch(l.get(0), l.get(l.size()-1), l));
//        }
//        return treeBranches;
//    }
//
//    private void findBranches(IGraph<TVertex, TEdge> tree, TVertex current, Map<TVertex, Boolean> visited,
//                      List<TVertex> currentBranch, List<List<TVertex>> allBranches){
//
//        visited.put(current, true);
//        List<TEdge> outEdges = outputEdges(tree, current, visited);
//        currentBranch.add(current);
//        if(outEdges.size() == 0) {
//            allBranches.add(currentBranch);
//            return;
//        }
//        if(outEdges.size()==1){
//            findBranches(tree, outEdges.get(0).getTo(), visited, currentBranch, allBranches);
//        }
//        else {
//            if(currentBranch.size()>1)
//                allBranches.add(currentBranch);
//            for(TEdge edge : outEdges){
//                List<TVertex> branch = new LinkedList<>();
//                branch.add(current);
//                findBranches(tree, edge.getTo(), visited, branch, allBranches);
//            }
//        }
//    }
//
//    private List<TEdge> outputEdges(IGraph<TVertex, TEdge> graph, TVertex vertex, Map<TVertex, Boolean> visited){
//        List<TEdge> edges = new LinkedList<>();
//        for(TEdge e : graph.getEdgesFrom(vertex)){
//            if(!visited.get(e.getTo())){
//                edges.add(e);
//            }
//        }
//        return edges;
//    }
}
