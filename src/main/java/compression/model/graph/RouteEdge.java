package compression.model.graph;

import compression.model.vrp.Route;
import lombok.Getter;

public class RouteEdge extends SimpleEdge{
    @Getter
    private Route route;

    public RouteEdge(int from, int to, double weight, Route route){
        super(from, to, weight);
        this.route = route;
    }
}
