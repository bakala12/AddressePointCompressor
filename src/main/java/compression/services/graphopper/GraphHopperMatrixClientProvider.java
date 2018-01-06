package compression.services.graphopper;

import com.graphhopper.api.GraphHopperMatrixWeb;

public class GraphHopperMatrixClientProvider {
    private GraphHopperMatrixClientProvider(){
    }

    private static GraphHopperMatrixWeb client;
    private static final String KEY = "14ca9e60-99ea-43b8-8071-8752e12a1746";

    public static synchronized GraphHopperMatrixWeb getClient(){
        if(client == null){
            client = new GraphHopperMatrixWeb();
            client.setKey(KEY);
        }
        return client;
    }
}
