package compression.model.vrp;

public class SymmetricalDistanceMatrix extends DistanceMatrix{

    public SymmetricalDistanceMatrix(Integer dimensions) {
        super(dimensions);
    }

    @Override
    public void setDistance(Long fromId, Long toId, Double dist){
        distances[fromId.intValue()-1][toId.intValue()-1] = dist;
        distances[toId.intValue()-1][fromId.intValue()-1] = dist;
    }

    @Override
    public void setDistance(Integer from, Integer to, Double dist){
        distances[from][to] = dist;
        distances[to][from] = dist;
    }
}
