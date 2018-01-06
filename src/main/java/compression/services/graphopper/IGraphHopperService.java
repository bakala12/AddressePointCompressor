package compression.services.graphopper;

import compression.model.AddressPoint;
import compression.model.DistanceMatrix;

import java.util.List;

public interface IGraphHopperService {
    DistanceMatrix getDistanceMatrix(List<AddressPoint> addressPoints);
}
