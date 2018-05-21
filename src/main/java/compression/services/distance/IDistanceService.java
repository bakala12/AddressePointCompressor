package compression.services.distance;

import compression.model.vrp.Location;

public interface IDistanceService {
    Double getEuclideanDistance(Location from, Location to);
}
