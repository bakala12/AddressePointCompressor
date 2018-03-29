package compression.services.compression;

import compression.graph.IGraph;
import compression.model.vrp.VrpProblem;

public interface IProblemToGraphConverter<TGraph extends IGraph> {
    ProblemGraph<TGraph> convert(VrpProblem problem);
}
