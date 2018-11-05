package compression.model.vrp;

import lombok.Getter;

/**
 * Represents a matrix with distances between all vertices.
 */
public class DistanceMatrix {
    @Getter
    private final Integer dimensions;
    /**
     * Distance internal array.
     */
    protected final Double[][] distances;

    /**
     * Initializes a new instance of empty distance matrix.
     * @param dimensions Number of vertices.
     */
    public DistanceMatrix(Integer dimensions){
        this.dimensions = dimensions;
        distances = new Double[dimensions][dimensions];
    }

    /**
     * Gets the distance between two vertices.
     * @param fromId Id of start vertex.
     * @param toId Id of end vertex.
     * @return A weight of edge connecting two given vertices.
     */
    public Double getDistance(Long fromId, Long toId) {
        return distances[fromId.intValue()-1][toId.intValue()-1];
    }

    /**
     * Sets the distance between given vertices.
     * @param fromId Id of start vertex.
     * @param toId Id of end vertex.
     * @param dist Given distance.
     */
    public void setDistance(Long fromId, Long toId, Double dist){
        distances[fromId.intValue()-1][toId.intValue()-1] = dist;
    }

    /**
     * Sets the distance between given vertices.
     * @param from Index of start vertex.
     * @param to Index of end vertex.
     * @param dist Given distance.
     */
    public void setDistance(Integer from, Integer to, Double dist){
        distances[from][to] = dist;
    }
}
