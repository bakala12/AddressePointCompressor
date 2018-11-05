package compression.model.vrp;

/**
 * A distance matrix that not distinguish direction of edge (for non-directed graphs).
 */
public class SymmetricalDistanceMatrix extends DistanceMatrix{

    /**
     * Initializes a new instance of symmetrical distance matrix.
     * @param dimensions Number of vertices.
     */
    public SymmetricalDistanceMatrix(Integer dimensions) {
        super(dimensions);
    }

    /**
     * Sets the distance between given vertices (in both directions).
     * @param fromId Id of start vertex.
     * @param toId Id of end vertex.
     * @param dist Given distance.
     */
    @Override
    public void setDistance(Long fromId, Long toId, Double dist){
        distances[fromId.intValue()-1][toId.intValue()-1] = dist;
        distances[toId.intValue()-1][fromId.intValue()-1] = dist;
    }

    /**
     * Sets the distance between given vertices (in both directions).
     * @param from Index of start vertex.
     * @param to Index of end vertex.
     * @param dist Given distance.
     */
    @Override
    public void setDistance(Integer from, Integer to, Double dist){
        distances[from][to] = dist;
        distances[to][from] = dist;
    }
}
