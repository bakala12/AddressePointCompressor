package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import compression.model.vrp.VrpProblem;

/**
 * An interface that defines a method for conversion phase.
 */
public interface IVrpProblemToJSpritConverter {
    /**
     * Perform conversion phase of the algorithm.
     * @param problem VRP problem.
     * @return Conversion phase result.
     */
    ConversionResult convertToJsprit(VrpProblem problem);
}
