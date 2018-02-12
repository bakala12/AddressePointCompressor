package compression.services.graphhopper;

import compression.model.vrp.Location;
import compression.model.vrp.Route;
import compression.model.web.WebResponse;
import compression.parsing.IParser;
import javafx.util.Pair;
import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.json.simple.parser.ParseException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class GraphHopperService extends BaseService
    implements IGraphHopperService {

    private final IParser<Route> parser;

    public GraphHopperService(IParser<Route> parser){
        super("http://194.29.178.216:8989/route");
        this.parser = parser;
    }

    public void getRoute(Location from, Location to){
        List<Pair<String, String>> parameters = new LinkedList<>();

        parameters.add(new Pair<>("point", from.toString()));
        parameters.add(new Pair<>("point", to.toString()));
        parameters.add(new Pair<>("locale", "pl-PL"));
        parameters.add(new Pair<>("instructions", "true"));
        parameters.add(new Pair<>("vehicle", "car"));
        parameters.add(new Pair<>("weighting", "fastest"));
        parameters.add(new Pair<>("elevation", "false"));
        parameters.add(new Pair<>("points_encoded", "false"));
        parameters.add(new Pair<>("use_miles", "false"));
        parameters.add(new Pair<>("layer", "Omniscale"));

        WebResponse response = sendRequest(parameters);

        if(response.getStatusCode() != 200){
            throw new WebServiceExcpetion("Invalid response code");
        }

        parser.parse(response.getResponseString());
    }


}
