package compression;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CompressionApplication {

    public void run(){
        //List<AddressPoint> points = ResourceHelper.readResource(fileReader, "/selected.csv");
        //if(points == null) return;
        //System.out.println("Loaded "+points.size()+" address points");

        //IGraphHopperService ghService = new GraphHopperService();
        //DistanceMatrix distances = ghService.getDistanceMatrix(points);

        //System.out.println("Example distances:");
        //System.out.println("From: "+ points.get(0).getName()+" to: "+ points.get(1).getName()+" distance is: "+distances.getDistance(points.get(0), points.get(1)));

        //IJSpritService jSpritService = new JSpritService(new NoAggregationVRPProblemAggregator());
        //VRPProblem problem = new VRPProblem(points, distances);

        //VRPResult result = jSpritService.solveProblem(problem);

        //System.out.println("Problem solution:");
        //JSpritSolutionHelper.printSolution(result);
    }
}
