package compression.parsing;

import java.io.InputStream;

public interface IParser<TOutput> {
    TOutput parse(String json);
    TOutput parse(InputStream stream);
}
