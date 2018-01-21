package compression.io;

import compression.model.vrp.VrpProblem;

public interface IProblemReader {
    VrpProblem readProblemInstance(String resourceName);
}
