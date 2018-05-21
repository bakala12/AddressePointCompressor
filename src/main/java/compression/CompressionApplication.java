package compression;

import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.Solutions;
import compression.graph.IGraph;
import compression.graph.branching.ITreeBranchFinder;
import compression.graph.branching.TreeBranchFinder;
import compression.graph.mst.IMinimalArborescenceFinder;
import compression.graph.mst.MinimalArborescenceFinder;
import compression.input.IProblemReader;
import compression.input.VrpProblemReader;
import compression.input.parsing.vrp.VrpProblemParser;
import compression.model.jsprit.VrpProblemSolution;
import compression.model.vrp.VrpProblem;
import compression.output.plot.ChartPlotter;
import compression.output.plot.IChartPlotter;
import compression.output.vrp.VrpProblemWriter;
import compression.services.compression.IProblemToGraphConverter;
import compression.services.compression.ProblemGraph;
import compression.services.compression.nonmap.NonMapCompressionService;
import compression.services.compression.nonmap.NonMapProblemToGraphConverter;
import compression.services.compression.nonmap.graph.LocationEdge;
import compression.services.compression.nonmap.graph.LocationGraph;
import compression.services.compression.nonmap.graph.LocationVertex;
import compression.services.distance.DistanceService;
import compression.services.distance.IDistanceService;
import compression.services.jsprit.IJSpritService;
import compression.services.jsprit.JSpritService;
import lombok.NoArgsConstructor;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@NoArgsConstructor
public class CompressionApplication {

    public void run() {
        IProblemReader<VrpProblem> reader = new VrpProblemReader<VrpProblem>(new VrpProblemParser());
        String[] benchmarks = new String[]{
                //"E-n13-k4.vrp", //- exception
                "E-n22-k4.vrp",
                "E-n23-k3.vrp",
                "E-n30-k3.vrp",
                //"E-n31-k7.vrp", //- exception
                "E-n33-k4.vrp",
                "E-n51-k5.vrp",
                "E-n76-k7.vrp",
                "E-n76-k8.vrp",
                "E-n76-k10.vrp",
                "E-n101-k8.vrp",
                "E-n101-k14.vrp"};

        for(String b : benchmarks){
            try {
                VrpProblem problem = reader.readProblemInstanceFromResources("/benchmarks/" + b);
                IProblemToGraphConverter<LocationVertex, LocationEdge, LocationGraph> problemConverter = new NonMapProblemToGraphConverter();
                IMinimalArborescenceFinder minimalArborescenceFinder = new MinimalArborescenceFinder();
                ITreeBranchFinder<LocationVertex, LocationEdge> treeBranchFinder = new TreeBranchFinder<>();
                IDistanceService distanceService = new DistanceService();
                NonMapCompressionService compressionService = new NonMapCompressionService(problemConverter, minimalArborescenceFinder, treeBranchFinder);
                IJSpritService service = new JSpritService(compressionService, distanceService);
                //Solving original problem
                String dataPath = "./solutions/data/" + problem.getProblemName() + ".csv";
                String dataPath1 = "./solutions/data/" + problem.getProblemName() + "-compressed.csv";
                VrpProblemSolution solutions = service.compressAndSolve(problem, dataPath1);
                VehicleRoutingProblemSolution best = Solutions.bestOf(solutions.getSolutions());
                //SolutionPrinter.print(solutions.getProblem(), best, SolutionPrinter.Print.VERBOSE);
                VehicleRoutingProblemSolution opt = Solutions.bestOf(service.solve(problem, dataPath).getSolutions());
                System.out.println("Problem: " + problem.getProblemName());
                System.out.println("Optimal: " + problem.getBestKnownSolution());
                System.out.println("Best full: " + opt.getCost());
                System.out.println("Best compressed: " + best.getCost());
                System.out.println("Original dimension = " + problem.getDimensions() + " Compressed dimension = " + solutions.getProblem().getJobs().size());
            } catch (Exception ex){
                System.out.println(b);
                ex.printStackTrace();
            }
      }
        IChartPlotter plotter = new ChartPlotter();
        for (String b : benchmarks) {
            String bb = b.replace(".vrp", "");
            plotter.plotTimeChart("./solutions/data/" + bb + ".csv", "./solutions/plots/" + bb + "-time.jpeg");
            plotter.plotTimeChart("./solutions/data/" + bb + "-compressed.csv", "./solutions/plots/" + bb + "-compressed-time.jpeg");
        }
    }
}
