package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.util.Coordinate;
import compression.model.vrp.Client;
import compression.model.vrp.VrpProblem;
import compression.model.vrp.VrpProblemMetric;
import compression.services.compression.ICompressionService;
import compression.services.distance.IDistanceService;

/**
 * Converts map VRP problem to JSprit problem using compression algorithm.
 */
public class MapMetricCompressionVrpProblemToJspritConverter extends ExplicitMetricCompressionVrpProblemToJSpritConverter {

    /**
     * Initializes a new instance of MapMetricCompressionVrpProblemToJspritConverter.
     * @param compressionService Compression service.
     * @param distanceService Distance service.
     */
    public MapMetricCompressionVrpProblemToJspritConverter(ICompressionService compressionService, IDistanceService distanceService) {
        super(compressionService, distanceService);
    }

    /**
     * Converts map VRP problem to JSprit problem. This uses compression and convert compressed problem to be solved by JSprit.
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
