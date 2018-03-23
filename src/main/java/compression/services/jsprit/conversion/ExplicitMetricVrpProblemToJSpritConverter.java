package compression.services.jsprit.conversion;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.cost.VehicleRoutingTransportCosts;
import com.graphhopper.jsprit.core.problem.driver.Driver;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.util.VehicleRoutingTransportCostsMatrix;
import compression.model.vrp.*;

import javax.sound.midi.Soundbank;

public class ExplicitMetricVrpProblemToJSpritConverter implements IVrpProblemToJSpritConverter {
    @Override
    public VehicleRoutingProblem convertToJsprit(VrpProblem problem) {
        if(problem.getProblemMetric() != VrpProblemMetric.Explicit){
            throw new ProblemConversionException("Metric must be explicit for this converter");
        }
        VehicleRoutingProblem.Builder problemBuilder = VehicleRoutingProblem.Builder.newInstance();

        for(Vehicle veh : problem.getVehicles()){
            VehicleTypeImpl.Builder vtb = VehicleTypeImpl.Builder.newInstance(veh.getId().toString());
            vtb.addCapacityDimension(0, veh.getCapacity());
            VehicleTypeImpl vti = vtb.build();
            VehicleImpl.Builder vb = VehicleImpl.Builder.newInstance(veh.getId().toString());
            vb.setType(vti);
            vb.setStartLocation(Location.newInstance(problem.getDepot().getId().toString()));
            VehicleImpl vimpl = vb.build();
            problemBuilder.addVehicle(vimpl);
        }

        for(Client cl : problem.getClients()){
            Service s = Service.Builder.newInstance(cl.getId().toString())
                    .setLocation(Location.newInstance(cl.getId().toString()))
                    .addSizeDimension(0, cl.getAmount().intValue()) //TODO:: INT VALUE
                    .setServiceTime(cl.getTime())
                    .build();
            problemBuilder.addJob(s);
        }

        VehicleRoutingTransportCostsMatrix.Builder matrixCostBuilder = VehicleRoutingTransportCostsMatrix.Builder.newInstance(true);
        copyDistanceMatrix(problem, matrixCostBuilder);
        problemBuilder.setRoutingCost(matrixCostBuilder.build());
        return problemBuilder.build();
    }

    private void copyDistanceMatrix(VrpProblem problem, VehicleRoutingTransportCostsMatrix.Builder matirxCostBuilder){
        SymmetricalDistanceMatrix matrix = problem.getDistanceMatrix();
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
        }
    }
}
