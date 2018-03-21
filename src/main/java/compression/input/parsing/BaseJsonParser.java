package compression.input.parsing;

import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;

public abstract class BaseJsonParser<TOutput>
    implements IParser<TOutput> {

    protected final JSONParser parser;

    public BaseJsonParser(){
        parser = new JSONParser();
    }

    public TOutput parse(InputStream inputStream){
        try {
            Object object = parser.parse(new InputStreamReader(inputStream));
            return parseObject(object);
        } catch (Exception e) {
            throw new ParsingException("Parsing failed.", e);
        }
    }

    public TOutput parse(String json){
        try {
            Object object = parser.parse(json);
            return parseObject(object);
        } catch (Exception e) {
            throw new ParsingException("Parsing failed.", e);
        }
    }

    protected abstract TOutput parseObject(Object object);
}
