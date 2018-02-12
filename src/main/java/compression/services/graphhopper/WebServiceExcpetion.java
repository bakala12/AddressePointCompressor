package compression.services.graphhopper;

public class WebServiceExcpetion extends RuntimeException {
    public WebServiceExcpetion(String message, Exception ex) {
        super(message, ex);
    }

    public WebServiceExcpetion(String message){
        super(message);
    }
}
