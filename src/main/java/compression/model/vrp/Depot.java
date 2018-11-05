package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a depot for VRP problem vehicles.
 */
@AllArgsConstructor
public class Depot {
    @Getter
    private Long id;
    @Getter
    private Location location;

    /**
     * Converts this instance to a String.
     * @return String representation of this object.
     */
    @Override
    public String toString(){
        return "ID: "+id+" LOCATION: "+location;
    }
}
