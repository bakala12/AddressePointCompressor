package compression.output.result;

import compression.model.jsprit.DecompressionMethod;
import compression.model.jsprit.ProblemType;
import compression.model.jsprit.RunResult;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of IGeneralInfoWriter interface.
 */
public class GeneralInfoWriter implements IGeneralInfoWriter {
    /**
     * Writes information about several solutions to a file.
     * @param path File path.
     * @param runResults A list of solutions.
     */
    @Override
    public void writeGeneralSolution(String path, List<RunResult> runResults) {
        try(PrintWriter writer = new PrintWriter(path)){
            RunResult first = runResults.get(0);
            GeneralInfo info = getGeneralInfo(runResults);
            writer.println("Benchmark: "+first.getBenchmark());
            writer.println("OptimalSolution: "+first.getBestKnownSolution());
            writer.println("ProblemSize: "+first.getOriginalSize());
            writer.println("UseCompression: " + (first.getProblemType() == ProblemType.FULL ? 0 : 1));
            writer.println("DecompressionMode: "+(first.getDecompressionMethod() == DecompressionMethod.SIMPLE ? 0 : 1));
            writer.println("UsedVehicles: "+first.getUsedVehicles());

            writer.println("CompressedProblemSize: "+(first.getProblemType() == ProblemType.FULL ? 0 : first.getCompressedSize()));
            if(first.getProblemType() == ProblemType.COMPRESSED)
                writer.println("CompressionLevel: "+(((double)first.getCompressedSize()) / first.getOriginalSize() * 100));
            else
                writer.println("NULL");

            writer.println("SimpleDecompressionSolutionAvg: "+info.simpleDecompressionSolutionCost.getAverage());
            writer.println("SimpleDecompressionSolutionSD: "+info.simpleDecompressionSolutionCost.getStandardDeviation());
            writer.println("SimpleDecompressionSolutionMin: "+info.simpleDecompressionSolutionCost.getMinValue());
            writer.println("SimpleDecompressionSolutionMax: "+info.simpleDecompressionSolutionCost.getMaxValue());

            if(first.getDecompressionMethod() == DecompressionMethod.GREEDY) {
                writer.println("GreedyDecompressionSolutionAvg: " + info.greedyDecompressionSolutionCost.getAverage());
                writer.println("GreedyDecompressionSolutionSD: " + info.greedyDecompressionSolutionCost.getStandardDeviation());
                writer.println("GreedyDecompressionSolutionMin: " + info.greedyDecompressionSolutionCost.getMinValue());
                writer.println("GreedyDecompressionSolutionMax: " + info.greedyDecompressionSolutionCost.getMaxValue());
            } else {
                writer.println("NULL");
                writer.println("NULL");
                writer.println("NULL");
                writer.println("NULL");
            }
            writer.println("SolutionTimeAvg: "+info.solutionTime.getAverage());
            writer.println("SolutionTimeSD: "+info.solutionTime.getStandardDeviation());
            writer.println("SolutionTimeMin: "+info.solutionTime.getMinValue());
            writer.println("SolutionTimeMax: "+info.solutionTime.getMaxValue());

            if(first.getProblemType() == ProblemType.COMPRESSED) {
                writer.println("CompressionTimeAvg: " + info.compressionTime.getAverage());
                writer.println("CompressionTimeSD: " + info.compressionTime.getStandardDeviation());
                writer.println("CompressionTimeMin: " + info.compressionTime.getMinValue());
                writer.println("CompressionTimeMax: " + info.compressionTime.getMaxValue());
            } else {
                writer.println("NULL");
                writer.println("NULL");
                writer.println("NULL");
                writer.println("NULL");
            }
        } catch (IOException e) {
            System.out.println("Unable to write general solutions info to a file: "+e);
        }
    }

    @NoArgsConstructor
    private class GeneralValueInfo{
        @Getter
        private Double minValue;
        @Getter
        private Double maxValue;

        private List<Double> values = new ArrayList<>();
        private int count = 0;

        private void setMinValue(Double val){
            if(minValue == null || minValue > val){
                minValue = val;
            }
        }

        private void setMaxValue(Double val){
            if(maxValue == null || maxValue < val){
                maxValue = val;
            }
        }

        public void loadValue(Double val){
            setMinValue(val);
            setMaxValue(val);
            if(val != null){
                values.add(val);
                count++;
            }
        }

        public Double getAverage(){
            Double sum = 0.0;
            for(Double v : values){
                sum += v;
            }
            return sum/count;
        }

        public Double getVariancy(){
            Double avg = getAverage();
            Double sum = 0.0;
            for(Double v : values){
                Double x = v - avg;
                sum += x*x;
            }
            return sum/count;
        }

        public Double getStandardDeviation(){
            return Math.sqrt(getVariancy());
        }
    }

    @AllArgsConstructor
    private class GeneralInfo{
        @Getter
        private GeneralValueInfo simpleDecompressionSolutionCost;
        @Getter
        private GeneralValueInfo greedyDecompressionSolutionCost;
        @Getter
        private GeneralValueInfo solutionTime;
        @Getter
        private GeneralValueInfo compressionTime;
    }

    private GeneralInfo getGeneralInfo(List<RunResult> results){
        GeneralValueInfo simpleDecompressionCost = new GeneralValueInfo();
        GeneralValueInfo greedyDecompressionCost = new GeneralValueInfo();
        GeneralValueInfo solutionTime = new GeneralValueInfo();
        GeneralValueInfo compressionTime = new GeneralValueInfo();
        for(RunResult run : results){
            simpleDecompressionCost.loadValue(run.getSimpleDecompressionSolutionValue());
            greedyDecompressionCost.loadValue(run.getGreedyDecompressionSolutionValue());
            solutionTime.loadValue(run.getSolutionTime());
            compressionTime.loadValue(run.getCompressionTime());
        }
        return new GeneralInfo(simpleDecompressionCost, greedyDecompressionCost, solutionTime, compressionTime);
    }
}
