package compression.graph.mst;

import compression.model.disjointset.DisjointSet;
import compression.graph.IEdge;
import compression.graph.IGraph;
import compression.graph.IVertex;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MinimalSpanningTreeFinder implements IMinimalSpanningTreeFinder{

    @Override
    public <TVertex extends IVertex, TEdge extends IEdge<TVertex>> IGraph<TVertex, TEdge> findMinimalSpanningTree(IGraph<TVertex, TEdge> graph){
        List<TEdge> allEdges = graph.getAllEdges();
        Collections.sort(allEdges, new EdgesComparator<>());
        IGraph<TVertex, TEdge> tree = graph.emptyGraph();
        List<TVertex> allVertices = graph.getAllVertices();
        DisjointSet<TVertex> forest = new DisjointSet<>(allVertices);
        int edgesCount = 0;
        while(edgesCount < allVertices.size()-1){
            TEdge edge = allEdges.remove(0);
            TVertex v1 = forest.find(edge.getFrom());
            TVertex v2 = forest.find(edge.getTo());
            if(v1 != v2){
                tree.addEdge(edge);
                forest.union(v1,v2);
                edgesCount++;
            }
        }
        return tree;
    }

    static class EdgesComparator<TVertex extends IVertex, TEdge extends IEdge<TVertex>> implements Comparator<TEdge>{
        @Override
        public int compare(TEdge o1, TEdge o2) {
            Double diff = o1.getWeight() - o2.getWeight();
            if (diff == 0)
                return 0;
            return diff>0 ? 1 : -1;
        }
    }
}
