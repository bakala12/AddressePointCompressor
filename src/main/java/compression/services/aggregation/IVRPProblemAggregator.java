package compression.services.aggregation;

import compression.model.VRPProblem;

public interface IVRPProblemAggregator {
    VRPProblem aggregate(VRPProblem problem);
}
