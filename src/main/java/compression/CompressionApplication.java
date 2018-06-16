package compression;

import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.Solutions;
import compression.model.graph.*;
import compression.model.jsprit.VrpProblemSolution;
import compression.model.vrp.helpers.LocationVertex;
import compression.output.plot.ChartPlotter;
import compression.output.plot.IChartPlotter;
import compression.output.result.ISolutionInfoWriter;
import compression.output.result.SolutionInfoWriter;
import compression.services.IProblemToGraphConverter;
import compression.services.ProblemToGraphConverter;
import compression.services.branching.ITreeBranchFinder;
import compression.services.branching.TreeBranchFinder;
import compression.services.compression.CompressionService;
import compression.services.compression.ICompressionService;
import compression.services.distance.DistanceService;
import compression.services.distance.IDistanceService;
import compression.services.jsprit.IJSpritService;
import compression.services.jsprit.JSpritService;
import compression.spanning.IMinimumSpanningArborescenceFinder;
import compression.spanning.*;
import compression.input.IProblemReader;
import compression.input.VrpProblemReader;
import compression.input.parsing.vrp.VrpProblemParser;
import compression.model.vrp.VrpProblem;
import lombok.NoArgsConstructor;

import java.io.PrintWriter;
import java.nio.file.Paths;

@NoArgsConstructor
public class CompressionApplication {

    private final IProblemReader<VrpProblem> problemReader = new VrpProblemReader<>(new VrpProblemParser());
    private final IProblemToGraphConverter<LocationVertex> problemConverter = new ProblemToGraphConverter();
    private final IMinimumSpanningArborescenceFinder<LocationVertex, Edge> minimalArborescenceFinder = new TarjanMinimumArborescenceFinder<>();
    private final ITreeBranchFinder<LocationVertex> treeBranchFinder = new TreeBranchFinder<>();
    private final IDistanceService distanceService = new DistanceService();
    private final ICompressionService compressionService = new CompressionService(problemConverter, minimalArborescenceFinder, treeBranchFinder);
    private final IJSpritService service = new JSpritService(compressionService, distanceService);
    private final IChartPlotter chartPlotter = new ChartPlotter();
    private final ISolutionInfoWriter solutionInfoWriter = new SolutionInfoWriter();

    public void run(String inputFile, String outputFile, String resultFilePath, Boolean useCompression, String dataPath, String plotPath, Integer iterations){
        try {
            VrpProblem problem = problemReader.readProblemInstanceFromFile(inputFile);
            VrpProblemSolution solution = null;
            service.setMaxNumberOfIterations(iterations);
            if (useCompression) {
                System.out.println("Compressed problem");
                solution = service.compressAndSolve(problem, dataPath);
            } else {
                System.out.println("Full problem");
                solution = service.solve(problem, dataPath);
            }
            System.out.println("Finished");
            VehicleRoutingProblemSolution best = Solutions.bestOf(solution.getSolutions());
            try (PrintWriter printWriter = new PrintWriter(outputFile)) {
                System.out.println(best.getCost());
                SolutionPrinter.print(printWriter, solution.getProblem(), best, SolutionPrinter.Print.VERBOSE);
                printWriter.flush();
            }
            if(resultFilePath != null){
                solutionInfoWriter.writeSolution(resultFilePath, solution.getSolutionInfo());
            }
            if(dataPath!= null){
                System.out.println("Generating and saving plots");
                chartPlotter.plotCostChart(dataPath, Paths.get(plotPath, "cost.jpeg").toString());
                chartPlotter.plotTimeChart(dataPath, Paths.get(plotPath, "time.jpeg").toString());
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
