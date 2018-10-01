package compression.model.graph;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * A class that represents graph edge.
 */
@AllArgsConstructor
public class Edge {
    private Object source;
    private Object target;
    @Getter
    private Double weight;

    /**
     * Gets the source vertex of the edge.
     * @param <T> Vertex type.
     * @return Source vertex.
     */
    @SuppressWarnings("unchecked")
    public <T> T getSource() {
        return (T) source;
    }

    /**
     * Gets the target vertex of the edge.
     * @param <T> Vertex type.
     * @return Target vertex.
     */
    @SuppressWarnings("unchecked")
    public <T> T getTarget() {
        return (T) target;
    }

    /**
     * Converts this object to String
     * @return String representation of an object.
     */
    @Override
    public String toString(){
        return source.toString()+"-("+weight.toString()+")->"+target.toString();
    }
}
