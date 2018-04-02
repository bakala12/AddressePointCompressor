package compression.services.jsprit;

import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import compression.model.jsprit.VrpProblemSolution;
import compression.model.vrp.VrpProblem;
import compression.services.jsprit.conversion.EuclideanMetricVrpProblemToJSpritConverter;
import compression.services.jsprit.conversion.ExplicitMetricVrpProblemToJSpritConverter;
import compression.services.jsprit.conversion.IVrpProblemToJSpritConverter;
import compression.services.jsprit.conversion.ProblemConversionException;
import java.util.Collection;

public class JSpritService implements IJSpritService {

    private static class JSpritConvertersFactory{
        static IVrpProblemToJSpritConverter getConverter(VrpProblem problem) {
            switch (problem.getProblemMetric()){
                case Euclidean:
                    return new EuclideanMetricVrpProblemToJSpritConverter();
                case Explicit:
                    return new ExplicitMetricVrpProblemToJSpritConverter();
                case GraphHopper:
                    throw new RuntimeException();
                default:
                    throw new ProblemConversionException("Unsupported or unknown problem metrics");
            }
        }
    }

    private VrpProblemSolution solveJSpritProblem(VehicleRoutingProblem jSpritProblem){
        VehicleRoutingAlgorithm algorithm = Jsprit.createAlgorithm(jSpritProblem);
        Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();
        return new VrpProblemSolution(jSpritProblem, solutions);
    }

    @Override
    public VrpProblemSolution solve(VrpProblem problem){
        IVrpProblemToJSpritConverter converter = JSpritConvertersFactory.getConverter(problem);
        VehicleRoutingProblem vrp = converter.convertToJsprit(problem);
        return solveJSpritProblem(vrp);
    }

    @Override
    public VrpProblemSolution compressAndSolve(VrpProblem problem){
        IVrpProblemToJSpritConverter converter = JSpritConvertersFactory.getConverter(problem);
        VehicleRoutingProblem vrp = converter.compressAndConvertToJSprit(problem);
        return solveJSpritProblem(vrp);
    }
}
