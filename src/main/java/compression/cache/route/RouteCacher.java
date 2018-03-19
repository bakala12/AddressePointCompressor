package compression.cache.route;

import compression.cache.store.ICacheStore;
import compression.model.vrp.Location;
import compression.model.graphhopper.Route;

public class RouteCacher implements IRouteCacher {

    private final ICacheStore<LocationsKey, Route> store;

    public RouteCacher(ICacheStore<LocationsKey, Route> store){
        this.store = store;
    }

    @Override
    public boolean hasRouteBetween(Location from, Location to) {
        return store.hasKey(new LocationsKey(from, to));
    }

    @Override
    public Route getRouteBetween(Location from, Location to) {
        return store.get(new LocationsKey(from, to));
    }

    @Override
    public void addRoute(Location from, Location to, Route route){
        store.add(new LocationsKey(from, to), route);
    }
}
