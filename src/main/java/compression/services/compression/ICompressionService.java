package compression.services.compression;

import compression.model.vrp.VrpProblem;
import compression.model.vrp.helpers.AggregatedService;

import java.util.List;

/**
 * Defines a method to perform conversion and compression phase of the algorithm.
 */
public interface ICompressionService {
    /**
     * Compresses the VRP problem.
     * @param problem Original VRP problem.
     * @return Compression phase result.
     */
    CompressionResult getAggregatedClients(VrpProblem problem);
}
