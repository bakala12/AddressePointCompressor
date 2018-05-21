package compression.services.distance;

import compression.model.vrp.Location;

public class DistanceService implements IDistanceService {

    @Override
    public Double getEuclideanDistance(Location from, Location to) {
        Double latDiff = from.getLatitude() - to.getLatitude();
        Double longDiff = from.getLongitude() - to.getLongitude();
        return Math.sqrt(latDiff*latDiff + longDiff*longDiff);
    }
}
