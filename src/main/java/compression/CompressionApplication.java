package compression;

import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.Solutions;
import compression.graph.branching.ITreeBranchFinder;
import compression.graph.branching.TreeBranchFinder;
import compression.graph.mst.IMinimalArborescenceFinder;
import compression.graph.mst.MinimalArborescenceFinder;
import compression.graphnew.*;
import compression.input.IProblemReader;
import compression.input.VrpProblemReader;
import compression.input.parsing.vrp.VrpProblemParser;
import compression.model.jsprit.VrpProblemSolution;
import compression.model.vrp.VrpProblem;
import compression.output.plot.ChartPlotter;
import compression.output.plot.IChartPlotter;
import compression.services.compression.CompressionService;
import compression.services.compression.IProblemToGraphConverter;
import compression.services.compression.ProblemGraph;
import compression.services.compression.ProblemToGraphConverter;
import compression.services.compression.graph.LocationEdge;
import compression.services.compression.graph.LocationGraph;
import compression.services.compression.graph.LocationVertex;
import compression.services.distance.DistanceService;
import compression.services.distance.IDistanceService;
import compression.services.jsprit.IJSpritService;
import compression.services.jsprit.JSpritService;
import lombok.NoArgsConstructor;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
public class CompressionApplication {

    private final IProblemReader<VrpProblem> problemReader = new VrpProblemReader<>(new VrpProblemParser());
    private final IProblemToGraphConverter<LocationVertex, LocationEdge, LocationGraph> problemConverter = new ProblemToGraphConverter();
    private final MinimalArborescenceFinder minimalArborescenceFinder = new MinimalArborescenceFinder();
    private final ITreeBranchFinder<LocationVertex, LocationEdge> treeBranchFinder = new TreeBranchFinder<>();
    private final IDistanceService distanceService = new DistanceService();
    private final CompressionService compressionService = new CompressionService(problemConverter, minimalArborescenceFinder, treeBranchFinder);
    private final IJSpritService service = new JSpritService(compressionService, distanceService);
    private final IChartPlotter chartPlotter = new ChartPlotter();

    public void run(String inputFile, String outputFile, Boolean useCompression, String dataPath, String plotPath){
        try {
            VrpProblem problem = problemReader.readProblemInstanceFromFile(inputFile);
            GraphConverter conv = new GraphConverter();
            SimpleDirectedWeightedGraph<GraphConverter.LocationVertex, Edge> gr = conv.convert(problem);
            IMinimumSpanningArborescenceFinder<GraphConverter.LocationVertex, Edge> t = new TarjanMinimumArborescenceFinder<>();
            GraphConverter.LocationVertex d = new GraphConverter.LocationVertex(problem.getDepot().getId(), problem.getDepot().getLocation());
            Set<Edge> ed = t.getSpanningArborescence(gr, d).getEdges();
            System.out.println("Finished");
//            VrpProblemSolution solution = null;
//            if (useCompression) {
//                System.out.println("Compressed problem");
//                solution = service.compressAndSolve(problem, dataPath);
//            } else {
//                System.out.println("Full problem");
//                solution = service.solve(problem, dataPath);
//            }
//            System.out.println("Finished");
//            VehicleRoutingProblemSolution best = Solutions.bestOf(solution.getSolutions());
//            PrintWriter printWriter = new PrintWriter(outputFile);
//            System.out.println(best.getCost());
//            SolutionPrinter.print(printWriter, solution.getProblem(), best, SolutionPrinter.Print.VERBOSE);
//            printWriter.flush();
//            if(dataPath!= null && chartPlotter != null){
//                System.out.println("Generating and saving plots");
//                chartPlotter.plotCostChart(dataPath, Paths.get(plotPath, "cost.jpeg").toString());
//                chartPlotter.plotTimeChart(dataPath, Paths.get(plotPath, "time.jpeg").toString());
//            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
