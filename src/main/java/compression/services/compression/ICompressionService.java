package compression.services.compression;

import compression.model.vrp.CompressedProblem;
import compression.model.vrp.VrpProblem;

public interface ICompressionService {
    CompressedProblem compress(VrpProblem problem);
}
