package compression.output.plot;

public class ChartException extends RuntimeException {
    public ChartException(String message, Exception innerException){
        super(message, innerException);
    }

    public ChartException(String message){
        super(message, null);
    }
}
