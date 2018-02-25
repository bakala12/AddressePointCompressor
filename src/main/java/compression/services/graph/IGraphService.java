package compression.services.graph;

import compression.model.graph.LocationGraph;
import compression.model.vrp.VrpProblem;

public interface IGraphService {
    LocationGraph minimalSpanningTree(VrpProblem problem);
}
