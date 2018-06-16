package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import compression.model.vrp.VrpProblem;

public interface IVrpProblemToJSpritConverter {
    ConversionResult convertToJsprit(VrpProblem problem);
}
