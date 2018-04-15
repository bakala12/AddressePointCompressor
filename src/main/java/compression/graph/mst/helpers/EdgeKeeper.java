package compression.graph.mst.helpers;

import compression.graph.IEdge;
import compression.graph.IVertex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class EdgeKeeper<TVertex extends IVertex, TEdge extends IEdge<TVertex>> implements IEdge<VertexKeeper<TVertex>> {
    @Getter
    private VertexKeeper<TVertex> from;
    @Getter
    private VertexKeeper<TVertex> to;
    @Getter @Setter
    private Double weight;
    @Getter
    private TEdge edge;
}
