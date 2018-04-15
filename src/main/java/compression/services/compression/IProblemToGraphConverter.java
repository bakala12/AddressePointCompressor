package compression.services.compression;

import compression.graph.IEdge;
import compression.graph.IGraph;
import compression.graph.IVertex;
import compression.model.vrp.VrpProblem;

public interface IProblemToGraphConverter<TVertex extends IVertex, TEdge extends IEdge<TVertex>, TGraph extends IGraph<TVertex, TEdge>> {
    ProblemGraph<TVertex, TEdge, TGraph> convert(VrpProblem problem);
}
