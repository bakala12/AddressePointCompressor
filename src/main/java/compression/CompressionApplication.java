package compression;

import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import compression.helpers.JSpritSolutionHelper;
import compression.helpers.ResourceHelper;
import compression.io.IFileReader;
import compression.model.AddressPoint;
import compression.model.DistanceMatrix;
import compression.model.VRPProblem;
import compression.model.VRPResult;
import compression.services.aggregation.NoAggregationVRPProblemAggregator;
import compression.services.graphopper.GraphHopperService;
import compression.services.graphopper.IGraphHopperService;
import compression.services.jsprit.IJSpritService;
import compression.services.jsprit.JSpritService;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CompressionApplication {
    private final IFileReader<AddressPoint> fileReader;

    public void run(){
        List<AddressPoint> points = ResourceHelper.readResource(fileReader, "/selected.csv");
        if(points == null) return;
        System.out.println("Loaded "+points.size()+" address points");

        IGraphHopperService ghService = new GraphHopperService();
        DistanceMatrix distances = ghService.getDistanceMatrix(points);

        System.out.println("Example distances:");
        System.out.println("From: "+ points.get(0).getName()+" to: "+ points.get(1).getName()+" distance is: "+distances.getDistance(points.get(0), points.get(1)));

        IJSpritService jSpritService = new JSpritService(new NoAggregationVRPProblemAggregator());
        VRPProblem problem = new VRPProblem(points, distances);

        VRPResult result = jSpritService.solveProblem(problem);

        System.out.println("Problem solution:");
        JSpritSolutionHelper.printSolution(result);
    }
}
