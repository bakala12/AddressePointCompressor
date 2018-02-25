package compression.cache.route;

import compression.model.vrp.Location;
import compression.model.vrp.Route;

public interface IRouteCacher {
    boolean hasRouteBetween(Location from, Location to);
    Route getRouteBetween(Location from, Location to);
}
