package compression.model.jsprit;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Represents a single test run for benchmark. It contains parameters and some results.
 */
@AllArgsConstructor
public class RunResult{
    @Getter
    private String benchmark;
    @Getter
    private Double bestKnownSolution;
    @Getter
    private Integer originalSize;
    @Getter
    private Integer compressedSize;
    @Getter
    private Integer usedVehicles;
    @Getter
    private ProblemType problemType;
    @Getter
    private Double solutionTime;
    @Getter
    private Double compressionTime;
    @Getter
    private DecompressionMethod decompressionMethod;
    @Getter
    private Double simpleDecompressionSolutionValue;
    @Getter
    private Double greedyDecompressionSolutionValue;
}
