package compression.output.result;

import compression.services.resolving.ResolvedSolution;

/**
 * Defines way to write solution routes to a file.
 */
public interface IRouteWriter {
    /**
     * Writes solution routes to a file.
     * @param solution Solution routes.
     * @param path File path.
     */
    void writeRoute(ResolvedSolution solution, String path);
}
