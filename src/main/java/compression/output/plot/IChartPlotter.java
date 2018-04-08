package compression.output.plot;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public interface IChartPlotter {
    void plotCostChart(String sourceDataFilePath, String outputFilePath);
    void plotTimeChart(String sourceDataFilePath, String outputFilePath);
    void plotCostChart(String sourceDataFilePath, String outputFilePath, ChartOptions options);
    void plotTimeChart(String sourceDataFilePath, String outputFilePath, ChartOptions options);

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

    @AllArgsConstructor
    class ScalingFactor{
        @Getter
        private Double lowerScale;
        @Getter
        private Double upperScale;

        public static final ScalingFactor DEFAULT = new ScalingFactor(1.,1.);
    }
}
