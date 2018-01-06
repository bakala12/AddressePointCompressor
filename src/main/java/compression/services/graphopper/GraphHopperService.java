package compression.services.graphopper;

import com.graphhopper.api.GHMRequest;
import com.graphhopper.api.GraphHopperMatrixWeb;
import com.graphhopper.api.MatrixResponse;
import com.graphhopper.util.shapes.GHPoint;
import compression.model.AddressPoint;
import compression.model.DistanceMatrix;

import java.util.LinkedList;
import java.util.List;

public class GraphHopperService implements IGraphHopperService {

    public DistanceMatrix getDistanceMatrix(List<AddressPoint> addressPoints){
        GraphHopperMatrixWeb matrixClient = GraphHopperMatrixClientProvider.getClient();

        GHMRequest ghmRequest = new GHMRequest();
        ghmRequest.addOutArray("distances");
        ghmRequest.addOutArray("times");
        ghmRequest.addOutArray("weights");
        ghmRequest.setVehicle("car");

        List<GHPoint> allPoints = new LinkedList<>();
        for (AddressPoint addr : addressPoints) {
            allPoints.add(new GHPoint(addr.getLatitude(), addr.getLongitude()));
        }
        ghmRequest.addAllPoints(allPoints);

        MatrixResponse response = matrixClient.route(ghmRequest);
        return new DistanceMatrix(response, addressPoints);
    }
}
