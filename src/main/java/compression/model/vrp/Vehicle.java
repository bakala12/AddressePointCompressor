package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Vehicle {
    @Getter
    private long id;
    @Getter
    private double capacity;
    @Getter
    private Location startLocation;
    @Getter
    private Location endLocation;
}
