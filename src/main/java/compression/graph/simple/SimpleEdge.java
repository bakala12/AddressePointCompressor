package compression.graph.simple;

import compression.graph.IEdge;
import compression.graph.IVertex;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleEdge<TVertex extends IVertex> implements IEdge<TVertex> {
    @Getter
    private final TVertex from;
    @Getter
    private final TVertex to;
    @Getter
    private final Double weight;
}
