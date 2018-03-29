package compression.graph.simple;

import compression.graph.IEdge;
import compression.graph.IGraph;
import compression.graph.IVertex;

import javax.swing.text.EditorKit;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class SimpleListGraph<TVertex extends IVertex, TEdge extends IEdge<TVertex>>
        extends SimpleGraph<TVertex, TEdge> {

    private final List<TEdge>[] edges;

    public SimpleListGraph(Collection<TVertex> items){
        super(items);
        edges = (List<TEdge>[]) new Object[verticesCount];
        for(int i =0; i<verticesCount; i++){
            edges[i] = new LinkedList<>();
        }
    }

    @Override
    public List<TEdge> getAllEdges() {
        LinkedList<TEdge> edgeList = new LinkedList<>();
        for(int from = 0; from<verticesCount; from++){
            for(TEdge e : edges[from]){
                if(e != null)
                    edgeList.add(e);
            }
        }
        return edgeList;
    }

    @Override
    public void addEdge(TEdge edge) {
        int from = verticesMap.get(edge.getFrom());
        List<TEdge> neighbours = edges[from];
        neighbours.remove(edge);
        neighbours.add(edge);
    }

    @Override
    public IGraph<TVertex, TEdge> emptyGraph() {
        return new SimpleListGraph<>(this.items);
    }

    @Override
    public List<TEdge> getEdgesFrom(IVertex vertex) {
        int v = verticesMap.get(vertex);
        return this.edges[v];
    }
}
