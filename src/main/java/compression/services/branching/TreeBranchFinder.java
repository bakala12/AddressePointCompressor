package compression.services.branching;

import compression.model.graph.Edge;
import compression.model.graph.IMinimumSpanningArborescence;
import compression.model.graph.TreeBranch;
import compression.model.structures.FibonacciHeap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

public class TreeBranchFinder<V>
        implements ITreeBranchFinder<V> {

    private static class TreeNode<V>{
        @Getter
        private final V value;
        @Getter
        private final List<Edge> edges;

        public TreeNode(V value){
            this.value = value;
            this.edges = new ArrayList<>();
        }
    }

    @Override
    public List<TreeBranch<V>> findBranches(IMinimumSpanningArborescence<V,Edge> arborescence) {
        TreeNode<V> root = new TreeNode(arborescence.getRoot());
        Map<V, TreeNode<V>> nodeMap = new HashMap<>();
        nodeMap.put(arborescence.getRoot(), root);
        for(Edge e : arborescence.getEdges()){
            TreeNode<V> n = new TreeNode<>(e.getTarget());
            nodeMap.put(e.getTarget(), n);
        }
        for(Edge e : arborescence.getEdges()){
            TreeNode<V> n = nodeMap.get(e.getSource());
            n.edges.add(e);
        }
        Map<V, Boolean> visited = new HashMap<>();
        for(Map.Entry<V, TreeNode<V>> e : nodeMap.entrySet()){
            visited.put(e.getKey(), false);
        }
        List<List<V>> allBranches = new ArrayList<>();
        List<V> currentBranch = new ArrayList<>();
        findBranches(nodeMap, root.value, visited, currentBranch, allBranches);
        List<TreeBranch<V>> treeBranches = new ArrayList<>();
        for(List<V> l : allBranches){
            treeBranches.add(new TreeBranch(l.get(0), l.get(l.size()-1), l));
        }
        return treeBranches;
    }

    private void findBranches(Map<V, TreeNode<V>> tree, V current, Map<V, Boolean> visited,
                      List<V> currentBranch, List<List<V>> allBranches){
        visited.put(current, true);
        currentBranch.add(current);
        TreeNode<V> node = tree.get(current);
        if(node.edges.size() == 0) {
            allBranches.add(currentBranch);
            return;
        }
        if(node.edges.size()==1){
            findBranches(tree, node.edges.get(0).getTarget(), visited, currentBranch, allBranches);
        }
        else {
            if(currentBranch.size()>1)
                allBranches.add(currentBranch);
            for(Edge edge : node.edges){
                List<V> branch = new ArrayList<>();
                branch.add(current);
                findBranches(tree, edge.getTarget(), visited, branch, allBranches);
            }
        }
    }
}
