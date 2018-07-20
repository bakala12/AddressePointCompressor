package compression.spanning;

import compression.model.graph.Edge;
import compression.model.graph.IMinimumSpanningArborescence;
import compression.model.structures.DisjointSet;
import compression.model.structures.FibonacciHeap;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.*;

public class TarjanMinimumSpanningArborescenceFinder<V,E>
        extends AbstractMinimumSpanningArborescenceFinder<V,E>
        implements IMinimumSpanningArborescenceFinder<V,E> {

    public TarjanMinimumSpanningArborescenceFinder(){
        super(new IntegerTarjanMinimumSpanningArborescenceFinder());
    }
}

class MaxFHeap<TElem>{
    private FibonacciHeap<TElem> internalHeap;

    public MaxFHeap(){
        internalHeap = new FibonacciHeap<>();
    }

    private MaxFHeap(FibonacciHeap<TElem> heap){
        internalHeap = heap;
    }

    public static <T> MaxFHeap<T> union(MaxFHeap<T> heap1, MaxFHeap<T> heap2){
        return new MaxFHeap<>(FibonacciHeap.merge(heap1.internalHeap, heap2.internalHeap));
    }

    public void insert(TElem elem, double priority){
        internalHeap.enqueue(elem, -priority);
    }

    public FibonacciHeap.Entry<TElem> deleteMax(){
        FibonacciHeap.Entry<TElem> entry = internalHeap.dequeueMin();
        return new FibonacciHeap.Entry<>(entry.getValue(), -entry.getPriority());
    }
}

class IntegerTarjanMinimumSpanningArborescenceFinder
        implements IMinimumSpanningArborescenceFinder<Integer, Edge>{

    @Override
    public IMinimumSpanningArborescence<Integer, Edge> getSpanningArborescence(SimpleDirectedWeightedGraph<Integer, Edge> graph, Integer root) {
        return null;
    }



    //private List<FibonacciHeap<Edge>> heaps;
    //private DisjointSet<Integer> sSets;
    //private DisjointSet<Integer> wSets;
    //private Queue<Integer> roots;
    //private List<Edge> enter;
    //private List<Integer> min;
    //private Set<Integer> rSet;


//    private void init(SimpleDirectedWeightedGraph<Integer, Edge> graph){
//        roots = new PriorityQueue<>();
//        heaps = new ArrayList<>();
//        heaps.add(null);
//        sSets = new DisjointSet<>(graph.vertexSet());
//        wSets = new DisjointSet<>(graph.vertexSet());
//        enter = new ArrayList<>();
//        enter.add(null);
//        min = new ArrayList<>();
//        min.add(null);
//        for(Integer v : graph.vertexSet()){
//            enter.add(null);
//            min.add(v);
//            heaps.add(new FibonacciHeap<>());
//        }
//        roots.addAll(graph.vertexSet());
//        rSet = new HashSet<>();
//        for(Edge e : graph.edgeSet()){
//            FibonacciHeap<Edge> h = heaps.get(e.getTarget());
//            h.enqueue(e, e.getWeight());
//        }
//    }
}
