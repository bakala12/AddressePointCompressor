package compression.io;

import compression.model.AddressPoint;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class AddressFileReader implements IFileReader<AddressPoint> {

    @Override
    public List<AddressPoint> readFile(InputStream stream) throws LoadFileException {
        try{
            List<AddressPoint> addresses = new LinkedList<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            String line;
            reader.readLine();
            while((line = reader.readLine()) != null){
                String[] split = line.split(",");
                addresses.add(new AddressPoint(split[3], Double.parseDouble(split[1]), Double.parseDouble(split[2])));
            }
            return addresses;
        }
        catch (Exception ex){
            throw new LoadFileException("Cannot load file from resource", ex);
        }
    }
}
