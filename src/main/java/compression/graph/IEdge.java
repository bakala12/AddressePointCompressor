package compression.graph;

public interface IEdge<TVertex extends IVertex> {
    TVertex getFrom();
    TVertex getTo();
    Double getWeight();
}
