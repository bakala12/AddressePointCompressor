package compression.model.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class SimpleEdge {
    @Getter
    private int fromId;
    @Getter
    private int toId;
    @Getter
    private double weight;
}
