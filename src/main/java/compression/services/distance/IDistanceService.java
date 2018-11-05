package compression.services.distance;

import compression.model.vrp.Location;

/**
 * Defines method for getting Euclidean distance between two locations on the map.
 */
public interface IDistanceService {
    /**
     * Gets the distance between two locations.
     * @param from First location.
     * @param to Second location.
     * @return Distance.
     */
    Double getEuclideanDistance(Location from, Location to);
}
