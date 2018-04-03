package compression.output.vrp;

import compression.model.jsprit.PerformTestResults;

public class TestResultWriter {
    public void printTestResults(PerformTestResults results){
        System.out.println("Problem results "+ results.getProblem().getProblemName()+ ":");
        System.out.println("Best solution: "+ results.getProblem().getBestKnownSolution());
        for (Integer iter: results.getIterations()) {
            System.out.println("Iterations: "+iter+ " Cost: "+ results.getProblemCostsPerIteration().get(iter)+" Time: "+results.getTimePerIteration().get(iter));
        }
    }
}
