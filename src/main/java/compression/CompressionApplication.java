package compression;

import compression.io.IProblemReader;
import compression.io.VrpProblemReader;
import compression.io.parsing.input.VrpNonMapProblemParser;
import compression.model.vrp.VrpNonMapProblem;
import compression.model.vrp.VrpProblem;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CompressionApplication {

    public void run(){
        //IProblemReader<VrpProblem> reader = new VrpProblemReader<VrpNonMapProblem>(new VrpNonMapProblemParser());
        //VrpProblem problem = reader.readProblemInstanceFromResources("/vrp.json");
        //System.out.println(problem.getClients().size());

        //IGraphHopperService ghService = new GraphHopperService(new GraphHopperResponseParser(), new RouteCacher(new SimpleMemoryStore<>()));
        //Route r1 = ghService.getRoute(problem.getClients().get(0), problem.getClients().get(1));
        //Route r2 = ghService.getRoute(problem.getClients().get(0), problem.getClients().get(1));
        //System.out.println(r1.equals(r2));
    }
}
