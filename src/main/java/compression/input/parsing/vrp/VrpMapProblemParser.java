package compression.input.parsing.vrp;

import compression.input.parsing.BaseJsonParser;
import compression.input.parsing.ParsingException;
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
        Depot depot = parseDepot(vehicleArr, installations);
        return new VrpProblem("mapProblem", 0.0, clients.size()+1, VrpProblemMetric.GraphHopper, clients, vehicles,depot, null);
    }

    private List<Client> parseClients(JSONArray locations){
        List<Client> list = new LinkedList<>();
        Long clientId = 1L;
        for (Object obj : locations) {
            JSONObject location = (JSONObject) obj;
            double latitude = (double) location.get("x");
            double longitude = (double) location.get("y");
            double amount = (double) location.get("massEstimation");
            double time = (double) location.get("timeEstimation");
            Location clientLocation = new Location(latitude, longitude);
            Client loc = new Client(clientId, amount, time, clientLocation);
            list.add(loc);
        }
        return list;
    }

    private List<Vehicle> parseVehicles(JSONArray vehicles){
        List<Vehicle> list = new LinkedList<>();
        for (Object obj : vehicles) {
            JSONObject vehicle = (JSONObject) obj;
            double capacity = (double)vehicle.get("totalCapacity");
            long id = (long)vehicle.get("idcar");
            Vehicle veh = new Vehicle(id, Double.valueOf(capacity).intValue());
            list.add(veh);
        }
        return list;
    }

    private Depot parseDepot(JSONArray vehicles, JSONObject installation){
        List<Long> depotIds = new LinkedList<>();
        for(Object obj : vehicles){
            JSONObject vobj = (JSONObject)obj;
            Long startLocId = (Long)vobj.get("startLocation");
            JSONObject endLocationObj = (JSONObject)vobj.get("endBase");
            Long endLocId = (Long) endLocationObj.get("idLocation");
            if(!depotIds.contains(startLocId)){
                depotIds.add(startLocId);
            }
            if(!depotIds.contains(endLocId)) {
                depotIds.add(endLocId);
            }
        }
        if(depotIds.size()!=1){
            throw new ParsingException("More than one depot is not supported");
        }
        Long locId = depotIds.get(0);
        JSONObject locationObj = (JSONObject)installation.get(locId.toString());
        Double x = (Double)locationObj.get("x");
        Double y = (Double)locationObj.get("y");
        Location depotLocation = new Location(x,y);
        return new Depot(1L, depotLocation);
    }


}
