package compression.services.distance;

import compression.model.vrp.Location;

/**
 * Implementation of IDistance service interface.
 */
public class DistanceService implements IDistanceService {

    /**
     * Gets the distance between two locations.
     * @param from First location.
     * @param to Second location.
     * @return Distance.
     */
    @Override
    public Double getEuclideanDistance(Location from, Location to) {
        Double latDiff = from.getLatitude() - to.getLatitude();
        Double longDiff = from.getLongitude() - to.getLongitude();
        return Math.sqrt(latDiff*latDiff + longDiff*longDiff);
    }
}
