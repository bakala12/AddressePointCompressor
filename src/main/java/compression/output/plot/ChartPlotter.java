package compression.output.plot;

import compression.model.jsprit.PerformTestResults;
import javafx.util.Pair;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class ChartPlotter {

    private XYSeriesCollection prepareData(Map<Integer, Double> data){
        XYSeries series = new XYSeries("Plot");
        for(Map.Entry<Integer, Double> entry : data.entrySet()){
            series.add(entry.getKey(), entry.getValue());
        }
        XYSeriesCollection seriesCollection = new XYSeriesCollection();
        seriesCollection.addSeries(series);
        return seriesCollection;
    }

    private void saveToFile(JFreeChart chart, String path){
        try {
            ChartUtilities.saveChartAsJPEG(new File(path), chart, 500, 300);
        } catch (IOException ex){
            System.out.println(ex.getMessage());
        }
    }

    public void drawCostPlot(PerformTestResults results, String path){
        XYSeriesCollection data = prepareData(results.getProblemCostsPerIteration());
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Cost chart",
                "Iterations",
                "Cost",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        saveToFile(chart, path);
    }

    public void drawTimePlot(PerformTestResults results, String path){
        XYSeriesCollection data = prepareData(results.getProblemCostsPerIteration());
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Time chart",
                "Iterations",
                "Time",
                data,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );
        saveToFile(chart, path);
    }
}
