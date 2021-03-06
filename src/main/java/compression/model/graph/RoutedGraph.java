package compression.model.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 * Represents a graph with selected root vertex.
 * @param <V> Vertex type.
 * @param <E> Edge type.
 */
@AllArgsConstructor
public class RoutedGraph<V,E> {
    @Getter @Setter
    private SimpleDirectedWeightedGraph<V,E> graph;
    @Getter @Setter
    private V root;
}
