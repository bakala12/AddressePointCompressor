package compression.output.plot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Defines method for drawing time and cost charts for the algorithms.
 */
public interface IChartPlotter {
    /**
     * Draws a chart of cost per iteration with default options.
     * @param sourceDataFilePath Data file source path.
     * @param outputFilePath Output file.
     */
    void plotCostChart(String sourceDataFilePath, String outputFilePath);
    /**
     * Draws a chart of time per iteration with default options.
     * @param sourceDataFilePath Data file source path.
     * @param outputFilePath Output file.
     */
    void plotTimeChart(String sourceDataFilePath, String outputFilePath);
    /**
     * Draws a chart of cost per iteration.
     * @param sourceDataFilePath Data file source path.
     * @param outputFilePath Output file.
     * @param options Chart options.
     */
    void plotCostChart(String sourceDataFilePath, String outputFilePath, ChartOptions options);
    /**
     * Draws a chart of time per iteration.
     * @param sourceDataFilePath Data file source path.
     * @param outputFilePath Output file.
     * @param options Chart options.
     */
    void plotTimeChart(String sourceDataFilePath, String outputFilePath, ChartOptions options);

    /**
     * A class that represents chart options.
     */
    @NoArgsConstructor
    class ChartOptions {
        @Getter @Setter
        private double width;
        @Getter @Setter
        private double height;
        @Getter @Setter
        private String title;
        @Getter @Setter
        private String xAxisLabel;
        @Getter @Setter
        private String yAxisLabel;
        @Getter @Setter
        private ScalingFactor xScalingFactor;
        @Getter @Setter
        private ScalingFactor yScalingFactor;
    }

    /**
     * Represents a scale for a chart drawing.
     */
    @AllArgsConstructor
    class ScalingFactor{
        @Getter
        private Double lowerScale;
        @Getter
        private Double upperScale;

        /**
         * Default instance of ScalingFactor.
         */
        public static final ScalingFactor DEFAULT = new ScalingFactor(1.,1.);
    }
}
