package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import compression.services.compression.CompressionResult;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ConversionResult {
    @Getter
    private VehicleRoutingProblem convertedProblem;
    @Getter
    private CompressionResult compressionResult;
}
