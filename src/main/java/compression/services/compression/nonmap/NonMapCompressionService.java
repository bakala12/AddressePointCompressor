package compression.services.compression.nonmap;

import compression.graph.IGraph;
import compression.graph.IVertex;
import compression.graph.mst.IMinimalSpanningTreeFinder;
import compression.model.vrp.CompressedProblem;
import compression.model.vrp.VrpProblem;
import compression.services.compression.CompressionHelper;
import compression.services.compression.ICompressionService;
import compression.services.compression.IProblemToGraphConverter;
import compression.services.compression.ProblemGraph;
import compression.services.compression.nonmap.graph.LocationEdge;
import compression.services.compression.nonmap.graph.LocationGraph;
import compression.services.compression.nonmap.graph.LocationVertex;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class NonMapCompressionService implements ICompressionService {

    private final IProblemToGraphConverter<LocationGraph> graphConverter;
    private final IMinimalSpanningTreeFinder minimalSpanningTreeFinder;

    @Override
    public CompressedProblem compress(VrpProblem problem) {
        ProblemGraph<LocationGraph> problemGraph = graphConverter.convert(problem);
        LocationGraph graph = problemGraph.getGraph();
        IGraph<LocationVertex, LocationEdge> tree = minimalSpanningTreeFinder.findMinimalSpanningTree(graph);
        Map<IVertex, Boolean> visited = new HashMap<>();
        for(IVertex ver : graph.getAllVertices()){
            visited.put(ver, false);
        }
        LocationVertex depotVertex = graph.getVertex(problem.getDepot().getLocation());
        CompressionHelper.compress(tree, depotVertex);
        return new CompressedProblem(problem, null);
    }
}
