package compression.graph.simple;

import compression.graph.IEdge;
import compression.graph.IGraph;
import compression.graph.IVertex;

import java.util.*;

public abstract class SimpleGraph<TVertex extends IVertex, TEdge extends IEdge<TVertex>>
        implements IGraph<TVertex, TEdge> {

    protected final List<TVertex> vertices;
    protected final Map<TVertex, Integer> verticesMap = new HashMap<>();
    protected final int verticesCount;
    protected final Collection<TVertex> items;

    protected SimpleGraph(Collection<TVertex> items){
        this.items = items;
        verticesCount = items.size();
        vertices = new LinkedList<>();
        int num = 0;
        for(TVertex v : items){
            vertices.add(v);
            verticesMap.put(v, num);
            num++;
        }
    }

    @Override
    public List<TVertex> getAllVertices() {
        return vertices;
    }

    @Override
    public abstract List<TEdge> getAllEdges();

    @Override
    public abstract void addEdge(TEdge edge);

    @Override
    public abstract IGraph<TVertex, TEdge> emptyGraph();
}
