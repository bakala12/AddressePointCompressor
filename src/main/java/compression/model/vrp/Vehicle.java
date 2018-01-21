package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Vehicle {
    @Getter
    private String name;
    @Getter
    private double capacity;
    @Getter
    private Location startLocation;
    @Getter
    private Location endLocation;
}
