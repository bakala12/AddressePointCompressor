package compression.input.parsing.web;

import compression.input.parsing.IParser;
import compression.model.graphhopper.Route;

public interface IGraphHopperResponseParser extends IParser<Route>{
    Route parse(String json);
}
