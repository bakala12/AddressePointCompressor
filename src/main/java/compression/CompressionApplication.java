package compression;

import compression.input.IProblemReader;
import compression.input.VrpProblemReader;
import compression.input.parsing.vrp.VrpNonMapProblemParser;
import compression.model.jsprit.VrpSolution;
import compression.model.vrp.VrpProblem;
import compression.output.vrp.VrpProblemWriter;
import compression.output.vrp.VrpSolutionWriter;
import compression.services.jsprit.IJSpritService;
import compression.services.jsprit.JSpritService;
import lombok.NoArgsConstructor;

import java.util.Collection;

@NoArgsConstructor
public class CompressionApplication {

    public void run(){
        IProblemReader<VrpProblem> reader = new VrpProblemReader<VrpProblem>(new VrpNonMapProblemParser());
        VrpProblem problem = reader.readProblemInstanceFromResources("/benchmarks/E-n13-k4.vrp");

        VrpProblemWriter writer = new VrpProblemWriter();
        writer.writeProblem(problem);

        IJSpritService jSpritService = new JSpritService();
        Collection<VrpSolution> solutions = jSpritService.solve(problem);

        VrpSolutionWriter solutionWriter = new VrpSolutionWriter();
        solutionWriter.writeSolution(problem, solutions);
    }
}
