package compression.services;

import compression.model.graph.Edge;
import compression.model.graph.RoutedGraph;
import compression.model.vrp.VrpProblem;

public interface IProblemToGraphConverter<TVertex> {
    RoutedGraph<TVertex, Edge> convert(VrpProblem problem);
}
