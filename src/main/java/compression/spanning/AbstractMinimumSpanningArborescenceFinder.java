package compression.spanning;

import compression.model.graph.DefaultMinimumSpanningArborescence;
import compression.model.graph.Edge;
import compression.model.graph.IMinimumSpanningArborescence;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class AbstractMinimumSpanningArborescenceFinder<V,E>
    implements IMinimumSpanningArborescenceFinder<V,E> {

    private final IMinimumSpanningArborescenceFinder<Integer, Edge> internalFinder;

    protected AbstractMinimumSpanningArborescenceFinder(IMinimumSpanningArborescenceFinder<Integer, Edge> internalFinder){
        this.internalFinder = internalFinder;
    }

    @Override
    public IMinimumSpanningArborescence<V, E> getSpanningArborescence(SimpleDirectedWeightedGraph<V, E> graph, V root) {
        SimpleDirectedWeightedGraph<Integer, Edge> intG = new SimpleDirectedWeightedGraph<>(Edge.class);
        Integer id = 1;
        Map<V, Integer> vertexMap = new HashMap<>();
        Map<Integer, V> reverseMap = new HashMap<>();
        for(V v : graph.vertexSet()){
            intG.addVertex(id);
            vertexMap.put(v, id);
            reverseMap.put(id, v);
            id = new Integer(id+1);
        }
        for(E e : graph.edgeSet()){
            V s = graph.getEdgeSource(e);
            V t = graph.getEdgeTarget(e);
            double w = graph.getEdgeWeight(e);
            Edge ee = new Edge(vertexMap.get(s), vertexMap.get(t), w);
            intG.addEdge(ee.getSource(), ee.getTarget(), ee);
        }
        IMinimumSpanningArborescence<Integer, Edge> intArb = internalFinder.getSpanningArborescence(intG, vertexMap.get(root));
        Set<E> edges = new HashSet<>();
        for(Edge e : intArb.getEdges()){
            edges.add(graph.getEdge(reverseMap.get(e.getSource()), reverseMap.get(e.getTarget())));
        }
        return new DefaultMinimumSpanningArborescence<>(root, edges);
    }
}
