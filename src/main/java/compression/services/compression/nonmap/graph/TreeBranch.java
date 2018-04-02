package compression.services.compression.nonmap.graph;

import compression.graph.IVertex;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class TreeBranch<TVertex extends IVertex> {
    @Getter
    private TVertex startVertex;
    @Getter
    private TVertex endVertex;
    @Getter
    private List<TVertex> vertices;

    public TVertex firstVertex(){
        return vertices.get(1);
    }
}
