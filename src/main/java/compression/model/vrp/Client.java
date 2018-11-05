package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents VRP problem client vertex.
 */
@AllArgsConstructor
public class Client {
    @Getter
    private Long id;
    @Getter
    private Double amount;
    @Getter
    private Double time;
    @Getter
    private Location location;

    /**
     * Converts this instance to a String.
     * @return String representation of this object.
     */
    @Override
    public String toString(){
        return "ID: "+id+" Amount: "+amount+" Time: "+ time + " Location: " + location;
    }
}
