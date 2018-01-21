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
            throw new LoadFileException(ex.getMessage());
        }
    }

    private VrpProblem parse(InputStream stream) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject obj = (JSONObject)parser.parse(new InputStreamReader(stream));
        JSONObject definitions = (JSONObject)obj.get("Definitions");
        JSONArray locationArr = (JSONArray) definitions.get("locations");
        List<Client> clients = parseLocations(locationArr);
        JSONArray vehicleArr = (JSONArray) definitions.get("vehicles");
        JSONObject installations = (JSONObject) definitions.get("Instalations");
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
            String name = (String) location.get("idLocation");
            Client loc = new Client(name, latitude, longitude, amount, time);
            list.add(loc);
        }
        return list;
    }

    private List<Vehicle> parseVehicles(JSONArray vehicles, JSONObject installations){
        List<Vehicle> list = new LinkedList<>();
        for (Object obj : vehicles) {
            JSONObject vehicle = (JSONObject) obj;
            String startLocationId = (String)vehicle.get("startLocation");
            JSONObject endLocationObj = (JSONObject)vehicle.get("endBase");
            String endLocationId = (String) endLocationObj.get("idLocation");
            Location startLocation = parseVehicleLocation(startLocationId, installations);
            Location endLocation = parseVehicleLocation(endLocationId, installations);
            double capacity = (double)vehicle.get("totalCapacity");
            String name = (String)vehicle.get("idCar");
            Vehicle veh = new Vehicle(name, capacity, startLocation, endLocation);
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
