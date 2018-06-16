package compression.output.result;

import compression.model.jsprit.ProblemType;
import compression.model.jsprit.SolutionInfo;

import java.io.IOException;
import java.io.PrintWriter;

public class SolutionInfoWriter implements ISolutionInfoWriter {

    @Override
    public void writeSolution(String path, SolutionInfo solutionInfo) {
        boolean compressed = solutionInfo.getProblemType() == ProblemType.COMPRESSED;
        Double bestKnown = solutionInfo.getBestKnownSolution();
        try(PrintWriter writer = new PrintWriter(path)){
            writer.println(compressed);
            writer.println(solutionInfo.getBenchmark());
            writer.println(bestKnown == 0.0 ? "Unknown" : bestKnown.toString());
            writer.println(solutionInfo.getOriginalSize());
            writer.println(solutionInfo.getSolutionValue());
            writer.println(solutionInfo.getSolutionTime());
            writer.println(solutionInfo.getUsedVehicles());
            if(compressed){
                writer.println(solutionInfo.getCompressedSize());
                writer.println(solutionInfo.getCompressionTime());
            }
        } catch (IOException e) {
            System.out.println("Unable to write solution info to a file: "+e);
        }
    }
}
