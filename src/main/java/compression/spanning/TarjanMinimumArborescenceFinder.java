package compression.spanning;

import compression.model.graph.DefaultMinimumSpanningArborescence;
import compression.model.graph.Edge;
import compression.model.graph.IMinimumSpanningArborescence;
import compression.model.structures.FibonacciHeap;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.*;

/**
 * Implementation of Tarjan algorithm for finding minimum spanning arborescence.
 * @param <V> Vertex type.
 * @param <E> Edge type.
 */
public class TarjanMinimumArborescenceFinder<V,E>
    extends AbstractMinimumSpanningArborescenceFinder<V,E>
    implements IMinimumSpanningArborescenceFinder<V,E> {

    /**
     * Initialzes a new instance of TarjanMinimumArborescenceFinder.
     */
    public TarjanMinimumArborescenceFinder(){
        super(new IntegerTarjanMinimumArborescenceFinder());
    }
}

/**
 * Implementation of Tarjan algorithm for finding minimum spanning arborescence.
 */
class IntegerTarjanMinimumArborescenceFinder
    implements IMinimumSpanningArborescenceFinder<Integer, Edge>{

    /**
     * Finds a minimum spanning arborescence for the given graph using Tarjan algorithm.
     * @param graph Graph.
     * @param root Root vertex for the arborescence.
     * @return Minimum spanning arborescence for the given graph.
     */
    @Override
    public IMinimumSpanningArborescence<Integer, Edge> getSpanningArborescence(SimpleDirectedWeightedGraph<Integer, Edge> graph, Integer root) {
        contract(graph);
        return expand(root, graph.vertexSet().size());
    }

    private SuperVertexManager vertexManager;

    private void initVertex(Integer v){
        SuperVertexKeeper k = new SuperVertexKeeper(v);
        k.setIn(null);
        k.setCons(0.0);
        k.setPrev(null);
        k.setParent(null);
        k.setChildren(null);
        k.setP(new FibonacciHeap<>());
        vertexManager.add(k);
    }

    private void initialize(SimpleDirectedWeightedGraph<Integer, Edge> graph){
        vertexManager = new SuperVertexManager();
        for(Integer v : graph.vertexSet()){
            initVertex(v);
        }
        for(Edge e : graph.edgeSet()){
            SuperVertexKeeper k = vertexManager.get(e.getTarget());
            k.getP().enqueue(e, e.getWeight());
        }
    }

    private void contract(SimpleDirectedWeightedGraph<Integer, Edge> graph){
        initialize(graph);
        Integer a = 1;
        SuperVertexKeeper ak = vertexManager.get(a);
        while (!ak.getP().isEmpty()){
            Edge e = ak.getP().dequeueMin().getValue();
            Integer u = e.getSource();
            Integer b = find(u);
            if(a!=b){
                ak.setIn(e);
                ak.setPrev(b);
                if(vertexManager.get(u).getIn() == null){
                    a = b;
                    ak = vertexManager.get(a);
                } else{
                    SuperVertexKeeper ck = vertexManager.add();
                    Integer c = ck.getV();
                    ck.setP(new FibonacciHeap<>());
                    ck.setCons(0.0);
                    ck.setParent(null);
                    ck.setPrev(null);
                    ck.setIn(null);
                    ck.setChildren(null);
                    while(ak.getParent()==null){
                        ak.setParent(c);
                        ak.setCons(-ak.getIn().getWeight());
                        if(ck.getChildren() == null)
                            ck.setChildren(new ArrayList<>());
                        ck.getChildren().add(a);
                        FibonacciHeap<Edge> pck = FibonacciHeap.merge(ck.getP(), ak.getP());
                        ck.setP(pck);
                        a = ak.getPrev();
                        ak = vertexManager.get(a);
                    }
                    a = c;
                    ak = ck;
                }
            }
        }
    }

    //todo - use Union Find
    private Integer find(Integer u){
        SuperVertexKeeper uk = vertexManager.get(u);
        while(uk.getParent() != null){
            u = uk.getParent();
            uk = vertexManager.get(u);
        }
        return uk.getV();
    }

    //todo - use union find
    private Double weight(Edge e){
        Integer u = e.getSource();
        Integer v = e.getTarget();
        Double w = e.getWeight();
        SuperVertexKeeper vk = vertexManager.get(v);
        while(vk.getParent() != null){
            w += vk.getCons();
            v = vk.getParent();
            vk = vertexManager.get(v);
        }
        return w;
    }

    private void dismantle(Integer u, Stack<Integer> R){
        SuperVertexKeeper uk = vertexManager.get(u);
        while(uk.getParent()!=null){
            SuperVertexKeeper ukp = vertexManager.get(uk.getParent());
            for(Integer v : ukp.getChildren()){
                if(!v.equals(u)){
                    SuperVertexKeeper vk = vertexManager.get(v);
                    vk.setParent(null);
                    if(vk.getChildren() != null){
                        R.push(v);
                    }
                }
            }
            u = uk.getParent();
            uk = ukp;
        }
    }

    private IMinimumSpanningArborescence<Integer,Edge> expand(Integer r, int vertices){
        Stack<Integer> R = new Stack<>();
        dismantle(r, R);
        while(!R.isEmpty()){
            Integer c = R.pop();
            SuperVertexKeeper ck = vertexManager.get(c);
            Edge e = ck.getIn();
            Integer v = e.getTarget();
            SuperVertexKeeper vk = vertexManager.get(v);
            vk.setIn(e);
            dismantle(v, R);
        }
        Set<Edge> inc = new HashSet<>();
        inc.addAll(vertexManager.getAllInEdgesExceptRoot(r, vertices));
        IMinimumSpanningArborescence<Integer, Edge> arb = new DefaultMinimumSpanningArborescence<>(r, inc);
        return arb;
    }
}