package compression.graph.mst;

import compression.graph.IEdge;
import compression.graph.IGraph;
import compression.graph.IVertex;

public interface IMinimalArborescenceFinder {
    <TVertex extends IVertex, TEdge extends IEdge<TVertex>> IGraph<TVertex, TEdge>
    findMinimalArborescence(IGraph<TVertex, TEdge> graph, TVertex root);
}
