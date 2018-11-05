package compression.model.vrp.helpers;

import compression.model.vrp.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Class that represents graph vertex with a given location and demand like VRP client vertex.
 */
@AllArgsConstructor
public class LocationVertex{
    @Getter
    private Long id;
    @Getter
    private Location location;
    @Getter
    private Double demand;

    /**
     * Converts this instance to a String.
     * @return String representation of this object.
     */
    @Override
    public String toString(){
        return "ID="+id+":"+location.toString();
    }
}
