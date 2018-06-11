package compression.model.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class TreeBranch<TVertex> {
    @Getter
    private TVertex startVertex;
    @Getter
    private TVertex endVertex;
    @Getter
    private List<TVertex> vertices;
}
