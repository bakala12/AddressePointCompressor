package compression.graph.mst.helpers;

import compression.graph.IEdge;
import compression.graph.IVertex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class EdgeKeeper<TVertex extends IVertex, TEdge extends IEdge<TVertex>> {
    @Getter
    private VertexKeeper<TVertex> from;
    @Getter
    private VertexKeeper<TVertex> to;
    @Getter @Setter
    private Double weight;
    @Getter
    private TEdge edge;

    @Override
    public boolean equals(Object other)
    {
        try {
            EdgeKeeper<TVertex, TEdge> oe = (EdgeKeeper<TVertex, TEdge>)other;
            if(oe == null) return false;
            return from.equals(oe.from) && to.equals(oe.to);
        } catch (ClassCastException ex){
            return false;
        }
    }

    @Override
    public int hashCode(){
        int result = 17;
        result = 31 * result + from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + weight.hashCode();
        return result;
    }

    @Override
    public String toString(){
        return from.toString()+"--->"+to.toString();
    }
}
