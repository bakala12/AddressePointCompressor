package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Depot {
    @Getter
    private Long id;
    @Getter
    private Location location;

    @Override
    public String toString(){
        return "ID: "+id+" LOCATION: "+location;
    }
}
