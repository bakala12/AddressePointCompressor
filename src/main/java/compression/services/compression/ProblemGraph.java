package compression.services.compression;

import compression.graph.IEdge;
import compression.graph.IGraph;
import compression.graph.IVertex;
import compression.model.vrp.VrpProblem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ProblemGraph<TVertex extends IVertex, TEdge extends IEdge<TVertex>, TGraph extends IGraph<TVertex, TEdge>> {
    @Getter
    private VrpProblem problem;
    @Getter
    private TGraph graph;
    @Getter
    private TVertex depotVertex;
}
