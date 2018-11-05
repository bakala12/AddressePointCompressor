package compression.spanning;

import compression.model.graph.Edge;
import compression.model.structures.FibonacciHeap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Represents a model for super-vertex.
 */
@RequiredArgsConstructor
class SuperVertexKeeper {
    @Getter
    private final Integer v;
    @Getter @Setter
    private Edge in;
    @Getter @Setter
    private Double cons;
    @Getter @Setter
    private Integer prev;
    @Getter @Setter
    private Integer parent;
    @Getter @Setter
    private List<Integer> children;
    @Getter @Setter
    private FibonacciHeap<Edge> p;
}
