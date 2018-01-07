package compression.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class VRPProblem {
    @Getter
    private final List<AddressPoint> addressPoints;
    @Getter
    private final DistanceMatrix distanceMatrix;
}
