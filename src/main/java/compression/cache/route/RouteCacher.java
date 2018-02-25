package compression.cache.route;

import compression.cache.store.ICacheStore;
import compression.model.vrp.Location;
import compression.model.vrp.Route;

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
}
