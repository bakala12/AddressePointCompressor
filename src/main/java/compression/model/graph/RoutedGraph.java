package compression.model.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

@AllArgsConstructor
public class RoutedGraph<V,E> {
    @Getter @Setter
    private SimpleDirectedWeightedGraph<V,E> graph;
    @Getter @Setter
    private V root;
}
