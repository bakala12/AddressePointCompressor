package compression.model.vrp;

import lombok.Getter;

public class SymmetricalDistanceMatrix {
    @Getter
    private final Integer dimensions;
    private final Double[][] distances;

    public SymmetricalDistanceMatrix(Integer dimensions){
        this.dimensions = dimensions;
        distances = new Double[dimensions][dimensions];
    }

    public Double getDistance(Long fromId, Long toId) {
        return distances[fromId.intValue()-1][toId.intValue()-1];
    }

    public void setDistance(Long fromId, Long toId, Double dist){
        distances[fromId.intValue()-1][toId.intValue()-1] = dist;
        distances[toId.intValue()-1][fromId.intValue()-1] = dist;
    }

    public void setDistance(Integer from, Integer to, Double dist){
        distances[from][to] = dist;
        distances[to][from] = dist;
    }
}
