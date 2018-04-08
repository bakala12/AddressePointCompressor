package compression.output.datalogger;

import lombok.RequiredArgsConstructor;

import java.io.*;

@RequiredArgsConstructor
public class CsvDataLogger implements IDataLogger {

    private final String csvPath;
    private PrintWriter writer;

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

    @Override
    public void saveData(String problemName, int iterationNumber, double time, double cost, int numberOfVehicles) {
        if(writer == null)
            throw new DataLoggerException("Logger is not open to write");
        String line = problemName +"," + iterationNumber+","+time+"," + cost +","+ numberOfVehicles;
        writer.println(line);
    }

    @Override
    public void closeLogger() {
        if(writer == null)
            return;
        writer.close();
        writer = null;
    }
}
