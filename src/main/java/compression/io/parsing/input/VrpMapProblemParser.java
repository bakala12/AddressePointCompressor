package compression.io.parsing.input;

import compression.io.parsing.BaseJsonParser;
import compression.model.vrp.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class VrpMapProblemParser extends BaseJsonParser<VrpProblem>
    implements IVrpProblemParser {

    @Override
    protected VrpProblem parseObject(Object object) {
        JSONObject obj = (JSONObject) object;
        JSONObject definitions = (JSONObject)obj.get("Definition");
        JSONArray locationArr = (JSONArray) definitions.get("locations");
        List<Client> clients = parseClients(locationArr);
        JSONArray vehicleArr = (JSONArray) definitions.get("vehicles");
        JSONObject installations = (JSONObject) obj.get("Instalations");
        List<Vehicle> vehicles = parseVehicles(vehicleArr);
        List<Depot> depots = parseDepots(installations, vehicles);
        return new VrpProblem(clients, vehicles,depots);
    }

    private List<Client> parseClients(JSONArray locations){
        List<Client> list = new LinkedList<>();
        for (Object obj : locations) {
            JSONObject location = (JSONObject) obj;
            double latitude = (double) location.get("x");
            double longitude = (double) location.get("y");
            double amount = (double) location.get("massEstimation");
            double time = (double) location.get("timeEstimation");
            long id = (long) location.get("idLocation");
            Client loc = new Client(id, amount, time, new Location(latitude, longitude));
            list.add(loc);
        }
        return list;
    }

    private List<Vehicle> parseVehicles(JSONArray vehicles){
        List<Vehicle> list = new LinkedList<>();
        for (Object obj : vehicles) {
            JSONObject vehicle = (JSONObject) obj;
            long startLocationId = (long)vehicle.get("startLocation");
            JSONObject endLocationObj = (JSONObject)vehicle.get("endBase");
            long endLocationId = (long) endLocationObj.get("idLocation");
            double capacity = (double)vehicle.get("totalCapacity");
            long id = (long)vehicle.get("idcar");
            Vehicle veh = new Vehicle(id, capacity, startLocationId, endLocationId);
            list.add(veh);
        }
        return list;
    }

    private List<Depot> parseDepots(JSONObject installation, List<Vehicle> vehicles){
        List<Depot> depots = new LinkedList<>();
        List<Long> depotIds = new LinkedList<>();
        for(Vehicle v : vehicles){
            if(!depotIds.contains(v.getStartDepotId())){
                depotIds.add(v.getStartDepotId());
            }
            if(!depotIds.contains(v.getEndDepotId())) {
                depotIds.add(v.getEndDepotId());
            }
        }
        for(Long id : depotIds){
            JSONObject locObj = (JSONObject) installation.get(id.toString());
            double latitude = (double) locObj.get("x");
            double longitude = (double) locObj.get("y");
            depots.add(new Depot(id,  new Location(latitude, longitude)));
        }
        return depots;
    }
}
