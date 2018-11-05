package compression.model.graph;

import java.util.Set;

/**
 * A class that represents minimum spanning arborescence.
 * @param <V> Vertex type.
 * @param <E> Edge type.
 */
public class DefaultMinimumSpanningArborescence<V, E>
    implements IMinimumSpanningArborescence<V, E> {

    private V root;
    private Set<E> edges;

    /**
     * Initializes a new instance of the class.
     * @param root Root vertex.
     * @param edges Edges of the spanning arborescence.
     */
    public DefaultMinimumSpanningArborescence(V root, Set<E> edges){
        this.root = root;
        this.edges = edges;
    }

    /**
     * Gets the root vertex.
     * @return Root vertex.
     */
    @Override
    public V getRoot() {
        return root;
    }

    /**
     * Gets edges of the spanning arborescence.
     * @return Edges of the spanning arborescence.
     */
    @Override
    public Set<E> getEdges() {
        return edges;
    }
}
