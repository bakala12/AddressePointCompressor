package compression.graph.simple;

import compression.graph.IGraph;

import java.util.*;

public class SimpleMatrixGraph<T>
        extends SimpleGraph<T>
        implements IGraph<SimpleVertex<T>, SimpleEdge<SimpleVertex<T>>> {

    private final SimpleEdge<SimpleVertex<T>>[][] edges;

    public SimpleMatrixGraph(Collection<T> items){
        super(items);
        edges = (SimpleEdge<SimpleVertex<T>>[][]) new Object[verticesCount][verticesCount];
    }

    @Override
    public List<SimpleEdge<SimpleVertex<T>>> getAllEdges() {
        LinkedList<SimpleEdge<SimpleVertex<T>>> edgeList = new LinkedList<>();
        for(int from = 0; from<verticesCount; from++){
            for(int to = 0; to<verticesCount; to++){
                SimpleEdge<SimpleVertex<T>> e = edges[from][to];
                if(e != null)
                    edgeList.add(e);
            }
        }
        return edgeList;
    }

    @Override
    public void addEdge(SimpleEdge<SimpleVertex<T>> edge) {
        int from = verticesMap.get(edge.getFrom());
        int to = verticesMap.get(edge.getTo());
        edges[from][to] = edge;
    }

    @Override
    public IGraph<SimpleVertex<T>, SimpleEdge<SimpleVertex<T>>> emptyGraph(){
        return new SimpleMatrixGraph<>(this.items);
    }
}
