package compression.output.plot;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.LogAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.axis.NumberAxis;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class ChartPlotter {

    private XYSeriesCollection prepareData(Map<Integer, Double> data, String name){
        XYSeries series = new XYSeries(name);
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

//    private void addOptimalCost(XYSeriesCollection data, PerformTestResults results){
//        XYSeries series = new XYSeries("Optimal solution");
//        Double optimalSolution = results.getProblem().getBestKnownSolution();
//        for(Integer i : results.getIterations()){
//            series.add(i, optimalSolution);
//        }
//        data.addSeries(series);
//    }

//    public void drawCostPlot(PerformTestResults results, String path){
//        XYSeriesCollection data = prepareData(results.getProblemCostsPerIteration(), "Cost");
//        addOptimalCost(data, results);
//        Double opt = results.getProblem().getBestKnownSolution();
//        Double lower = 0.0;
//        Double upper = Collections.max(results.getProblemCostsPerIteration().values())*1.2;
//        if(opt > 0){
//            lower = 0.8*opt;
//        }
//        NumberAxis yAxis = new NumberAxis("cost");
//        yAxis.setRange(lower, upper);
//        LogAxis xAxis = new LogAxis("iterations");
//        XYPlot plot = new XYPlot(data, xAxis, yAxis, new XYLineAndShapeRenderer(true, false));
//        JFreeChart chart = new JFreeChart("Result cost", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
//        saveToFile(chart, path);
//    }
//
//    public void drawTimePlot(PerformTestResults results, String path){
//        XYSeriesCollection data = prepareData(results.getTimePerIteration(), "Time");
//        NumberAxis yAxis = new NumberAxis("time in seconds");
//        LogAxis xAxis = new LogAxis("iterations");
//        XYPlot plot = new XYPlot(data, xAxis, yAxis, new XYLineAndShapeRenderer(true, false));
//        JFreeChart chart = new JFreeChart("Calculation time", JFreeChart.DEFAULT_TITLE_FONT, plot, false);
//        saveToFile(chart, path);
//    }
}
