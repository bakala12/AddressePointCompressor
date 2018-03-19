package compression.cache.route;

import compression.model.vrp.Location;
import compression.model.graphhopper.Route;

public interface IRouteCacher {
    boolean hasRouteBetween(Location from, Location to);
    Route getRouteBetween(Location from, Location to);
    void addRoute(Location from, Location to, Route route);
}
