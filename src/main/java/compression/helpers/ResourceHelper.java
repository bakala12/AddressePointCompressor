package compression.helpers;

import compression.io.IFileReader;

import java.io.InputStream;
import java.util.List;

public class ResourceHelper {
    public static <T> List<T> readResource(IFileReader<T> reader, String resourceName){
        try(InputStream stream = ResourceHelper.class.getResourceAsStream(resourceName)){
            return reader.readFile(stream);
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
