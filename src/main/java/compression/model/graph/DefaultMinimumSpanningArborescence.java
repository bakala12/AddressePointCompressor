package compression.model.graph;

import java.util.Set;

public class DefaultMinimumSpanningArborescence<V, E>
    implements IMinimumSpanningArborescence<V, E> {

    private V root;
    private Set<E> edges;

    public DefaultMinimumSpanningArborescence(V root, Set<E> edges){
        this.root = root;
        this.edges = edges;
    }

    @Override
    public V getRoot() {
        return root;
    }

    @Override
    public Set<E> getEdges() {
        return edges;
    }
}
