package compression.services.resolving;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Represents a VRP solution route.
 */
@AllArgsConstructor
public class VrpSolutionRoute {
    @Getter
    private String vehicleId;
    @Getter
    private List<VrpSolutionRouteNode> nodes;
}
