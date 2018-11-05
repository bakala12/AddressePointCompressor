package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a location on the map.
 */
@AllArgsConstructor
public class Location {
    @Getter
    private Double latitude;
    @Getter
    private Double longitude;

    /**
     * Converts this instance to a String.
     * @return String representation of this object.
     */
    @Override
    public String toString(){
        return "("+latitude+","+longitude+")";
    }
}
