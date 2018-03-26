package compression.graph.mst;

import compression.graph.IEdge;
import compression.graph.IGraph;
import compression.graph.IVertex;

public interface IMinimalSpanningTreeFinder {
    <TVertex extends IVertex, TEdge extends IEdge<TVertex>> IGraph<TVertex, TEdge> findMinimalSpanningTree(IGraph<TVertex, TEdge> graph);
}
