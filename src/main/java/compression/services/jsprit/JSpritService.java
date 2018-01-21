/*
package compression.services.jsprit;

import com.graphhopper.directions.api.client.model.Solution;
import com.graphhopper.jsprit.core.algorithm.VehicleRoutingAlgorithm;
import com.graphhopper.jsprit.core.algorithm.box.Jsprit;
import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.VehicleRoutingProblem;
import com.graphhopper.jsprit.core.problem.cost.VehicleRoutingTransportCosts;
import com.graphhopper.jsprit.core.problem.job.Service;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.vehicle.Vehicle;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleImpl;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleType;
import com.graphhopper.jsprit.core.problem.vehicle.VehicleTypeImpl;
import com.graphhopper.jsprit.core.util.Solutions;
import com.graphhopper.jsprit.core.util.VehicleRoutingTransportCostsMatrix;
import compression.model.AddressPoint;
import compression.model.VRPProblem;
import compression.model.VRPResult;
import compression.services.aggregation.IVRPProblemAggregator;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class JSpritService implements IJSpritService {
    private final IVRPProblemAggregator problemAggregator;

    public VRPResult solveProblem(VRPProblem problem){
        VRPProblem aggregatedProblem = problemAggregator.aggregate(problem);
        VehicleRoutingProblem.Builder builder = VehicleRoutingProblem.Builder.newInstance();

        createVehicleFleet(builder);
        createClients(builder, aggregatedProblem);
        setRoutingCost(builder, aggregatedProblem);

        VehicleRoutingProblem vrpProblem = builder.build();

        VehicleRoutingAlgorithm algorithm = Jsprit.createAlgorithm(vrpProblem);

        Collection<VehicleRoutingProblemSolution> solutions = algorithm.searchSolutions();
        VehicleRoutingProblemSolution bestSolution = Solutions.bestOf(solutions);

        return new VRPResult(vrpProblem, bestSolution);
    }

    private void setRoutingCost(VehicleRoutingProblem.Builder builder, VRPProblem problem){
        //VehicleRoutingTransportCosts matrix = JSpritProblemBuilder.getDistancesMatrix(problem);
        //builder.setRoutingCost(matrix);
    }

    private void createVehicleFleet(VehicleRoutingProblem.Builder builder){
        VehicleTypeImpl.Builder vehicleTypeBuilder = VehicleTypeImpl.Builder.newInstance("vehicleType");
        VehicleType vehicleType = vehicleTypeBuilder.addCapacityDimension(0, 10).build();

        VehicleImpl.Builder vehicleBuilder = VehicleImpl.Builder.newInstance("vehicle");
        Vehicle vehicle = vehicleBuilder.setType(vehicleType)
                                        .setStartLocation(Location.newInstance(0))
                                        .setEndLocation(Location.newInstance(0))
                                        .build();

        builder.addVehicle(vehicle);
    }

    private void createClients(VehicleRoutingProblem.Builder builder, VRPProblem problem){
        int i=0;
        for(AddressPoint addressPoint : problem.getAddressPoints()){
            Service service = Service.Builder.newInstance(addressPoint.getId())
                    .setLocation(Location.newInstance(i))
                    .addSizeDimension(0,1)
                    .build();
            builder.addJob(service);
            i++;
        }
    }
}*/
