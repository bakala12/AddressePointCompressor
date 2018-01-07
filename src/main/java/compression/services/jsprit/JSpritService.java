package compression.services.jsprit;

import compression.model.VRPProblem;
import compression.services.aggregation.IVRPProblemAggregator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JSpritService implements IJSpritService {
    private final IVRPProblemAggregator problemAggregator;

    public void SolveProblem(VRPProblem problem){
        VRPProblem aggreatedProblem = problemAggregator.aggregate(problem);
        
    }
}
