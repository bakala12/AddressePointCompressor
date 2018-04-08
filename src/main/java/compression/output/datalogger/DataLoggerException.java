package compression.output.datalogger;

public class DataLoggerException extends RuntimeException {
    public DataLoggerException(String message, Exception innerException){
        super(message, innerException);
    }

    public DataLoggerException(String message){
        this(message, null);
    }
}
