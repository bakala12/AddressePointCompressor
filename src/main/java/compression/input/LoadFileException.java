package compression.input;

/**
 * Exception that is thrown when file loading problem occurs.
 */
public class LoadFileException extends RuntimeException {
    /**
     * Initializes a new instance of exception.
     * @param message Exception message.
     * @param ex Inner exception.
     */
    public LoadFileException(String message, Exception ex){
        super(message, ex);
    }
}
