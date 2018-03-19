package compression.model.graphhopper;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Instruction {
    @Getter
    private double distance;
    @Getter
    private Long sign;
    @Getter
    private Long[] interval;
    @Getter
    private double time;
}
