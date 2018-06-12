package compression;

import compression.model.graph.*;
import compression.model.vrp.helpers.LocationVertex;
import compression.services.ProblemToGraphConverter;
import compression.services.branching.ITreeBranchFinder;
import compression.services.branching.TreeBranchFinder;
import compression.spanning.IMinimumSpanningArborescenceFinder;
import compression.spanning.*;
import compression.input.IProblemReader;
import compression.input.VrpProblemReader;
import compression.input.parsing.vrp.VrpProblemParser;
import compression.model.vrp.VrpProblem;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@NoArgsConstructor
public class CompressionApplication {

    private final IProblemReader<VrpProblem> problemReader = new VrpProblemReader<>(new VrpProblemParser());
    //private final IProblemToGraphConverter<LocationVertex, LocationEdge, LocationGraph> problemConverter = new ProblemToGraphConverter();
    //private final MinimalArborescenceFinder minimalArborescenceFinder = new MinimalArborescenceFinder();
    //private final ITreeBranchFinder<LocationVertex, LocationEdge> treeBranchFinder = new TreeBranchFinder<>();
    //private final IDistanceService distanceService = new DistanceService();
    //private final CompressionService compressionService = new CompressionService(problemConverter, minimalArborescenceFinder, treeBranchFinder);
    //private final IJSpritService service = new JSpritService(compressionService, distanceService);
    //private final IChartPlotter chartPlotter = new ChartPlotter();

    public void run(String inputFile, String outputFile, Boolean useCompression, String dataPath, String plotPath){
        try {
            VrpProblem problem = problemReader.readProblemInstanceFromFile(inputFile);
            ProblemToGraphConverter conv = new ProblemToGraphConverter();
            RoutedGraph<LocationVertex, Edge> gr = conv.convert(problem);
            IMinimumSpanningArborescenceFinder<LocationVertex, Edge> t = new TarjanMinimumArborescenceFinder<>();
            LocationVertex d = (LocationVertex) gr.getGraph().vertexSet().toArray()[0];
            IMinimumSpanningArborescence<LocationVertex, Edge> arb = t.getSpanningArborescence(gr.getGraph(), d);
            Set<Edge> ed = arb.getEdges();
            for(Edge e : ed){
                System.out.println(e);
            }
            System.out.println("Finished");
            ITreeBranchFinder<LocationVertex> brFinder = new TreeBranchFinder<>();
            List<TreeBranch<LocationVertex>> branches = brFinder.findBranches(arb);
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
