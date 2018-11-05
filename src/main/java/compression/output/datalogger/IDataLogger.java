package compression.output.datalogger;

/**
 * An interface that provides a way to log algorithm progress after each iteration.
 */
public interface IDataLogger {
    /**
     * Opens logger.
     */
    void openLogger();

    /**
     * Saves algorithm iteration progress.
     * @param problemName VRP problem name
     * @param iterationNumber Number of iteration
     * @param time Calculation time.
     * @param cost Current solution value.
     * @param numberOfVehicles Number of vehicles used.
     * @param bestKnownSolution Best known solution.
     */
    void saveData(String problemName, int iterationNumber, double time, double cost, int numberOfVehicles, double bestKnownSolution);
    /**
     * Closes logger.
     */
    void closeLogger();
}
