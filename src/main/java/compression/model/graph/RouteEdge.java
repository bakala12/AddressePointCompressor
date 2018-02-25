package compression.model.graph;

import compression.model.vrp.Route;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class RouteEdge {
    @Getter
    private int fromLocationId;
    @Getter
    private int toLocationId;
    @Getter
    private Route route;
}
