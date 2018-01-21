package compression.io;

import compression.model.vrp.Client;
import compression.model.vrp.Location;
import compression.model.vrp.Vehicle;
import compression.model.vrp.VrpProblem;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class VrpProblemReader implements IProblemReader{

    @Override
    public VrpProblem readProblemInstance(String resourceName) {
        try(InputStream stream = VrpProblem.class.getResourceAsStream(resourceName)){
            return parse(stream);
        }catch (Exception ex) {
            throw new LoadFileException(ex.getMessage(), ex);
        }
    }

    private VrpProblem parse(InputStream stream) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject)parser.parse(new InputStreamReader(stream));
        JSONObject definitions = (JSONObject)obj.get("Definition");
        JSONArray locationArr = (JSONArray) definitions.get("locations");
        List<Client> clients = parseLocations(locationArr);
        JSONArray vehicleArr = (JSONArray) definitions.get("vehicles");
        JSONObject installations = (JSONObject) obj.get("Instalations");
        List<Vehicle> vehicles = parseVehicles(vehicleArr, installations);
        return new VrpProblem(clients, vehicles);
    }

    private List<Client> parseLocations(JSONArray locations){
        List<Client> list = new LinkedList<>();
        for (Object obj : locations) {
            JSONObject location = (JSONObject) obj;
            double latitude = (double) location.get("x");
            double longitude = (double) location.get("y");
            double amount = (double) location.get("massEstimation");
            double time = (double) location.get("timeEstimation");
            long id = (long) location.get("idLocation");
            Client loc = new Client(id, latitude, longitude, amount, time);
            list.add(loc);
        }
        return list;
    }

    private List<Vehicle> parseVehicles(JSONArray vehicles, JSONObject installations){
        List<Vehicle> list = new LinkedList<>();
        for (Object obj : vehicles) {
            JSONObject vehicle = (JSONObject) obj;
            long startLocationId = (long)vehicle.get("startLocation");
            JSONObject endLocationObj = (JSONObject)vehicle.get("endBase");
            long endLocationId = (long) endLocationObj.get("idLocation");
            Location startLocation = parseVehicleLocation(Long.toString(startLocationId), installations);
            Location endLocation = parseVehicleLocation(Long.toString(endLocationId), installations);
            double capacity = (double)vehicle.get("totalCapacity");
            long id = (long)vehicle.get("idcar");
            Vehicle veh = new Vehicle(id, capacity, startLocation, endLocation);
            list.add(veh);
        }
        return list;
    }

    private Location parseVehicleLocation(String locationId, JSONObject installation){
        JSONObject locObj = (JSONObject) installation.get(locationId);
        double latitude = (double) locObj.get("x");
        double longitude = (double) locObj.get("y");
        return new Location(latitude, longitude);
    }
}
