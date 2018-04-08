package compression.services.jsprit;

import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.algorithm.state.StateManager;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.constraint.ConstraintManager;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.util.Solutions;
import com.graphhopper.jsprit.core.util.StopWatch;
import com.graphhopper.storage.Directory;
import compression.model.jsprit.VrpProblemSolution;
import compression.model.vrp.VrpProblem;
import compression.output.datalogger.CsvDataLogger;
import compression.output.datalogger.IDataLogger;
import compression.services.jsprit.conversion.EuclideanMetricVrpProblemToJSpritConverter;
import compression.services.jsprit.conversion.ExplicitMetricVrpProblemToJSpritConverter;
import compression.services.jsprit.conversion.IVrpProblemToJSpritConverter;
import compression.services.jsprit.conversion.ProblemConversionException;
import compression.services.jsprit.extensions.DataCollectorIterationEndListener;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

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

    private int maxNumberOfIterations = 2000;

    @Override
    public void setMaxNumberOfIterations(int maxNumberOfIterations){
        this.maxNumberOfIterations = maxNumberOfIterations;
    }

    @Override
    public VrpProblemSolution solve(VrpProblem problem){
        return solve(problem, null);
    }

    @Override
    public VrpProblemSolution solve(VrpProblem problem, String dataPath){
        IVrpProblemToJSpritConverter converter = JSpritConvertersFactory.getConverter(problem);
        VehicleRoutingProblem vrp = converter.convertToJsprit(problem);
        VehicleRoutingAlgorithm algorithm = Jsprit.createAlgorithm(vrp);
        algorithm.setMaxIterations(maxNumberOfIterations);
        if(dataPath != null){
            IDataLogger logger = new CsvDataLogger(dataPath);
            algorithm.addListener(new DataCollectorIterationEndListener(problem, logger));
        }
        Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();
        return new VrpProblemSolution(vrp, solutions);
    }

    @Override
    public VrpProblemSolution compressAndSolve(VrpProblem problem){
        return compressAndSolve(problem, null);
    }

    @Override
    public VrpProblemSolution compressAndSolve(VrpProblem problem, String dataPath) {
        //IVrpProblemToJSpritConverter converter = JSpritConvertersFactory.getConverter(problem);
        //VehicleRoutingProblem vrp = converter.compressAndConvertToJSprit(problem);
        //Jsprit.Builder algorithmBuilder = Jsprit.Builder.newInstance(vrp);
        //StateManager stateManager = new StateManager(vrp);
        //ConstraintManager constraintManager = new ConstraintManager(vrp, stateManager);
        //stateManager.addStateUpdater(new MyShipmentStateUpdater(stateManager));
        //algorithmBuilder.setStateAndConstraintManager(stateManager, constraintManager);
        //VehicleRoutingAlgorithm algorithm = algorithmBuilder.buildAlgorithm();
        //Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();
        //return new VrpProblemSolution(vrp, solutions);
        return null;
    }
}
