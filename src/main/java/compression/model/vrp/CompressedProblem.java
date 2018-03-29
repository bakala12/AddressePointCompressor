package compression.model.vrp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class CompressedProblem {
    @Getter
    private VrpProblem originalProblem;
    @Getter
    private VrpProblem compressedProblem;
}
