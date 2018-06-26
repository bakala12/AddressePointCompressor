package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.util.Coordinate;
import compression.model.vrp.Client;
import compression.model.vrp.VrpProblem;
import compression.model.vrp.VrpProblemMetric;
import compression.services.compression.ICompressionService;
import compression.services.distance.IDistanceService;

public class MapMetricCompressionVrpProblemToJspritConverter extends ExplicitMetricCompressionVrpProblemToJSpritConverter {

    public MapMetricCompressionVrpProblemToJspritConverter(ICompressionService compressionService, IDistanceService distanceService) {
        super(compressionService, distanceService);
    }

    @Override
    public ConversionResult convertToJsprit(VrpProblem problem) {
        problem.setProblemMetric(VrpProblemMetric.Explicit);
        return super.convertToJsprit(problem);
    }

    @Override
    protected Location convertLocation(Client client) {
        Location.Builder b = Location.Builder.newInstance();
        b.setCoordinate(new Coordinate(client.getLocation().getLongitude(), client.getLocation().getLatitude()));
        b.setId(client.getId().toString());
        return b.build();
    }
}
