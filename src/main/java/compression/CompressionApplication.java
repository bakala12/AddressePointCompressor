package compression;

import compression.io.IProblemReader;
import compression.io.VrpProblemReader;
import compression.model.vrp.VrpProblem;
import compression.parsing.input.VrpProblemParser;
import compression.services.graphhopper.GraphHopperService;
import compression.services.graphhopper.IGraphHopperService;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CompressionApplication {

    public void run(){
        IProblemReader reader = new VrpProblemReader(new VrpProblemParser());
        VrpProblem problem = reader.readProblemInstance("/vrp.json");
        System.out.println(problem.getClients().size());

        IGraphHopperService ghService = new GraphHopperService();
        ghService.getRoute(problem.getClients().get(0), problem.getClients().get(1));
    }
}
