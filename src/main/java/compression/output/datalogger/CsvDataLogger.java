package compression.output.datalogger;

import lombok.RequiredArgsConstructor;

import java.io.*;

/**
 * Algorithm progress logger that stores information in csv file.
 */
@RequiredArgsConstructor
public class CsvDataLogger implements IDataLogger {

    private final String csvPath;
    private PrintWriter writer;

    /**
     * Opens csv log file.
     */
    @Override
    public void openLogger() {
        try{
            if(writer != null)
                closeLogger();
            this.writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(csvPath)));
        }
        catch (IOException e){
            this.writer = null;
            throw new DataLoggerException("Failed to open log file", e);
        }
    }

    /**
     * Saves algorithm iteration progress to the csv file.
     * @param problemName VRP problem name
     * @param iterationNumber Number of iteration
     * @param time Calculation time.
     * @param cost Current solution value.
     * @param numberOfVehicles Number of vehicles used.
     * @param bestKnownSolution Best known solution.
     */
    @Override
    public void saveData(String problemName, int iterationNumber, double time, double cost, int numberOfVehicles, double bestKnownSolution) {
        if(writer == null)
            throw new DataLoggerException("Logger is not open to write");
        String line = problemName +"," + iterationNumber+","+time+"," + cost +","+ numberOfVehicles +","+ bestKnownSolution;
        writer.println(line);
    }

    /**
     * Closes csv log file.
     */
    @Override
    public void closeLogger() {
        if(writer == null)
            return;
        writer.close();
        writer = null;
    }
}
