package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import compression.model.vrp.helpers.AggregatedService;
import compression.services.compression.CompressionResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@AllArgsConstructor
public class ConversionResult {
    @Getter
    private VehicleRoutingProblem convertedProblem;
    @Getter
    private CompressionResult compressionResult;
    @Getter
    private Map<Long, AggregatedService> compressionMap;
}
