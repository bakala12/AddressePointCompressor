package compression.services.resolving;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class VrpSolutionRoute {
    @Getter
    private String vehicleId;
    @Getter
    private List<VrpSolutionRouteNode> nodes;
}
