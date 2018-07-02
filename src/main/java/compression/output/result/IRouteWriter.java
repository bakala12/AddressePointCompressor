package compression.output.result;

import compression.services.resolving.ResolvedSolution;

public interface IRouteWriter {
    void writeRoute(ResolvedSolution solution, String path);
}
