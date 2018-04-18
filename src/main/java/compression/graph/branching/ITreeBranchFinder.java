package compression.graph.branching;

import compression.graph.IEdge;
import compression.graph.IGraph;
import compression.graph.IVertex;

import java.util.List;

public interface ITreeBranchFinder<TVertex extends IVertex, TEdge extends IEdge<TVertex>> {
    List<TreeBranch<TVertex>> findBranches(IGraph<TVertex, TEdge> spanningArborescence, TVertex root);
}
