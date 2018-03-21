package compression;

import compression.input.IProblemReader;
import compression.input.VrpProblemReader;
import compression.input.parsing.vrp.VrpNonMapProblemParser;
import compression.model.vrp.VrpProblem;
import compression.output.vrp.VrpProblemWriter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CompressionApplication {

    public void run(){
        IProblemReader<VrpProblem> reader = new VrpProblemReader<VrpProblem>(new VrpNonMapProblemParser());
        VrpProblem problem = reader.readProblemInstanceFromResources("/benchmarks/E-n22-k4.vrp");

        VrpProblemWriter writer = new VrpProblemWriter();
        writer.writeProblem(problem);
        //IGraphHopperService ghService = new GraphHopperService(new GraphHopperResponseParser(), new RouteCacher(new SimpleMemoryStore<>()));
        //Route r1 = ghService.getRoute(problem.getClients().get(0), problem.getClients().get(1));
        //Route r2 = ghService.getRoute(problem.getClients().get(0), problem.getClients().get(1));
        //System.out.println(r1.equals(r2));
    }
}
