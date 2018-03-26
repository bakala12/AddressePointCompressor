package compression.graph;

import java.util.List;

public interface IGraph<TVertex extends IVertex, TEdge extends IEdge<TVertex>> {
    List<TVertex> getAllVertices();
    List<TEdge> getAllEdges();
    void addEdge(TEdge edge);
    IGraph<TVertex, TEdge> emptyGraph();
}
