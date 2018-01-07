package compression.services.jsprit;

import com.graphhopper.jsprit.core.problem.cost.VehicleRoutingTransportCosts;
import com.graphhopper.jsprit.core.util.VehicleRoutingTransportCostsMatrix;
import compression.model.AddressPoint;
import compression.model.VRPProblem;

public class JSpritProblemBuilder {

    public static VehicleRoutingTransportCosts getDistancesMatrix(VRPProblem problem){
        VehicleRoutingTransportCostsMatrix.Builder builder = VehicleRoutingTransportCostsMatrix.Builder.newInstance(false);
        for (AddressPoint from : problem.getAddressPoints()) {
            for(AddressPoint to : problem.getAddressPoints()){
                builder.addTransportDistance(from.getId(), to.getId(), problem.getDistanceMatrix().getDistance(from, to));
            }
        }
        return builder.build();
    }
}
