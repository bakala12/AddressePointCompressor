package compression.model.jsprit;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collection;

@AllArgsConstructor
public class VrpSolution {
    @Getter
    private Double cost;
    @Getter
    private Collection<SolutionRoute> solutionRoutes;
}
