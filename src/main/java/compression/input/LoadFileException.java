package compression.input;

public class LoadFileException extends RuntimeException {
    public LoadFileException(String message, Exception ex){
        super(message, ex);
    }

    public LoadFileException(String message){
        this(message, null);
    }
}
