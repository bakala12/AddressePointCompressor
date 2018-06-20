package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.util.VehicleRoutingTransportCostsMatrix;
import compression.model.vrp.*;
import compression.services.distance.IDistanceService;

public abstract class BaseProblemToJSpritConverter {

    private final IDistanceService distanceService;

    protected BaseProblemToJSpritConverter(IDistanceService distanceService){
        this.distanceService = distanceService;
    }

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

    protected DistanceMatrix createDistanceMatrix(VrpProblem problem){
        int dimensions = problem.getDimensions();
        DistanceMatrix matrix = new DistanceMatrix(dimensions);
        for(Client c: problem.getClients()){
            for(Client c1 : problem.getClients()){
                if(c.getId() != c1.getId()){
                    matrix.setDistance(c.getId(), c1.getId(), distanceService.getEuclideanDistance(c.getLocation(), c1.getLocation()));
                }
            }
            matrix.setDistance(c.getId(), problem.getDepot().getId(), distanceService.getEuclideanDistance(c.getLocation(), problem.getDepot().getLocation()));
            matrix.setDistance(problem.getDepot().getId(), c.getId(), distanceService.getEuclideanDistance(problem.getDepot().getLocation(), c.getLocation()));
        }
        return matrix;
    }

    protected void copyDistanceMatrix(VrpProblem problem, VehicleRoutingTransportCostsMatrix.Builder matirxCostBuilder){
        DistanceMatrix matrix = problem.getDistanceMatrix();
        if(matrix == null)
            throw new ProblemConversionException("Distance matrix is obligatory for this converter");
        for(Client from : problem.getClients()){
            for(Client to : problem.getClients()){
                double distance = 0.0;
                Double d = matrix.getDistance(from.getId(), to.getId());
                if(d != null){
                    distance = d;
                }
                matirxCostBuilder.addTransportDistance(from.getId().toString(), to.getId().toString(), distance);
            }
            double distance = 0.0;
            Double d = matrix.getDistance(from.getId(), problem.getDepot().getId());
            if(d != null){
                distance = d;
            }
            matirxCostBuilder.addTransportDistance(from.getId().toString(), problem.getDepot().getId().toString(), distance);
            double distance1 = 0.0;
            Double d1 = matrix.getDistance(problem.getDepot().getId(), from.getId());
            if(d1 != null){
                distance1 = d1;
            }
            matirxCostBuilder.addTransportDistance(problem.getDepot().getId().toString(), from.getId().toString(), distance1);
        }
    }

    protected abstract Location convertLocation(compression.model.vrp.Client client);
}
