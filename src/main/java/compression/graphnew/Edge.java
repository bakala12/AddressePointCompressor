package compression.graphnew;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
public class Edge {
    private Object source;
    private Object target;
    @Getter
    private Double weight;

    @SuppressWarnings("unchecked")
    public <T> T getSource() {
        return (T) source;
    }

    @SuppressWarnings("unchecked")
    public <T> T getTarget() {
        return (T) target;
    }
}
