package compression.services.jsprit;

import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.Solutions;
import com.graphhopper.jsprit.core.util.StopWatch;
import compression.model.jsprit.DecompressionMethod;
import compression.model.jsprit.ProblemType;
import compression.model.jsprit.SolutionInfo;
import compression.model.jsprit.VrpProblemSolution;
import compression.model.vrp.VrpProblem;
import compression.output.datalogger.CsvDataLogger;
import compression.output.datalogger.IDataLogger;
import compression.output.result.IRouteWriter;
import compression.output.result.RouteWriter;
import compression.services.compression.ICompressionService;
import compression.services.distance.IDistanceService;
import compression.services.jsprit.conversion.*;
import compression.services.jsprit.extensions.DataCollectorIterationEndListener;
import compression.services.resolving.*;
import lombok.RequiredArgsConstructor;
import com.graphhopper.jsprit.io.problem.VrpXMLWriter;

import java.util.*;

@RequiredArgsConstructor
public class JSpritService implements IJSpritService {

    private class JSpritConvertersFactory{
        public IVrpProblemToJSpritConverter getConverter(VrpProblem problem) {
            switch (problem.getProblemMetric()){
                case Euclidean:
                    return new EuclideanMetricVrpProblemToJSpritConverter(distanceService);
                case Explicit:
                    return new ExplicitMetricVrpProblemToJSpritConverter(distanceService);
                case Map:
                    return new MapMetricVrpProblemToJspritConverter(distanceService);
                default:
                    throw new ProblemConversionException("Unsupported or unknown problem metrics");
            }
        }

        public IVrpProblemToJSpritConverter getCompressedConverter(VrpProblem problem){
            switch (problem.getProblemMetric()){
                case Euclidean:
                    return new EuclideanMetricCompressionVrpToJSpritConverter(compressionService, distanceService);
                case Explicit:
                    return new ExplicitMetricCompressionVrpProblemToJSpritConverter(compressionService, distanceService);
                case Map:
                    return new MapMetricCompressionVrpProblemToJspritConverter(compressionService, distanceService);
                default:
                    throw new ProblemConversionException("Unsupported or unknown problem metrics");
            }
        }
    }
    private final class SolutionRouteResolverFactory{
        private final ISolutionRouteResolver fullSolutionRouteResolver = new FullSolutionRouteResolver();
        private final ISolutionRouteResolver compressedSolutionRouteResolver = new SimpleCompressedSolutionRouteResolver();
        private final ISolutionRouteResolver greedySolutionRouteResolver = new GreedyCompressionSolutionRouteResolver();

        public ISolutionRouteResolver get(boolean useCompression, DecompressionMethod decompressionMethod){
            return useCompression ? (decompressionMethod == DecompressionMethod.SIMPLE ? compressedSolutionRouteResolver : greedySolutionRouteResolver) : fullSolutionRouteResolver;
        }
    }

    private final ICompressionService compressionService;
    private final IDistanceService distanceService;
    private final JSpritConvertersFactory factory = new JSpritConvertersFactory();
    private final IRouteWriter solutionRouteWriter = new RouteWriter();
    private final SolutionRouteResolverFactory solutionRoureResolverFactory = new SolutionRouteResolverFactory();
    private int maxNumberOfIterations = 2000;
    private Long seed = null;

    @Override
    public void setMaxNumberOfIterations(int maxNumberOfIterations){
        this.maxNumberOfIterations = maxNumberOfIterations;
    }

    @Override
    public void setRandomSeed(Long seed){
        this.seed = seed;
    }

    @Override
    public VrpProblemSolution solve(VrpProblem problem){
        return solve(problem, null);
    }

    @Override
    public VrpProblemSolution solve(VrpProblem problem, String dataPath){
        return solve(problem, dataPath, null);
    }

