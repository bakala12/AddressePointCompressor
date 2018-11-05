package compression.output.plot;

/**
 * An exception which is thrown when chart generation failed.
 */
public class ChartException extends RuntimeException {
    /**
     * Initializes a new instance of ChartException.
     * @param message Exception message.
     * @param innerException Reason for this exception.
     */
    public ChartException(String message, Exception innerException){
        super(message, innerException);
    }

    /**
     * Initializes a new instance of ChartException.
     * @param message Exception message.
     */
    public ChartException(String message){
        super(message, null);
    }
}
