package compression.services.distance;

//import compression.model.vrp.Distance;
//import compression.model.vrp.Location;

import compression.model.vrp.Location;

public interface IDistanceService {
    //Distance getApproximateDistance(Location from, Location to);
    Double getEuclideanDistance(Location from, Location to);
}
