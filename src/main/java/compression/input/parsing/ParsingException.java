package compression.input.parsing;

/**
 * An exception that is thrown when parsing error occurs.
 */
public class ParsingException extends RuntimeException {
    /**
     * Initializes a new instance of Parsing exception.
     * @param message Exception message.
     */
    public ParsingException(String message){
        super(message);
    }

    /**
     * Initializes a new instance of Parsing exception.
     * @param message Exception message.
     * @param e Inner exception.
     */
    public ParsingException(String message, Exception e){
        super(message, e);
    }
}
