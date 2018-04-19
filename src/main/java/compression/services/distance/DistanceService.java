package compression.services.distance;

//import compression.model.vrp.Distance;
//import compression.model.vrp.Location;

import compression.model.vrp.Location;

public class DistanceService implements IDistanceService {

    @Override
    public Double getEuclideanDistance(Location from, Location to) {
        Double latDiff = from.getLatitude() - to.getLatitude();
        Double longDiff = from.getLongitude() - to.getLongitude();
        return Math.sqrt(latDiff*latDiff + longDiff*longDiff);
    }

    /**public Distance getApproximateDistance(Location from, Location to){
        return new Distance(from, to, distance(from.getLatitude(), from.getLongitude(), to.getLatitude(), to.getLongitude(), "K"));
    }

    private static double distance(double lat1, double lon1, double lat2, double lon2, String unit) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        if (unit == "K") {
            dist = dist * 1.609344;
        } else if (unit == "N") {
            dist = dist * 0.8684;
        }
        return (dist);
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }**/
}
