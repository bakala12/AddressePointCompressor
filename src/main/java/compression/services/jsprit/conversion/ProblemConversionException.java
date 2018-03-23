package compression.services.jsprit.conversion;

public class ProblemConversionException extends RuntimeException {
    public ProblemConversionException(String message){
        super(message);
    }

    public ProblemConversionException(String message, Exception ex){
        super(message, ex);
    }
}
