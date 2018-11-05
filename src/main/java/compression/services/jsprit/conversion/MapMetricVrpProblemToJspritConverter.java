package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.util.Coordinate;
import compression.model.vrp.Client;
import compression.model.vrp.VrpProblem;
import compression.model.vrp.VrpProblemMetric;
import compression.services.distance.IDistanceService;

/**
 * Converts full map problem to JSprit problem - no compression algorithm used.
 */
public class MapMetricVrpProblemToJspritConverter extends ExplicitMetricVrpProblemToJSpritConverter {

    /**
     * Initializes a new instance of MapMetricVrpProblemToJspritConverter.
     * @param distanceService Distance service.
     */
    public MapMetricVrpProblemToJspritConverter(IDistanceService distanceService) {
        super(distanceService);
    }

    /**
     * Converts map VRP problem to JSprit problem. This converts full problem - no compression used.
     * @param problem VRP problem.
     * @return Conversion phase result.
     */
    @Override
    public ConversionResult convertToJsprit(VrpProblem problem) {
        problem.setProblemMetric(VrpProblemMetric.Explicit);
        return super.convertToJsprit(problem);
    }
    /**
     * Converts location to JSprit location.
     * @param client Client location
     * @return JSprit location.
     */
    @Override
    protected Location convertLocation(Client client) {
        Location.Builder b = Location.Builder.newInstance();
        b.setCoordinate(new Coordinate(client.getLocation().getLongitude(), client.getLocation().getLatitude()));
        b.setId(client.getId().toString());
        return b.build();
    }
}
