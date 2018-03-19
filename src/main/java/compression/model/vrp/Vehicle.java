package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Vehicle {
    @Getter
    private Long id;
    @Getter
    private Double capacity;
    @Getter
    private Long startDepotId;
    @Getter
    private Long endDepotId;
}
