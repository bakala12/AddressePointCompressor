package compression.parsing.web;

import compression.model.vrp.Location;
import compression.model.vrp.Route;
import compression.parsing.BaseParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class GraphHopperResponseParser extends BaseParser<Route>
    implements IGraphHopperResponseParser {

    @Override
    protected Route parseObject(Object object) {
        JSONObject obj = (JSONObject)object;
        JSONObject paths = (JSONObject)((JSONArray)obj.get("paths")).get(0);
        List<Location> routeLocations = parseRouteLocations(paths);
        Location from = routeLocations.get(0);
        Location to = routeLocations.get(routeLocations.size()-1);
        double distance = (double)paths.get("distance");
        double time = (Long)paths.get("time");
        return new Route(from, to, routeLocations, distance, time);
    }

    private Location parseLocation(JSONArray array){
        double longitude = (double)array.get(0);
        double latitude = (double)array.get(1);
        return new Location(latitude, longitude);
    }

    private List<Location> parseRouteLocations (JSONObject paths){
        JSONObject points = (JSONObject)paths.get("points");
        JSONArray array = (JSONArray)points.get("coordinates");
        List<Location> locations = new LinkedList<>();
        for(Object loc : array){
            JSONArray locArr = (JSONArray) loc;
            locations.add(parseLocation(locArr));
        }
        return locations;
    }
}
