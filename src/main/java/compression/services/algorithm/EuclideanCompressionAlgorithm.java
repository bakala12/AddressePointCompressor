package compression.services.algorithm;

import compression.model.vrp.Location;

public class EuclideanCompressionAlgorithm extends BaseCompressionAlgorithm{

    @Override
    protected double getDistanceBetween(Location from, Location to) {
        return Math.sqrt((from.getLatitude()-to.getLatitude())*(from.getLatitude()-to.getLatitude())+(from.getLongitude()-to.getLongitude())*(from.getLongitude()-to.getLongitude()));
    }
}
