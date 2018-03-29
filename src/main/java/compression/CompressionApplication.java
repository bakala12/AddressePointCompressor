package compression;

import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.Solutions;
import compression.graph.mst.MinimalSpanningTreeFinder;
import compression.input.IProblemReader;
import compression.input.VrpProblemReader;
import compression.input.parsing.vrp.VrpNonMapProblemParser;
import compression.model.jsprit.VrpProblemSolution;
import compression.model.vrp.VrpProblem;
import compression.output.vrp.VrpProblemWriter;
import compression.services.compression.ICompressionService;
import compression.services.compression.nonmap.NonMapCompressionService;
import compression.services.compression.nonmap.NonMapProblemToGraphConverter;
import compression.services.jsprit.IJSpritService;
import compression.services.jsprit.JSpritService;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CompressionApplication {

    public void run(){
        IProblemReader<VrpProblem> reader = new VrpProblemReader<VrpProblem>(new VrpNonMapProblemParser());
        VrpProblem problem = reader.readProblemInstanceFromResources("/benchmarks/E-n22-k4.vrp");

        VrpProblemWriter writer = new VrpProblemWriter();
        writer.writeProblem(problem);

        IJSpritService jSpritService = new JSpritService();
        VrpProblemSolution solution = jSpritService.solve(problem);

        SolutionPrinter.print(solution.getProblem(), Solutions.bestOf(solution.getSolutions()), SolutionPrinter.Print.VERBOSE);

        ICompressionService service = new NonMapCompressionService(new NonMapProblemToGraphConverter(), new MinimalSpanningTreeFinder());
        service.compress(problem);
    }
}
