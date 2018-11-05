package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import compression.model.vrp.VrpProblem;

/**
 * An interface that defines a method for conversion VRP problem to JSprit problem.
 */
public interface IVrpProblemToJSpritConverter {
    /**
     * Converts VRP problem to JSprit problem.
     * @param problem VRP problem.
     * @return Conversion phase result.
     */
    ConversionResult convertToJsprit(VrpProblem problem);
}
