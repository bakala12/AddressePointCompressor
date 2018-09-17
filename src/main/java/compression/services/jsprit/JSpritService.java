package compression.services.jsprit;

import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.util.Solutions;
import com.graphhopper.jsprit.core.util.StopWatch;
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
import compression.services.resolving.CompressedSolutionRouteResolver;
import compression.services.resolving.FullSolutionRouteResolver;
import compression.services.resolving.ISolutionRouteResolver;
import compression.services.resolving.ResolvedSolution;
import lombok.RequiredArgsConstructor;

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

    private final ICompressionService compressionService;
    private final IDistanceService distanceService;
    private final JSpritConvertersFactory factory = new JSpritConvertersFactory();
    private final IRouteWriter solutionRouteWriter = new RouteWriter();

    private final SolutionRouteResolverFactory solutionRoureResolverFactory = new SolutionRouteResolverFactory();

    private final class SolutionRouteResolverFactory{
        private final ISolutionRouteResolver fullSolutionRouteResolver = new FullSolutionRouteResolver();
        private final ISolutionRouteResolver compressedSolutionRouteResolver = new CompressedSolutionRouteResolver();

        public ISolutionRouteResolver get(boolean useCompression){
            return useCompression ? compressedSolutionRouteResolver : fullSolutionRouteResolver;
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
        return solve(problem, dataPath, null);
    }

    @Override
    public VrpProblemSolution solve(VrpProblem problem, String dataPath, String solutionRoutePath){
        IVrpProblemToJSpritConverter converter = factory.getConverter(problem);
        VehicleRoutingProblem vrp = converter.convertToJsprit(problem).getConvertedProblem();
        VehicleRoutingAlgorithm algorithm = Jsprit.createAlgorithm(vrp);
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
        SolutionInfo info = new SolutionInfo(problem.getProblemName(),
                problem.getDimensions(),
                ProblemType.FULL,
                problem.getBestKnownSolution(),
                best.getCost(),
                jspritTime,
                null,
                best.getRoutes().size(),
                null);
        VrpProblemSolution solution = new VrpProblemSolution(vrp, solutions, info, null);
        if(solutionRoutePath != null){
            ISolutionRouteResolver resolver = solutionRoureResolverFactory.get(false);
            ResolvedSolution resolvedSolution = resolver.resolveRoutes(problem, solution);
            solutionRouteWriter.writeRoute(resolvedSolution, solutionRoutePath);
        }
        return solution;
    }

    @Override
    public VrpProblemSolution compressAndSolve(VrpProblem problem){
        return compressAndSolve(problem, null);
    }

    @Override
    public VrpProblemSolution compressAndSolve(VrpProblem problem, String dataPath){
        return compressAndSolve(problem, dataPath, null);
    }

    @Override
    public VrpProblemSolution compressAndSolve(VrpProblem problem, String dataPath, String solutionRoutePath) {
        IVrpProblemToJSpritConverter converter = factory.getCompressedConverter(problem);
        ConversionResult conversionResult = converter.convertToJsprit(problem);
        VehicleRoutingProblem vrp = conversionResult.getConvertedProblem();
        Jsprit.Builder algorithmBuilder = Jsprit.Builder.newInstance(vrp);
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
        SolutionInfo info = new SolutionInfo(problem.getProblemName(),
                problem.getDimensions(),
                ProblemType.COMPRESSED,
                problem.getBestKnownSolution(),
                best.getCost(),
                jspritTime,
                conversionResult.getCompressionResult().getTime(),
                best.getRoutes().size(),
                vrp.getNuActivities()+1);
        VrpProblemSolution solution = new VrpProblemSolution(vrp, solutions, info, conversionResult.getCompressionMap());
        if(solutionRoutePath != null){
            ISolutionRouteResolver resolver = solutionRoureResolverFactory.get(true);
            ResolvedSolution resolvedSolution = resolver.resolveRoutes(problem, solution);
            solutionRouteWriter.writeRoute(resolvedSolution, solutionRoutePath);
        }
        return solution;
    }
}
