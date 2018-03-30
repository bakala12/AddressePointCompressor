package compression.services.compression;

import compression.graph.IEdge;
import compression.graph.IGraph;
import compression.graph.IVertex;

import java.util.*;

public final class CompressionHelper {
    public static <TVertex extends IVertex, TEdge extends IEdge<TVertex>>
            void compress(IGraph<TVertex, TEdge> tree, TVertex startVertex){
        Map<TVertex, Boolean> visited = new HashMap<>();
        for(TVertex v : tree.getAllVertices()){
            visited.put(v, false);
        }
        List<List<TVertex>> allBranches = new LinkedList<>();
        List<TVertex> currentBranch = new LinkedList<>();
        findBranches(tree, startVertex, visited, currentBranch, allBranches);
        System.out.println("TreeBranches");
        for(List<TVertex> l : allBranches){
            for(TVertex v : l){
                System.out.print(v.toString() + " ");
            }
            System.out.println();
        }
    }

    public static <TVertex extends IVertex, TEdge extends IEdge<TVertex>>
        void findBranches(IGraph<TVertex, TEdge> tree, TVertex current, Map<TVertex, Boolean> visited,
                          List<TVertex> currentBranch, List<List<TVertex>> allBranches){

        visited.put(current, true);
        List<TEdge> outEdges = outputEdges(tree, current, visited);
        currentBranch.add(current);
        if(outEdges.size() == 0) {
            allBranches.add(currentBranch);
            return;
        }
        if(outEdges.size()==1){
            findBranches(tree, outEdges.get(0).getTo(), visited, currentBranch, allBranches);
        }
        else {
            if(currentBranch.size()>1)
                allBranches.add(currentBranch);
            for(TEdge edge : outEdges){
                List<TVertex> branch = new LinkedList<>();
                branch.add(current);
                findBranches(tree, edge.getTo(), visited, branch, allBranches);
            }
        }
    }

    static <TVertex extends IVertex, TEdge extends IEdge<TVertex>>
        List<TEdge> outputEdges(IGraph<TVertex, TEdge> graph, TVertex vertex, Map<TVertex, Boolean> visited){
        List<TEdge> edges = new LinkedList<>();
        for(TEdge e : graph.getEdgesFrom(vertex)){
            if(!visited.get(e.getTo())){
                edges.add(e);
            }
        }
        return edges;
    }
}
