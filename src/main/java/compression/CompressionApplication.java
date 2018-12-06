package compression;

import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.Solutions;
import compression.model.graph.*;
import compression.model.jsprit.DecompressionMethod;
import compression.model.jsprit.ProblemType;
import compression.model.jsprit.RunResult;
import compression.model.jsprit.VrpProblemSolution;
import compression.model.vrp.helpers.LocationVertex;
import compression.output.plot.ChartPlotter;
import compression.output.plot.IChartPlotter;
import compression.output.result.GeneralInfoWriter;
import compression.output.result.IGeneralInfoWriter;
import compression.output.result.ISolutionInfoWriter;
import compression.output.result.SolutionInfoWriter;
import compression.services.IProblemToGraphConverter;
import compression.services.ProblemToGraphConverter;
import compression.services.branching.ITreeBranchFinder;
import compression.services.branching.TreeBranchFinder;
import compression.services.compression.CompressionService;
import compression.services.compression.ICompressionService;
import compression.services.distance.DistanceService;
import compression.services.distance.IDistanceService;
import compression.services.jsprit.IJSpritService;
import compression.services.jsprit.JSpritService;
import compression.spanning.IMinimumSpanningArborescenceFinder;
import compression.spanning.*;
import compression.input.IProblemReader;
import compression.input.VrpProblemReader;
import compression.input.parsing.vrp.VrpProblemParser;
import compression.model.vrp.VrpProblem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.jfree.io.FileUtilities;

import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Main class of the application. Handles program flow.
 */
@NoArgsConstructor
public class CompressionApplication {

    private final IProblemReader<VrpProblem> problemReader = new VrpProblemReader<>(new VrpProblemParser());
    private final IProblemToGraphConverter<LocationVertex> problemConverter = new ProblemToGraphConverter();
    private final IMinimumSpanningArborescenceFinder<LocationVertex, Edge> minimalArborescenceFinder = new TarjanMinimumArborescenceFinder<>();
    private final ITreeBranchFinder<LocationVertex> treeBranchFinder = new TreeBranchFinder<>();
    private final IDistanceService distanceService = new DistanceService();
    private final ICompressionService compressionService = new CompressionService(problemConverter, minimalArborescenceFinder, treeBranchFinder);
    private final IJSpritService service = new JSpritService(compressionService, distanceService);
    private final IChartPlotter chartPlotter = new ChartPlotter();
    private final ISolutionInfoWriter solutionInfoWriter = new SolutionInfoWriter();
    private final IGeneralInfoWriter generalInfoWriter = new GeneralInfoWriter();

    static final class FilePathUtils{
        public static String getFileNameForRun(String path, int runNo){
            String ext = FilenameUtils.getExtension(path);
            return FilenameUtils.removeExtension(path) + runNo + "." + ext;
        }

        public static String getDirectoryForPlots(String path, int runNo){
            return FilenameUtils.getPathNoEndSeparator(path)+runNo;
        }
    }

    public void run(String inputFile, String outputFile, String resultFilePath, Boolean useCompression, String dataPath, String plotPath,
                    Integer iterations, String solutionRoutePath, DecompressionMethod decompressionMethod, Long seed, int numberOfRuns, String generalInfoPath){
        List<RunResult> results = new ArrayList<>();
        Random rand = new Random(seed);
        for(int i = 1; i <= numberOfRuns; i++) {
            //fix paths
            String  out = FilePathUtils.getFileNameForRun(outputFile, i);
            String result = FilePathUtils.getFileNameForRun(resultFilePath, i);
            String data = FilePathUtils.getFileNameForRun(dataPath, i);
            String solution = FilePathUtils.getFileNameForRun(solutionRoutePath, i);
            String plots = FilePathUtils.getDirectoryForPlots(plotPath, i);
            RunResult runResult = runInternal(inputFile, out, result, useCompression, data, plots, iterations, solution, decompressionMethod, rand.nextLong());
            if(dataPath!= null){
                System.out.println("Generating and saving plots");
                chartPlotter.plotCostChart(data, Paths.get(plotPath, "cost"+i+".jpeg").toString());
                chartPlotter.plotTimeChart(data, Paths.get(plotPath, "time"+i+".jpeg").toString());
            }
            results.add(runResult);
        }
        if(generalInfoPath != null){
            System.out.println("Generating general information about solutions");
            generalInfoWriter.writeGeneralSolution(generalInfoPath, results);
        }
    }

    public void run(String inputFile, String outputFile, String resultFilePath, Boolean useCompression, String dataPath, String plotPath,
                    Integer iterations, String solutionRoutePath, DecompressionMethod decompressionMethod, Long seed, String generalInfoPath){
        RunResult result = runInternal(inputFile, outputFile, resultFilePath, useCompression, dataPath, plotPath, iterations, solutionRoutePath, decompressionMethod, seed);
        if(dataPath!= null){
            System.out.println("Generating and saving plots");
            chartPlotter.plotCostChart(dataPath, Paths.get(plotPath, "cost.jpeg").toString());
            chartPlotter.plotTimeChart(dataPath, Paths.get(plotPath, "time.jpeg").toString());
        }
        if(generalInfoPath != null){
            System.out.println("Generating general information about solutions");
            List<RunResult> results = new LinkedList<>();
            results.add(result);
            generalInfoWriter.writeGeneralSolution(generalInfoPath, results);
        }
    }

    public RunResult runInternal(String inputFile, String outputFile, String resultFilePath, Boolean useCompression, String dataPath, String plotPath,
                Integer iterations, String solutionRoutePath, DecompressionMethod decompressionMethod, Long seed){
        try {
            VrpProblem problem = problemReader.readProblemInstanceFromFile(inputFile);
            VrpProblemSolution solution = null;
            service.setMaxNumberOfIterations(iterations);
            System.out.println("Using random seed: "+seed);
            service.setRandomSeed(seed);
            if (useCompression) {
                System.out.println("Compressed problem");
                solution = service.compressAndSolve(problem, dataPath, solutionRoutePath, decompressionMethod);
            } else {
                System.out.println("Full problem");
                solution = service.solve(problem, dataPath, solutionRoutePath);
            }
            System.out.println("Finished");
            try (PrintWriter printWriter = new PrintWriter(outputFile)) {
                System.out.println(solution.getCost());
                if(decompressionMethod == DecompressionMethod.GREEDY){
                    System.out.println("Greedy decompression: "+solution.getCost()+" simple decompression: "+solution.getBestSolution().getCost());
                }
                SolutionPrinter.print(printWriter, solution.getProblem(), solution.getBestSolution(), SolutionPrinter.Print.VERBOSE);
                printWriter.flush();
            }
            if(resultFilePath != null){
                solutionInfoWriter.writeSolution(resultFilePath, solution.getSolutionInfo());
            }
            return new RunResult(problem.getProblemName(),
                    problem.getBestKnownSolution(),
                    solution.getSolutionInfo().getOriginalSize(),
                    solution.getSolutionInfo().getCompressedSize(),
                    solution.getSolutionInfo().getUsedVehicles(),
                    solution.getSolutionInfo().getProblemType(),
                    solution.getSolutionInfo().getSolutionTime(),
                    solution.getSolutionInfo().getCompressionTime(),
                    decompressionMethod,
                    solution.getBestSolution().getCost(),
                    decompressionMethod == DecompressionMethod.GREEDY ? solution.getCost() : null);
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
}
