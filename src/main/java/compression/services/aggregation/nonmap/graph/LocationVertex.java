package compression.services.aggregation.nonmap.graph;

import compression.graph.IVertex;
import compression.model.vrp.Location;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class LocationVertex implements IVertex{
    @Getter
    private Location location;
}
