package compression.model.graphhopper;

import compression.model.vrp.Instruction;
import compression.model.vrp.Location;
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
    private double distance;
    @Getter
    private double time;
    @Getter
    private int hops;
    @Getter
    private List<Instruction> instructions;
}
