package compression.input.parsing;

import java.io.InputStream;

/**
 * General interface for parsing purposes.
 * @param <TOutput> Type of output model that represents file content.
 */
public interface IParser<TOutput> {
    /**
     * Parses content of the stream and return an object that represents that content.
     * @param stream Input stream.
     * @return An object that represents content of the stream.
     */
    TOutput parse(InputStream stream);
}
