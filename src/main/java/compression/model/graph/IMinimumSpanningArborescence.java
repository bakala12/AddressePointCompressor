package compression.model.graph;

import java.util.Set;

/**
 * An interface that represents minimal directed spanning tree.
 * @param <V> Vertex type.
 * @param <E> Edge type.
 */
public interface IMinimumSpanningArborescence<V,E> {
    /**
     * Gets the root vertex.
     * @return Root vertex.
     */
    V getRoot();

    /**
     * Gets edges of the spanning arborescence.
     * @return Edges of the spanning arborescence.
     */
    Set<E> getEdges();
}
