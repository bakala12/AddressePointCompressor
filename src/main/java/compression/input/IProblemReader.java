package compression.input;

/**
 * An interface that is responsible for reading VRP problem from a file.
 * @param <TVrpProblem>
 */
public interface IProblemReader<TVrpProblem> {
    /**
     * Reads VRP problem from resource file.
     * @param resourceName Name of resource.
     * @return VRP problem.
     */
    TVrpProblem readProblemInstanceFromResources(String resourceName);

    /**
     * Reads VRP problem instance from a file.
     * @param path File path.
     * @return VRP problem.
     */
    TVrpProblem readProblemInstanceFromFile(String path);
}
