package compression.output.datalogger;

/**
 * An exception which is thrown when problem with logging algorithm iteration problem occurs.
 */
public class DataLoggerException extends RuntimeException {
    /**
     * Initializes a new instance of DataLoggerException.
     * @param message Exception message.
     * @param innerException Reason for this exception.
     */
    public DataLoggerException(String message, Exception innerException){
        super(message, innerException);
    }

    /**
     * Initializes a new instance of DataLoggerException.
     * @param message Exception message.
     */
    public DataLoggerException(String message){
        this(message, null);
    }
}
