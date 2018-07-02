package compression.output.result;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.solution.route.VehicleRoute;
import com.graphhopper.jsprit.core.problem.solution.route.activity.TourActivity;
import com.graphhopper.jsprit.core.util.Coordinate;
import compression.services.resolving.ResolvedSolution;
import compression.services.resolving.VrpSolutionRoute;
import compression.services.resolving.VrpSolutionRouteNode;

import java.io.PrintWriter;
import java.util.List;

public class RouteWriter implements IRouteWriter{

    @Override
    public void writeRoute(ResolvedSolution solution, String path) {
        try(PrintWriter writer = new PrintWriter(path)) {
            Integer routeId = 1;
            for(VrpSolutionRoute route : solution.getRoutes()){
                for(VrpSolutionRouteNode n : route.getNodes()){
                    Location location = n.getNodeLocation();
                    Coordinate coordinate = location.getCoordinate();
                    if(coordinate != null){
                        Double lat = coordinate.getY();
                        Double lon = coordinate.getX();
                        writer.println(lat.toString()+" "+lon.toString()+" "+ routeId);
                    } else{
                        throw new Exception("No coordinate for location: "+location);
                    }
                }
                routeId++;
            }
        } catch (Exception ex){
            System.out.println("Unable to write solution route to a file: "+ex.getMessage());
        }
    }
}
