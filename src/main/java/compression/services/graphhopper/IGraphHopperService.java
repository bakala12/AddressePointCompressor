package compression.services.graphhopper;

import compression.model.vrp.Location;
import compression.model.vrp.Route;
import compression.model.vrp.SimpleRoute;

public interface IGraphHopperService {
    Route getRoute(Location from, Location to);
    SimpleRoute getSimpleRouteInformation(Location from, Location to);
}
