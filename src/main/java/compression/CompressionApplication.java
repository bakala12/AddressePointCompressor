package compression;

import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.Solutions;
import compression.graph.IGraph;
import compression.graph.branching.ITreeBranchFinder;
import compression.graph.branching.TreeBranchFinder;
import compression.graph.mst.IMinimalArborescenceFinder;
import compression.graph.mst.MinimalArborescenceFinder;
import compression.input.IProblemReader;
import compression.input.VrpProblemReader;
import compression.input.parsing.vrp.VrpNonMapProblemParser;
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
        IProblemReader<VrpProblem> reader = new VrpProblemReader<VrpProblem>(new VrpNonMapProblemParser());
        VrpProblem problem = reader.readProblemInstanceFromResources("/benchmarks/E-n22-k4.vrp");

        IProblemToGraphConverter<LocationVertex, LocationEdge, LocationGraph> problemConverter = new NonMapProblemToGraphConverter();
        IMinimalArborescenceFinder minimalArborescenceFinder = new MinimalArborescenceFinder();
        ITreeBranchFinder<LocationVertex, LocationEdge> treeBranchFinder = new TreeBranchFinder<>();
        IDistanceService distanceService = new DistanceService();
        NonMapCompressionService compressionService = new NonMapCompressionService(problemConverter, minimalArborescenceFinder, treeBranchFinder);
        IJSpritService service = new JSpritService(compressionService, distanceService);
        VrpProblemSolution solution = service.compressAndSolve(problem);
    }
}
