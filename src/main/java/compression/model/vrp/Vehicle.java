package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents information about the vehicle.
 */
@AllArgsConstructor
public class Vehicle {
    @Getter
    private Long id;
    @Getter
    private Integer capacity;

    /**
     * Converts this instance to a String.
     * @return String representation of this object.
     */
    @Override
    public String toString(){
        return "ID: "+id+" CAPACITY: "+capacity;
    }
}
