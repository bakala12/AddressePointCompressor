package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Location {
    @Getter
    private Double latitude;
    @Getter
    private Double longitude;

    @Override
    public String toString(){
        return "("+latitude+","+longitude+")";
    }
}
