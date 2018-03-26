package compression.services.aggregation.nonmap;

import compression.model.vrp.VrpProblem;
import compression.services.aggregation.IProblemToGraphConverter;
import compression.services.aggregation.ProblemGraph;
import compression.services.aggregation.nonmap.graph.LocationGraph;

public class NonMapProblemToGraphConverter implements IProblemToGraphConverter<LocationGraph>{

    @Override
    public ProblemGraph<LocationGraph> convert(VrpProblem problem) {
        return new ProblemGraph<>(problem, null);
    }

    @Override
    public VrpProblem convertBack(ProblemGraph<LocationGraph> compressedProblem) {
        return compressedProblem.getProblem();
    }
}
