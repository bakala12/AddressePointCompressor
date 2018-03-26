package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import compression.model.vrp.Client;
import compression.model.vrp.Vehicle;
import compression.model.vrp.VrpProblem;
import compression.model.vrp.VrpProblemMetric;

public class EuclideanMetricVrpProblemToJSpritConverter implements IVrpProblemToJSpritConverter {
    @Override
    public VehicleRoutingProblem convertToJsprit(VrpProblem problem) {
        if(problem.getProblemMetric() != VrpProblemMetric.Euclidean){
            throw new ProblemConversionException("Metrics must be Euclidean for that converter");
        }
        VehicleRoutingProblem.Builder problemBuilder = VehicleRoutingProblem.Builder.newInstance();

        for(Vehicle veh : problem.getVehicles()){
            VehicleTypeImpl.Builder vtb = VehicleTypeImpl.Builder.newInstance(veh.getId().toString());
            vtb.addCapacityDimension(0, veh.getCapacity());
            VehicleTypeImpl vti = vtb.build();
            VehicleImpl.Builder vb = VehicleImpl.Builder.newInstance(veh.getId().toString());
            vb.setType(vti);
            //there is no way to specify a depot -> vehicle start location is depot location
            vb.setStartLocation(Location.newInstance(problem.getDepot().getLocation().getLatitude(), problem.getDepot().getLocation().getLongitude()));
            VehicleImpl vimpl = vb.build();
            problemBuilder.addVehicle(vimpl);
        }

        for(Client cl : problem.getClients()){
            Service s = Service.Builder.newInstance(cl.getId().toString())
                    .setLocation(Location.newInstance(cl.getLocation().getLatitude(), cl.getLocation().getLongitude()))
                    .addSizeDimension(0, cl.getAmount().intValue()) //TODO:: INT VALUE
                    .setServiceTime(cl.getTime())
                    .build();
            problemBuilder.addJob(s);
        }
        return problemBuilder.build();
    }
}
