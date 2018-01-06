package compression;

import compression.helpers.ResourceHelper;
import compression.io.IFileReader;
import compression.model.AddressPoint;
import compression.model.DistanceMatrix;
import compression.services.graphopper.GraphHopperService;
import compression.services.graphopper.IGraphHopperService;
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

    }
}
