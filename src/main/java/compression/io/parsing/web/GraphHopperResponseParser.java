package compression.io.parsing.web;

import compression.io.parsing.BaseJsonParser;
import compression.model.graphhopper.Instruction;
import compression.model.vrp.Location;
import compression.model.graphhopper.Route;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class GraphHopperResponseParser extends BaseJsonParser<Route>
    implements IGraphHopperResponseParser{

    @Override
    protected Route parseObject(Object object) {
        JSONObject obj = (JSONObject)object;
        JSONObject paths = (JSONObject)((JSONArray)obj.get("paths")).get(0);
        List<Location> routeLocations = parseRouteLocations(paths);
        Location from = routeLocations.get(0);
        Location to = routeLocations.get(routeLocations.size()-1);
        double distance = (double)paths.get("distance");
        double time = (Long)paths.get("time");
        List<Instruction> instructions = parseInstructions(paths);
        return new Route(from, to, distance, time, instructions.size(), instructions);
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

    private Instruction parseInstruction(JSONObject instObj){
        double distance = 0.0;
        try{
            distance = (Double) instObj.get("distance");
        } catch (ClassCastException e){
            distance = (Long) instObj.get("distance");
        }
        double time = (Long)instObj.get("time");
        Long sign = (Long)instObj.get("sign");
        JSONArray intervalArr = (JSONArray)instObj.get("interval");
        Long[] interval = new Long[2];
        interval[0] = (Long)intervalArr.get(0);
        interval[1] = (Long)intervalArr.get(1);
        return new Instruction(distance, sign, interval, time);
    }

    private List<Instruction> parseInstructions(JSONObject path){
        List<Instruction> instructions = new LinkedList<>();
        JSONArray instrArr = (JSONArray)path.get("instructions");
        for(Object obj : instrArr){
            JSONObject instr = (JSONObject)obj;
            instructions.add(parseInstruction(instr));
        }
        return instructions;
    }
}
