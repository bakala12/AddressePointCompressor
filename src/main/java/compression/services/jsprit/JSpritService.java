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
import compression.services.compression.nonmap.NonMapCompressionService;
import compression.services.distance.IDistanceService;
import compression.services.jsprit.conversion.*;
import compression.services.jsprit.extensions.DataCollectorIterationEndListener;
import lombok.RequiredArgsConstructor;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

@RequiredArgsConstructor
public class JSpritService implements IJSpritService {

    private class JSpritConvertersFactory{
        public IVrpProblemToJSpritConverter getConverter(VrpProblem problem) {
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

        public IVrpProblemToJSpritConverter getCompressedConverter(VrpProblem problem){
            switch (problem.getProblemMetric()){
                case Euclidean:
                    return new EuclideanMetricCompressionVrpToJSpritConverter(nonMapCompressionService, distanceService);
                case Explicit:
                    return new ExplicitMetricCompressionVrpProblemToJSpritConverter(nonMapCompressionService);
                case GraphHopper:
                    throw new RuntimeException();
                default:
                    throw new ProblemConversionException("Unsupported or unknown problem metrics");
            }
        }
    }

    private final NonMapCompressionService nonMapCompressionService;
    private final IDistanceService distanceService;
    private final JSpritConvertersFactory factory = new JSpritConvertersFactory();

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
        IVrpProblemToJSpritConverter converter = factory.getConverter(problem);
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
        IVrpProblemToJSpritConverter converter = factory.getCompressedConverter(problem);
        VehicleRoutingProblem vrp = converter.convertToJsprit(problem);
        Jsprit.Builder algorithmBuilder = Jsprit.Builder.newInstance(vrp);
        VehicleRoutingAlgorithm algorithm = algorithmBuilder.buildAlgorithm();
        algorithm.setMaxIterations(maxNumberOfIterations);
        if(dataPath != null){
            IDataLogger logger = new CsvDataLogger(dataPath);
            algorithm.addListener(new DataCollectorIterationEndListener(problem, logger));
        }
        Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();
        return new VrpProblemSolution(vrp, solutions);
    }
}
