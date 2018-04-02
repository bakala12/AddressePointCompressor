package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import compression.model.vrp.VrpProblem;

public interface IVrpProblemToJSpritConverter {
    VehicleRoutingProblem convertToJsprit(VrpProblem problem);
    VehicleRoutingProblem compressAndConvertToJSprit(VrpProblem problem);
}
