package compression.services.compression;

import compression.model.vrp.VrpProblem;
import compression.model.vrp.helpers.AggregatedService;

import java.util.List;

public interface ICompressionService {
    List<AggregatedService> getAggregatedClients(VrpProblem problem);
}
