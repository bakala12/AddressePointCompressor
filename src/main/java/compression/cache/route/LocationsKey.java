package compression.cache.route;

import compression.model.vrp.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class LocationsKey {
    @Getter
    private double fromLatitude;
    @Getter
    private double fromLongitude;
    @Getter
    private double toLatitude;
    @Getter
    private double toLongitude;

    public LocationsKey(Location from, Location to){
        this(from.getLatitude(), from.getLongitude(), to.getLatitude(), to.getLongitude());
    }

    @Override
    public boolean equals(Object obj){
        if(obj == this)
            return true;
        if(obj == null || !(obj instanceof LocationsKey))
            return false;
        LocationsKey o = (LocationsKey)obj;

        return fromLatitude == o.fromLatitude &&
                fromLongitude == o.fromLongitude &&
                toLatitude == o.toLatitude &&
                toLongitude == o.toLongitude;
    }

    @Override
    public int hashCode(){
        int res = 17;
        res = 31*res + Double.valueOf(fromLatitude).hashCode();
        res = 31*res + Double.valueOf(fromLongitude).hashCode();
        res = 31*res + Double.valueOf(toLatitude).hashCode();
        res = 31*res + Double.valueOf(toLongitude).hashCode();
        return res;
    }
}
