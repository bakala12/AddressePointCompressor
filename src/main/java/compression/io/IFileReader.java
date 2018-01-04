package compression.io;

import java.io.InputStream;
import java.util.List;

public interface IFileReader<T> {
    List<T> readFile(InputStream stream) throws LoadFileException;
}
