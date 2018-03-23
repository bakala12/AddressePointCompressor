package compression.output.vrp;

import compression.model.jsprit.VrpSolution;
import compression.model.vrp.VrpProblem;

import java.util.Collection;

public class VrpSolutionWriter {
    public void writeSolution(VrpProblem problem, Collection<VrpSolution> solutions){
        System.out.println("SOLUTIONS of problem: "+problem.getProblemName());
        System.out.println("BEST KNOWN SOLUTION: "+problem.getBestKnownSolution());
        Integer num = 1;
        for(VrpSolution sol : solutions){
            System.out.println("SOLUTION " + num + " COST: "+ sol.getCost());
            num++;
        }
    }
}
