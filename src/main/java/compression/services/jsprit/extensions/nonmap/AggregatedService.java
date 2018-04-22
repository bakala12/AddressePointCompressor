package compression.services.jsprit.extensions.nonmap;

import compression.services.compression.nonmap.graph.LocationVertex;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
public class AggregatedService {
    private List<LocationVertex> vertices;
    @Getter
    private LocationVertex inputVertex;
    @Getter
    private LocationVertex outputVertex;
    @Getter
    private Double internalCost;
    @Getter
    private Long id;
}
