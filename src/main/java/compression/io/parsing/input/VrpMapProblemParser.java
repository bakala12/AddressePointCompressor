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
        List<Location> locations = new LinkedList<>();
        List<Client> clients = parseClients(locationArr, locations);
        JSONArray vehicleArr = (JSONArray) definitions.get("vehicles");
        JSONObject installations = (JSONObject) obj.get("Instalations");
        List<Vehicle> vehicles = parseVehicles(vehicleArr);
        List<Depot> depots = parseDepots(vehicles);
        return new VrpProblem("mapProblem", 0.0, VrpProblemMetric.GraphHopper, clients, vehicles,depots, null);
    }

    private List<Client> parseClients(JSONArray locations, List<Location> locationList){
        List<Client> list = new LinkedList<>();
        Long clientId = 1l;
        for (Object obj : locations) {
            JSONObject location = (JSONObject) obj;
            double latitude = (double) location.get("x");
            double longitude = (double) location.get("y");
            double amount = (double) location.get("massEstimation");
            double time = (double) location.get("timeEstimation");
            long id = (long) location.get("idLocation");
            locationList.add(new Location(latitude, longitude));
            Client loc = new Client(clientId, amount, time, id);
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

    private List<Depot> parseDepots(List<Vehicle> vehicles){
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
        Long depotId = 1l;
        for(Long id : depotIds){
            depots.add(new Depot(depotId, id));
        }
        return depots;
    }
}
