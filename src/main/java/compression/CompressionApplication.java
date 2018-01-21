package compression;

import compression.io.IProblemReader;
import compression.io.VrpProblemReader;
import compression.model.vrp.VrpProblem;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CompressionApplication {

    public void run(){
        IProblemReader reader = new VrpProblemReader();
        VrpProblem problem = reader.readProblemInstance("/vrp.json");
        System.out.println(problem.getClients().size());
    }
}
