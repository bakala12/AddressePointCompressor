package compression.io.parsing;

public class ParsingException extends RuntimeException {
    public ParsingException(String message){
        super(message);
    }

    public ParsingException(String message, Exception e){
        super(message, e);
    }
}
