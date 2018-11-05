package compression.services.compression;

import compression.model.vrp.helpers.AggregatedService;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

/**
 * Defines a compression phase result.
 */
@AllArgsConstructor
public class CompressionResult {
    @Getter
    private List<AggregatedService> aggregatedServices;
    @Getter
    private Double time;
}
