package compression.services.compression.nonmap.graph;

import compression.graph.IEdge;
import compression.model.vrp.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class LocationEdge implements IEdge<LocationVertex>{
    @Getter
    private LocationVertex from;
    @Getter
    private LocationVertex to;
    @Getter
    private Double weight;

    public LocationEdge(LocationVertex from, LocationVertex to){
        this(from, to, getDistance(from.getLocation(), to.getLocation()));
    }

    private static Double getDistance(Location from, Location to){
        Double latDiff = from.getLatitude()-to.getLatitude();
        Double longDiff = from.getLongitude() - to.getLongitude();
        return Math.sqrt(latDiff*latDiff+longDiff*longDiff);
    }

    @Override
    public String toString(){
        return from+"--"+weight+"-->"+to;
    }
}
