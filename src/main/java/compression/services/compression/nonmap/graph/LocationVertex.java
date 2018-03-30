package compression.services.compression.nonmap.graph;

import compression.graph.IVertex;
import compression.model.vrp.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class LocationVertex implements IVertex{
    @Getter
    private Location location;

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
