package compression.model.jsprit;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Stores information about the solution.
 */
@AllArgsConstructor
public class SolutionInfo {
    @Getter
    private String benchmark;
    @Getter
    private Integer originalSize;
    @Getter
    private ProblemType problemType;
    @Getter
    private Double bestKnownSolution;
    @Getter
    private Double solutionValue;
    @Getter
    private Double solutionTime;
    @Getter
    private Double compressionTime;
    @Getter
    private Integer usedVehicles;
    @Getter
    private Integer compressedSize;

}
