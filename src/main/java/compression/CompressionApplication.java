package compression;

import compression.cache.route.RouteCacher;
import compression.cache.store.SimpleMemoryStore;
import compression.io.IProblemReader;
import compression.io.VrpProblemReader;
import compression.model.vrp.Route;
import compression.model.vrp.VrpProblem;
import compression.parsing.input.VrpProblemParser;
import compression.parsing.web.GraphHopperResponseParser;
import compression.services.graphhopper.GraphHopperService;
import compression.services.graphhopper.IGraphHopperService;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CompressionApplication {

    public void run(){
        IProblemReader reader = new VrpProblemReader(new VrpProblemParser());
        VrpProblem problem = reader.readProblemInstance("/vrp.json");
        System.out.println(problem.getClients().size());

        IGraphHopperService ghService = new GraphHopperService(new GraphHopperResponseParser(), new RouteCacher(new SimpleMemoryStore<>()));
        Route r1 = ghService.getRoute(problem.getClients().get(0), problem.getClients().get(1));
        Route r2 = ghService.getRoute(problem.getClients().get(0), problem.getClients().get(1));
        System.out.println(r1.equals(r2));
    }
}
