package compression.output.datalogger;

public interface IDataLogger {
    void openLogger();
    void saveData(String problemName, int iterationNumber, double time, double cost, int numberOfVehicles, double bestKnownSolution);
    void closeLogger();
}
