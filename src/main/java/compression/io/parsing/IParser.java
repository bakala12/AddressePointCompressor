package compression.io.parsing;

import java.io.InputStream;

public interface IParser<TOutput> {
    TOutput parse(InputStream stream);
}
