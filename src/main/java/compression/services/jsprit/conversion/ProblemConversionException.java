package compression.services.jsprit.conversion;

/**
 * Thrown when problem with conversion occurred.
 */
public class ProblemConversionException extends RuntimeException {
    /**
     * Initializes a new instance of ProblemConversionException.
     * @param message Exception message.
     */
    public ProblemConversionException(String message){
        super(message);
    }
}
