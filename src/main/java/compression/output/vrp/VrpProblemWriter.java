package compression.output.vrp;

import compression.model.vrp.Client;
import compression.model.vrp.Vehicle;
import compression.model.vrp.VrpProblem;
import compression.model.vrp.VrpProblemMetric;

import java.util.List;

public class VrpProblemWriter {
    public void writeProblem(VrpProblem problem){
        System.out.println("NAME: "+problem.getProblemName());
        System.out.println("BEST KNOWN SOLUTION: "+problem.getBestKnownSolution());
        System.out.println("DIMENSIONS: "+problem.getDimensions());
        List<Client> clients = problem.getClients();
        System.out.println("CLIENTS: "+clients.size());
        for(Client cl:clients){
            System.out.println(cl);
        }
        List<Vehicle> vehicles = problem.getVehicles();
        System.out.println("VEHICLES: ");
        for(Vehicle veh : vehicles){
            System.out.println(veh);
        }
        System.out.println("DEPOT: "+ problem.getDepot());
        if(problem.getProblemMetric() == VrpProblemMetric.Explicit){
            System.out.println("DISTANCE MATRIX: ");
            for(Long from = 1L; from < problem.getDistanceMatrix().getDimensions(); from++){
                for(Long to = 1L; to < from; to++){
                    Double dist = problem.getDistanceMatrix().getDistance(from, to);
                    System.out.print(from+" -> "+to+":"+dist+" ");
                    System.out.print("\t");
                }
                System.out.println();
            }
        }
    }
}
