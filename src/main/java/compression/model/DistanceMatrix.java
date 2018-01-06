package compression.model;

import com.graphhopper.api.MatrixResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DistanceMatrix {
    private final MatrixResponse response;
    private final List<AddressPoint> addressPoints;

    public double getDistance(AddressPoint from, AddressPoint to){
        return response.getDistance(addressPoints.indexOf(from), addressPoints.indexOf(to));
    }
}
