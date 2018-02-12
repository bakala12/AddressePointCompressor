package compression.services.graphhopper;

import compression.model.vrp.Location;

public interface IGraphHopperService {
    void getRoute(Location from, Location to);
}
