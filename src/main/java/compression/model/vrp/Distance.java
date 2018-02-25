package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Distance {
    @Getter
    private Location from;
    @Getter
    private Location to;
    @Getter
    private double distance;
}
