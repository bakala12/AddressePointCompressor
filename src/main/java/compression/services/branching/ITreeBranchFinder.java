package compression.services.branching;

import compression.model.graph.Edge;
import compression.model.graph.IMinimumSpanningArborescence;
import compression.model.graph.TreeBranch;

import java.util.List;

/**
 * Defines a method for extracting tree branches from minimal spanning arborescence.
 * @param <V> Vertex type.
 */
public interface ITreeBranchFinder<V> {
    /**
     * Extracts a list of branches from minimal spanning arborescence.
     * @param arborescence Minimal spanning arborescence.
     * @return List of tree branches.
     */
    List<TreeBranch<V>> findBranches(IMinimumSpanningArborescence<V,Edge> arborescence);
}
