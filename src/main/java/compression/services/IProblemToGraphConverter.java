package compression.services;

import compression.model.graph.Edge;
import compression.model.vrp.VrpProblem;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

public interface IProblemToGraphConverter<TVertex> {
    SimpleDirectedWeightedGraph<TVertex, Edge> convert(VrpProblem problem);
}
