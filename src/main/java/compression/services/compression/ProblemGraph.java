package compression.services.compression;

import compression.graph.IGraph;
import compression.model.vrp.VrpProblem;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ProblemGraph<TGraph extends IGraph> {
    @Getter
    private VrpProblem problem;
    @Getter
    private TGraph graph;
}
