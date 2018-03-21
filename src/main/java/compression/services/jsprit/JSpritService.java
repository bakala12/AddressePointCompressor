package compression.services.jsprit;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import compression.model.vrp.Client;
import compression.model.vrp.Vehicle;
import compression.model.vrp.VrpProblem;

public class JSpritService implements IJSpritService {
    public void solve(VrpProblem problem){
        VehicleRoutingProblem.Builder problemBuilder = VehicleRoutingProblem.Builder.newInstance();
        //vehicle
        for(Vehicle veh : problem.getVehicles()){
            VehicleTypeImpl.Builder vtb = VehicleTypeImpl.Builder.newInstance(veh.getId().toString());
            vtb.addCapacityDimension(0, veh.getCapacity());
            VehicleTypeImpl vti = vtb.build();
            VehicleImpl.Builder vb = VehicleImpl.Builder.newInstance(veh.getId().toString());
            vb.setType(vti);
            VehicleImpl vimpl = vb.build();
            //TODO::start location, end location
            problemBuilder.addVehicle(vimpl);
        }
        //clients
        for(Client cl : problem.getClients()){
            Service s = Service.Builder.newInstance(cl.getId().toString())
                    .setLocation(Location.newInstance(cl.getLocation().getLatitude(), cl.getLocation().getLongitude()))
                    .addSizeDimension(0, cl.getAmount().intValue()) //TODO:: INT VALUE
                    .setServiceTime(cl.getTime())
                    .build();
            problemBuilder.addJob(s);
        }
        //depot
        
    }
}
