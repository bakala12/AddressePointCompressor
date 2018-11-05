package compression.services;

import compression.model.graph.Edge;
import compression.model.graph.RoutedGraph;
import compression.model.vrp.VrpProblem;

/**
 * Defines method for converting VRP problem instance to a graph.
 * @param <TVertex> Type of vertex.
 */
public interface IProblemToGraphConverter<TVertex> {
    /**
     * Converts VRP problem to graph.
     * @param problem VRP problem.
     * @return Graph.
     */
    RoutedGraph<TVertex, Edge> convert(VrpProblem problem);
}
