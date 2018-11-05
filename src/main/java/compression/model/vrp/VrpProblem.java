package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * A model class for VRP problem instance.
 */
@AllArgsConstructor
public class VrpProblem {
    @Getter
    private String problemName;
    @Getter
    private double bestKnownSolution;
    @Getter
    private int dimensions;
    @Getter @Setter
    private VrpProblemMetric problemMetric;
    @Getter
    private List<Client> clients;
    @Getter
    private List<Vehicle> vehicles;
    @Getter
    private Depot depot;
    @Getter @Setter
    private DistanceMatrix distanceMatrix;
}
