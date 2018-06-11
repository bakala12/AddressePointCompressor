package compression.graphnew;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.*;

public class TarjanMinimumArborescenceFinder<V,E>
    extends AbstractMinimumArborescenceFinder<V,E>
    implements IMinimumSpanningArborescenceFinder<V,E>{

    public TarjanMinimumArborescenceFinder(){
        super(new IntegerTarjanMinimumArborescenceFinder());
    }
}

@RequiredArgsConstructor
class SuperVertexKeeper {
    @Getter
    private final Integer v;
    @Getter @Setter
    private Edge in;
    @Getter @Setter
    private Double cons;
    @Getter @Setter
    private Integer prev;
    @Getter @Setter
    private Integer parent;
    @Getter @Setter
    private List<Integer> children;
    @Getter @Setter
    private FibonacciHeap<Edge> p;
}

class SuperVertexManager {
    private ArrayList<SuperVertexKeeper> list;
    private int vertices;

    public SuperVertexManager(){
        list = new ArrayList<>();
        list.add(new SuperVertexKeeper(-1)); //guard
        vertices = 0;
    }

    public SuperVertexKeeper get(Integer v){
        return list.get(v);
    }

    public SuperVertexKeeper add(){
        Integer c = vertices +1;
        vertices++;
        SuperVertexKeeper vk = new SuperVertexKeeper(c);
        list.add(vk);
        return vk;
    }

    protected void add(SuperVertexKeeper keeper){
        list.add(keeper);
        vertices++;
    }

    public List<Edge> getAllInEdgesExceptRoot(Integer root, Integer n){
        List<Edge> e = new ArrayList<>();
        int i=0;
        for(SuperVertexKeeper vk : list){
            if(i==0) {
                i++;
                continue;
            }
            if(i>n) break;
            if(vk.getV() != root){
                e.add(vk.getIn());
            }
            i++;
        }
        return e;
    }
}

class IntegerTarjanMinimumArborescenceFinder
    implements IMinimumSpanningArborescenceFinder<Integer, Edge>{

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
        Integer a = 2;
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
                if(v != u){
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