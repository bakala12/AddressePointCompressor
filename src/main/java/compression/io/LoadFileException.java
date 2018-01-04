package compression.io;

public class LoadFileException extends Exception {
    public LoadFileException(String message, Exception ex){
        super(message, ex);
    }

    public LoadFileException(String message){
        this(message, null);
    }
}
