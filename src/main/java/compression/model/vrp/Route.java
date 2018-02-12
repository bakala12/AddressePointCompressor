package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class Route {
    @Getter
    private Location from;
    @Getter
    private Location to;
    @Getter
    private List<Location> locations;
    @Getter
    private double distance;
    @Getter
    private double time;
}
