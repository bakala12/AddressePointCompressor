package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import compression.model.vrp.Client;
import compression.model.vrp.Vehicle;
import compression.model.vrp.VrpProblem;

public abstract class BaseProblemToJSpritConverter {

    protected void addVehicles(VehicleRoutingProblem.Builder problemBuilder, VrpProblem problem, Location depotLocation){
        for(Vehicle veh : problem.getVehicles()){
            VehicleTypeImpl.Builder vtb = VehicleTypeImpl.Builder.newInstance(veh.getId().toString());
            vtb.addCapacityDimension(0, veh.getCapacity());
            VehicleTypeImpl vti = vtb.build();
            VehicleImpl.Builder vb = VehicleImpl.Builder.newInstance(veh.getId().toString());
            vb.setType(vti);
            //there is no way to specify a depot -> vehicle start location is depot location
            vb.setStartLocation(depotLocation);
            VehicleImpl vImpl = vb.build();
            problemBuilder.addVehicle(vImpl);
        }
    }

    protected void addClients(VehicleRoutingProblem.Builder problemBuilder, VrpProblem problem){
        for(Client cl : problem.getClients()){
            Service s = Service.Builder.newInstance(Long.toString(cl.getId()-1))
                    .setLocation(convertLocation(cl))
                    .addSizeDimension(0, cl.getAmount().intValue())
                    .setServiceTime(cl.getTime())
                    .build();
            problemBuilder.addJob(s);
        }
    }

    protected abstract Location convertLocation(compression.model.vrp.Client client);
}
