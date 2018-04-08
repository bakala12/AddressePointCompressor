package compression.output.datalogger;

public interface IDataLogger {
    void openLogger();
    void saveData(String problemName, int iterationNumber, double cost, int numberOfVehicles);
    void closeLogger();
}
