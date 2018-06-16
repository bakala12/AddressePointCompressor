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

public class ExplicitMetricVrpProblemToJSpritConverter
        extends BaseProblemToJSpritConverter
        implements IVrpProblemToJSpritConverter {
    @Override
    public ConversionResult convertToJsprit(VrpProblem problem) {
        if(problem.getProblemMetric() != VrpProblemMetric.Explicit){
            throw new ProblemConversionException("Metric must be explicit for this converter");
        }
        VehicleRoutingProblem.Builder problemBuilder = VehicleRoutingProblem.Builder.newInstance();
        addVehicles(problemBuilder, problem, Location.newInstance(problem.getDepot().getId().toString()));
        addClients(problemBuilder, problem);
        VehicleRoutingTransportCostsMatrix.Builder matrixCostBuilder = VehicleRoutingTransportCostsMatrix.Builder.newInstance(false);
        copyDistanceMatrix(problem, matrixCostBuilder);
        problemBuilder.setRoutingCost(matrixCostBuilder.build());
        return new ConversionResult(problemBuilder.build(), null);
    }

    @Override
    protected Location convertLocation(compression.model.vrp.Client client){
        return Location.newInstance(client.getId().toString());
    }

    private void copyDistanceMatrix(VrpProblem problem, VehicleRoutingTransportCostsMatrix.Builder matirxCostBuilder){
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
}
