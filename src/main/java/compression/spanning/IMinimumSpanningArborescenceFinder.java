package compression.spanning;

import compression.model.graph.IMinimumSpanningArborescence;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public interface IMinimumSpanningArborescenceFinder<V,E> {
    IMinimumSpanningArborescence<V,E> getSpanningArborescence(SimpleDirectedWeightedGraph<V,E> graph, V root);
}
