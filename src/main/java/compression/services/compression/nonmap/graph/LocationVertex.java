package compression.services.compression.nonmap.graph;

import compression.graph.IVertex;
import compression.model.vrp.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
public class LocationVertex implements IVertex{
    @Getter
    private final Location location;
    @Getter @Setter
    private Double demand;
    @Getter @Setter
    private Long id;

    @Override
    public boolean equals(Object obj){
        if(obj == null || !(obj instanceof LocationVertex)){
            return false;
        }
        LocationVertex l = (LocationVertex)obj;
        return location.equals(l.getLocation());
    }

    @Override
    public int hashCode(){
        return location.hashCode();
    }

    @Override
    public String toString(){
        return "LocationVertex"+location.toString();
    }
}
