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
}
