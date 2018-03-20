package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class VrpProblem {
    @Getter
    private String problemName;
    @Getter
    private double bestKnownSolution;
    @Getter
    private VrpProblemMetric problemMetric;
    @Getter
    private List<Client> clients;
    @Getter
    private List<Vehicle> vehicles;
    @Getter
    private Depot depots;
    @Getter
    private DistanceMatrix distanceMatrix;
}
