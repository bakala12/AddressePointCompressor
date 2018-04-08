package compression;

import com.graphhopper.jsprit.core.reporting.SolutionPrinter;
import com.graphhopper.jsprit.core.util.Solutions;
import compression.input.IProblemReader;
import compression.input.VrpProblemReader;
import compression.input.parsing.vrp.VrpNonMapProblemParser;
import compression.model.jsprit.VrpProblemSolution;
import compression.model.vrp.VrpProblem;
import compression.output.plot.ChartPlotter;
import compression.output.plot.IChartPlotter;
import compression.output.vrp.VrpProblemWriter;
import compression.services.jsprit.IJSpritService;
import compression.services.jsprit.JSpritService;
import lombok.NoArgsConstructor;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@NoArgsConstructor
public class CompressionApplication {

    public void run() {
        IProblemReader<VrpProblem> reader = new VrpProblemReader<VrpProblem>(new VrpNonMapProblemParser());
        VrpProblem problem = reader.readProblemInstanceFromResources("/benchmarks/E-n22-k4.vrp");

        VrpProblemWriter writer = new VrpProblemWriter();
        writer.writeProblem(problem);

        IJSpritService jSpritService = new JSpritService();
        jSpritService.setMaxNumberOfIterations(10000);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String dataPath = "./solutions/data/" + problem.getProblemName() + "_" + timeStamp + ".csv";
        VrpProblemSolution solution = jSpritService.solve(problem, dataPath);

        SolutionPrinter.print(solution.getProblem(), Solutions.bestOf(solution.getSolutions()), SolutionPrinter.Print.VERBOSE);

        IChartPlotter chartPlotter = new ChartPlotter();
        chartPlotter.plotCostChart(dataPath,"./solutions/plots/"+problem.getProblemName()+"_cost_"+timeStamp+".jpeg");
        chartPlotter.plotTimeChart(dataPath, "./solutions/plots/"+problem.getProblemName()+"_time_"+timeStamp+".jpeg");
    }
}
