package compression.model.vrp.helpers;

import compression.model.vrp.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class LocationVertex{
    @Getter
    private Long id;
    @Getter
    private Location location;
    @Getter
    private Double demand;

    @Override
    public String toString(){
        return "ID="+id+":"+location.toString();
    }
}
