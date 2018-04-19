package compression.model.vrp;

import lombok.Getter;

public class DistanceMatrix {
    @Getter
    private final Integer dimensions;
    protected final Double[][] distances;

    public DistanceMatrix(Integer dimensions){
        this.dimensions = dimensions;
        distances = new Double[dimensions][dimensions];
    }

    public Double getDistance(Long fromId, Long toId) {
        return distances[fromId.intValue()-1][toId.intValue()-1];
    }

    public void setDistance(Long fromId, Long toId, Double dist){
        distances[fromId.intValue()-1][toId.intValue()-1] = dist;
    }

    public void setDistance(Integer from, Integer to, Double dist){
        distances[from][to] = dist;
    }
}
