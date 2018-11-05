package compression.spanning;

import compression.model.graph.IMinimumSpanningArborescence;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 * Defines a way to find minimum spanning arborescence for a given graph.
 * @param <V> Vertex type.
 * @param <E> Edge type.
 */
public interface IMinimumSpanningArborescenceFinder<V,E> {
    /**
     * Finds a minimum spanning arborescence for the given graph.
     * @param graph Graph.
     * @param root Root vertex for the arborescence.
     * @return Minimum spanning arborescence for the given graph.
     */
    IMinimumSpanningArborescence<V,E> getSpanningArborescence(SimpleDirectedWeightedGraph<V,E> graph, V root);
}