    @Override
    public VrpProblemSolution solve(VrpProblem problem, String dataPath, String solutionRoutePath){
        IVrpProblemToJSpritConverter converter = factory.getConverter(problem);
        VehicleRoutingProblem vrp = converter.convertToJsprit(problem).getConvertedProblem();
        Jsprit.Builder builder = Jsprit.Builder.newInstance(vrp);
        if(seed != null)
            builder.setRandom(new Random(seed));
        VehicleRoutingAlgorithm algorithm = builder.buildAlgorithm();
        algorithm.setMaxIterations(maxNumberOfIterations);
        if(dataPath != null){
            IDataLogger logger = new CsvDataLogger(dataPath);
            algorithm.addListener(new DataCollectorIterationEndListener(problem, logger));
        }
        StopWatch watch = new StopWatch();
        watch.reset();
        watch.start();
        Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();
        watch.stop();
        Double jspritTime = watch.getCurrTimeInSeconds();
        VehicleRoutingProblemSolution best = Solutions.bestOf(solutions);
        ISolutionRouteResolver resolver = solutionRoureResolverFactory.get(false, DecompressionMethod.SIMPLE);
        ResolvedSolution resolvedSolution = resolver.resolveRoutes(problem, best, null);
        if(solutionRoutePath != null){
            solutionRouteWriter.writeRoute(resolvedSolution, solutionRoutePath);
        }
        SolutionInfo info = new SolutionInfo(problem.getProblemName(),
                problem.getDimensions(),
                ProblemType.FULL,
                problem.getBestKnownSolution(),
                best.getCost(),
                jspritTime,
                null,
                best.getRoutes().size(),
                null);
        return new VrpProblemSolution(vrp, best, best.getCost(), info);
    }

    @Override
    public VrpProblemSolution compressAndSolve(VrpProblem problem){
        return compressAndSolve(problem, DecompressionMethod.SIMPLE);
    }

    @Override
    public VrpProblemSolution compressAndSolve(VrpProblem problem, String dataPath){
        return compressAndSolve(problem, dataPath, DecompressionMethod.SIMPLE);
    }

    @Override
    public VrpProblemSolution compressAndSolve(VrpProblem problem, DecompressionMethod decompressionMethod){
        return compressAndSolve(problem, null, decompressionMethod);
    }

    @Override
    public VrpProblemSolution compressAndSolve(VrpProblem problem, String dataPath, DecompressionMethod decompressionMethod){
        return compressAndSolve(problem, dataPath, null, decompressionMethod);
    }

    @Override
    public VrpProblemSolution compressAndSolve(VrpProblem problem, String dataPath, String solutionRoutePath) {
        return compressAndSolve(problem, dataPath, solutionRoutePath, DecompressionMethod.SIMPLE);
    }

    @Override
    public VrpProblemSolution compressAndSolve(VrpProblem problem, String dataPath, String solutionRoutePath, DecompressionMethod decompressionMethod) {
        IVrpProblemToJSpritConverter converter = factory.getCompressedConverter(problem);
        ConversionResult conversionResult = converter.convertToJsprit(problem);
        VehicleRoutingProblem vrp = conversionResult.getConvertedProblem();
        Jsprit.Builder algorithmBuilder = Jsprit.Builder.newInstance(vrp);
        if(seed != null)
            algorithmBuilder.setRandom(new Random(seed));
        VehicleRoutingAlgorithm algorithm = algorithmBuilder.buildAlgorithm();
        algorithm.setMaxIterations(maxNumberOfIterations);
        if(dataPath != null){
            IDataLogger logger = new CsvDataLogger(dataPath);
            algorithm.addListener(new DataCollectorIterationEndListener(problem, logger));
        }
        StopWatch watch = new StopWatch();
        watch.reset();
        watch.start();
        Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();
        watch.stop();
        Double jspritTime = watch.getCurrTimeInSeconds();
        VehicleRoutingProblemSolution best = Solutions.bestOf(solutions);
        ISolutionRouteResolver resolver = solutionRoureResolverFactory.get(true, decompressionMethod);
        ResolvedSolution resolvedSolution = resolver.resolveRoutes(problem, best, conversionResult.getCompressionMap());
        SolutionInfo info = new SolutionInfo(problem.getProblemName(),
                problem.getDimensions(),
                ProblemType.COMPRESSED,
                problem.getBestKnownSolution(),
                resolvedSolution.getCost(),
                jspritTime,
                conversionResult.getCompressionResult().getTime(),
                best.getRoutes().size(),
                vrp.getNuActivities()+1);
        VrpProblemSolution solution = new VrpProblemSolution(vrp, best, resolvedSolution.getCost(), info);
        if(solutionRoutePath != null){
            solutionRouteWriter.writeRoute(resolvedSolution, solutionRoutePath);
        }
        return solution;
    }
}
