package compression.graphnew;

import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public interface IMinimumSpanningArborescenceFinder<V,E> {
    IMinimumSpanningArborescence<V,E> getSpanningArborescence(SimpleDirectedWeightedGraph<V,E> graph, V root);
}
