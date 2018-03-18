package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Location {
    @Getter
    private double latitude;
    @Getter
    private double longitude;

    @Override
    public String toString(){
        return longitude+","+latitude;
    }

    @Override
    public boolean equals(Object obj){
        if(obj == null || !(obj instanceof Location))
            return false;
        Location l = (Location)obj;
        return latitude == l.latitude && longitude == l.longitude;
    }

    @Override
    public int hashCode(){
        int res = 17;
        res = 31*res+Double.valueOf(latitude).hashCode();
        res = 31*res+Double.valueOf(longitude).hashCode();
        return res;
    }
}
