package compression.services.branching;

import compression.model.graph.Edge;
import compression.model.graph.IMinimumSpanningArborescence;
import compression.model.graph.TreeBranch;

import java.util.List;

public interface ITreeBranchFinder<V> {
    List<TreeBranch<V>> findBranches(IMinimumSpanningArborescence<V,Edge> arborescence);
}
