package compression;

import compression.helpers.ResourceHelper;
import compression.io.IFileReader;
import compression.model.AddressPoint;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CompressionApplication {
    private final IFileReader<AddressPoint> fileReader;

    public void run(){
        List<AddressPoint> points = ResourceHelper.readResource(fileReader, "/selected.csv");
        if(points == null) return;
        System.out.println("Loaded "+points.size()+" address points");
    }
}
