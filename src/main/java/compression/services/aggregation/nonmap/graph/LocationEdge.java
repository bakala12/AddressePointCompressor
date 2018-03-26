package compression.services.aggregation.nonmap.graph;

import compression.graph.IEdge;
import compression.model.vrp.Location;
import lombok.Getter;

public class LocationEdge implements IEdge<LocationVertex>{
    @Getter
    private LocationVertex from;
    @Getter
    private LocationVertex to;

    public LocationEdge(Location from, Location to){
        this.from = new LocationVertex(from);
        this.to = new LocationVertex(to);
    }

    private static Double getDistance(Location from, Location to){
        Double latDiff = from.getLatitude()-to.getLatitude();
        Double longDiff = from.getLongitude() - to.getLongitude();
        return Math.sqrt(latDiff*latDiff+longDiff*longDiff);
    }

    @Override
    public Double getWeight() {
        return getDistance(from.getLocation(), to.getLocation());
    }
}
