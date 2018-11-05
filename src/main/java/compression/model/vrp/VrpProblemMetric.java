package compression.model.vrp;

/**
 * Represents a metric used by VRP problem instance.
 */
public enum VrpProblemMetric {
    /**
     * Metrics is unknown.
     */
    Unknown,
    /**
     * Euclidean metrics.
     */
    Euclidean,
    /**
     * Explicit distance matrix should be given.
     */
    Explicit,
    /**
     * This is the same as explicit, but distinguishes map problem instance.
     */
    Map
}
