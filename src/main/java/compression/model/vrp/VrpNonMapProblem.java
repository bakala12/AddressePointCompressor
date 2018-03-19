package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class VrpNonMapProblem {
    @Getter
    private String name;
    @Getter
    private double bestKnownSolution;
}
