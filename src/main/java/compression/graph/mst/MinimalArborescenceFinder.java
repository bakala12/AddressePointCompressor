package compression.graph.mst;

import com.graphhopper.GraphHopper;
import compression.graph.IEdge;
import compression.graph.IGraph;
import compression.graph.IVertex;
import compression.graph.mst.helpers.EdgeKeeper;
import compression.graph.mst.helpers.GraphKeeper;
import compression.graph.mst.helpers.VertexKeeper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MinimalArborescenceFinder implements IMinimalArborescenceFinder {

    @Override
    public <TVertex extends IVertex, TEdge extends IEdge<TVertex>> IGraph<TVertex, TEdge> findMinimalArborescence(IGraph<TVertex, TEdge> graph, TVertex root) {
        GraphKeeper<TVertex, TEdge> graphKeeper = new GraphKeeper<>(graph.getAllVertices(), graph.getAllEdges());
        GraphKeeper<TVertex, TEdge> arbKeeper = findMinimalArborescence(graphKeeper, root);
        IGraph<TVertex, TEdge> arb = graph.emptyGraph();
        for(EdgeKeeper<TVertex, TEdge> e : arbKeeper.getEdges()){
            arb.addEdge(e.getEdge());
        }
        return arb;
    }

    private <TVertex extends IVertex, TEdge extends IEdge<TVertex>> GraphKeeper<TVertex, TEdge> findMinimalArborescence
            (GraphKeeper<TVertex, TEdge> graph, TVertex root){
        graph.removeDestinationEdges(root);
        GraphKeeper<TVertex, TEdge> p = graph.takeSmallestWeightEdgesForEachVertex();
        List<VertexKeeper<TVertex>> cycle = new LinkedList<>();
        if(!p.findCycle(cycle)){
            return p;
        }
        Map<EdgeKeeper<TVertex, TEdge>, EdgeKeeper<TVertex, TEdge>> edgeMap = new HashMap<>();
        GraphKeeper<TVertex, TEdge> newGraph = graph.shrinkCycle(cycle, edgeMap, p);
        VertexKeeper<TVertex> cycleVertex = newGraph.getVertices().get(newGraph.getVertices().size()-1);
        GraphKeeper<TVertex, TEdge> recResult = findMinimalArborescence(newGraph, root); //A'
        EdgeKeeper<TVertex, TEdge> cycleEdge = recResult.getSingleInputEdge(cycleVertex);
        EdgeKeeper<TVertex, TEdge> oldEdgeInCycle = edgeMap.get(cycleEdge); //(u,v), v z C
        graph.markEdges(recResult, edgeMap, cycle, oldEdgeInCycle.getTo());
        return graph;
    }
}
