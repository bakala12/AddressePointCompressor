package compression.model.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Represents a branch of minimal directed spanning tree. It is a path from one vertex with degree greater than one to
 * another such vertex.
 * @param <TVertex> Vertex type.
 */
@AllArgsConstructor
public class TreeBranch<TVertex> {
    @Getter
    private TVertex startVertex;
    @Getter
    private TVertex endVertex;
    @Getter
    private List<TVertex> vertices;
}
