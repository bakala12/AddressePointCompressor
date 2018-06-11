package compression.services.compression;

import compression.model.vrp.VrpProblem;

import java.util.List;

public interface ICompressionService {
    //todo make use of that
    List<Object> getAggregatedClients(VrpProblem problem);
}
