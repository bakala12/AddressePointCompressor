package compression.io.parsing.web;

import compression.model.vrp.Route;
import compression.io.parsing.IParser;

public interface IGraphHopperResponseParser extends IParser<Route>{
    Route parse(String json);
}
