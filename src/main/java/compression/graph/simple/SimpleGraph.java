package compression.graph.simple;

import compression.graph.IGraph;

import java.util.*;

public abstract class SimpleGraph<T> implements IGraph<SimpleVertex<T>, SimpleEdge<SimpleVertex<T>>> {

    protected final List<SimpleVertex<T>> vertices;
    protected final Map<SimpleVertex<T>, Integer> verticesMap = new HashMap<>();
    protected final int verticesCount;
    protected final Collection<T> items;

    protected SimpleGraph(Collection<T> items){
        this.items = items;
        verticesCount = items.size();
        vertices = new LinkedList<>();
        int num = 0;
        for(T i : items){
            SimpleVertex<T> v = new SimpleVertex<>(i);
            vertices.add(v);
            verticesMap.put(v, num);
            num++;
        }
    }

    @Override
    public List<SimpleVertex<T>> getAllVertices() {
        return vertices;
    }

    @Override
    public abstract List<SimpleEdge<SimpleVertex<T>>> getAllEdges();

    @Override
    public abstract void addEdge(SimpleEdge<SimpleVertex<T>> edge);

    @Override
    public abstract IGraph<SimpleVertex<T>, SimpleEdge<SimpleVertex<T>>> emptyGraph();
}
