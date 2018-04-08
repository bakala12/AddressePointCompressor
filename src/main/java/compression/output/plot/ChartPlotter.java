package compression.output.plot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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

import java.io.*;

public class ChartPlotter implements IChartPlotter {

    @Override
    public void plotCostChart(String sourceDataFilePath, String outputFilePath) {
        ChartOptions options = new ChartOptions();
        setDefault(options);
        options.setTitle("Solution cost");
        options.setXAxisLabel("Iterations");
        options.setYAxisLabel("Cost");
        plotCostChart(sourceDataFilePath, outputFilePath, options);
    }

    @Override
    public void plotTimeChart(String sourceDataFilePath, String outputFilePath) {
        ChartOptions options = new ChartOptions();
        setDefault(options);
        options.setTitle("Calculation time");
        options.setXAxisLabel("Iterations");
        options.setYAxisLabel("Time in seconds");
        plotTimeChart(sourceDataFilePath, outputFilePath, options);
    }

    @Override
    public void plotCostChart(String sourceDataFilePath, String outputFilePath, ChartOptions options) {
        ReadDataResult cost = readData(sourceDataFilePath, "Cost", ITERATION_COLUMN, COST_COLUMN, SEPARATOR);
        ReadDataResult bestCost = readData(sourceDataFilePath, "Best solution", ITERATION_COLUMN, BEST_COLUMN, SEPARATOR);
        XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(cost.getSeries());
        collection.addSeries(bestCost.getSeries());
        JFreeChart chart = createChart(collection, options, cost.getBoundingBox());
        saveToFile(chart, outputFilePath);
    }

    @Override
    public void plotTimeChart(String sourceDataFilePath, String outputFilePath, ChartOptions options) {
        ReadDataResult time = readData(sourceDataFilePath, "Time", ITERATION_COLUMN, TIME_COLUMN, SEPARATOR);
        XYSeriesCollection collection = new XYSeriesCollection();
        collection.addSeries(time.getSeries());
        JFreeChart chart = createChart(collection, options, time.getBoundingBox());
        saveToFile(chart, outputFilePath);
    }

    private JFreeChart createChart(XYSeriesCollection data, ChartOptions options, BoundingBox boundingBox){
        boundingBox = boundingBox.scale(options.getXScalingFactor(), options.getYScalingFactor());
        NumberAxis yAxis = new NumberAxis(options.getYAxisLabel());
        NumberAxis xAxis = new NumberAxis(options.getXAxisLabel());
        if(options.getXScalingFactor() != null)
            xAxis.setRange(boundingBox.getXmin(), boundingBox.getXmax());
        if(options.getYScalingFactor() != null)
            yAxis.setRange(boundingBox.getYmin(), boundingBox.getYmax());
        XYPlot plot = new XYPlot(data, xAxis, yAxis, new XYLineAndShapeRenderer(true, false));
        return new JFreeChart(options.getTitle(), JFreeChart.DEFAULT_TITLE_FONT, plot, true);
    }

    private void saveToFile(JFreeChart chart, String outputPath){
        try {
            ChartUtilities.saveChartAsJPEG(new File(outputPath), chart, 500, 300);
        } catch (IOException ex){
            throw new ChartException("Cannot save chart to file " + outputPath, ex);
        }
    }

    private static final double DEFAULTWIDTH = 500;
    private static final double DEFAULTHEIGHT = 300;

    private void setDefault(ChartOptions options){
        options.setWidth(DEFAULTWIDTH);
        options.setHeight(DEFAULTHEIGHT);
        options.setXScalingFactor(ScalingFactor.DEFAULT);
        options.setYScalingFactor(new ScalingFactor(0.8, 1.2));
    }

    private static final int ITERATION_COLUMN = 1;
    private static final int COST_COLUMN = 3;
    private static final int TIME_COLUMN = 2;
    private static final int BEST_COLUMN = 5;
    private static final String SEPARATOR = ",";

    @AllArgsConstructor
    private static class ReadDataResult{
        @Getter
        private XYSeries series;
        @Getter
        private BoundingBox boundingBox;
    }

    @AllArgsConstructor
    private static class BoundingBox{
        @Getter
        private double xmin;
        @Getter
        private double xmax;
        @Getter
        private double ymin;
        @Getter
        private double ymax;

        public BoundingBox scale(ScalingFactor xScalingFactor, ScalingFactor yScalingFactor){
            return scaleX(xScalingFactor).scaleY(yScalingFactor);
        }

        public BoundingBox scaleX(ScalingFactor scalingFactor){
            if(scalingFactor == null)
                scalingFactor = ScalingFactor.DEFAULT;
            Double lower = scalingFactor.getLowerScale();
            Double upper = scalingFactor.getUpperScale();
            return new BoundingBox(xmin*lower, xmax*upper, ymin, ymax);
        }

        public BoundingBox scaleY(ScalingFactor scalingFactor){
            if(scalingFactor == null)
                scalingFactor = ScalingFactor.DEFAULT;
            Double lower = scalingFactor.getLowerScale();
            Double upper = scalingFactor.getUpperScale();
            return new BoundingBox(xmin, xmax, ymin*lower, ymax*upper);
        }
    }

    private ReadDataResult readData(String dataFile, String dataName, int xColumn, int yColumn, String separator){
        XYSeries series = new XYSeries(dataName);
        Double xmin = Double.MAX_VALUE;
        Double ymin = Double.MAX_VALUE;
        Double xmax = Double.MIN_VALUE;
        Double ymax = Double.MIN_VALUE;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dataFile)))){
            String line;
            while((line = reader.readLine()) != null){
                String[] split = line.split(separator);
                if(xColumn >= split.length || yColumn >= split.length || xColumn <0 || yColumn <0)
                    throw new ChartException("Invalid file data");
                Double x = Double.parseDouble(split[xColumn]);
                Double y = Double.parseDouble(split[yColumn]);
                series.add(x,y);
                if(x<xmin)
                    xmin = x;
                if(x>xmax)
                    xmax = x;
                if(y < ymin)
                    ymin = y;
                if(y > ymax)
                    ymax = y;
            }
            return new ReadDataResult(series, new BoundingBox(xmin, xmax, ymin, ymax));
        }
        catch (IOException ex){
            throw new ChartException("Cannot get data for chart", ex);
        }
    }
}
