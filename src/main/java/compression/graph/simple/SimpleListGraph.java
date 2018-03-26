package compression.graph.simple;

import compression.graph.IGraph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SimpleListGraph<T>
        extends SimpleGraph<T>
        implements IGraph<SimpleVertex<T>, SimpleEdge<SimpleVertex<T>>> {

    private final List<SimpleEdge<SimpleVertex<T>>>[] edges;

    public SimpleListGraph(Collection<T> items){
        super(items);
        edges = (List<SimpleEdge<SimpleVertex<T>>>[]) new Object[verticesCount];
        for(int i =0; i<verticesCount; i++){
            edges[i] = new LinkedList<>();
        }
    }

    @Override
    public List<SimpleEdge<SimpleVertex<T>>> getAllEdges() {
        LinkedList<SimpleEdge<SimpleVertex<T>>> edgeList = new LinkedList<>();
        for(int from = 0; from<verticesCount; from++){
            for(SimpleEdge<SimpleVertex<T>> e : edges[from]){
                if(e != null)
                    edgeList.add(e);
            }
        }
        return edgeList;
    }

    @Override
    public void addEdge(SimpleEdge<SimpleVertex<T>> edge) {
        int from = verticesMap.get(edge.getFrom());
        List<SimpleEdge<SimpleVertex<T>>> neighbours = edges[from];
        neighbours.remove(edge);
        neighbours.add(edge);
    }

    @Override
    public IGraph<SimpleVertex<T>, SimpleEdge<SimpleVertex<T>>> emptyGraph() {
        return new SimpleListGraph<T>(this.items);
    }
}
