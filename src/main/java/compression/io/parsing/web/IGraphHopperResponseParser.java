package compression.io.parsing.web;

import compression.io.parsing.IParser;
import compression.model.graphhopper.Route;

public interface IGraphHopperResponseParser extends IParser<Route>{
    Route parse(String json);
}
