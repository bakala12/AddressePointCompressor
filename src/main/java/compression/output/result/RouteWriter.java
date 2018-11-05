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

/**
 * An implementation of IRouteWriter interface.
 */
public class RouteWriter implements IRouteWriter{

    /**
     * Writes solution routes to a file.
     * @param solution Solution routes.
     * @param path File path.
     */
    @Override
    public void writeRoute(ResolvedSolution solution, String path) {
        try(PrintWriter writer = new PrintWriter(path)) {
            Long depotId = solution.getOriginalProblem().getDepot().getId();
            Double depotLat = solution.getOriginalProblem().getDepot().getLocation().getLatitude();
            Double depotLon = solution.getOriginalProblem().getDepot().getLocation().getLongitude();
            Integer routeId = 1;
            writer.println("id,lat,lon,routeId");
            for(VrpSolutionRoute route : solution.getRoutes()){
                writer.println(depotId+","+depotLat+","+depotLon+","+0);
                for(VrpSolutionRouteNode n : route.getNodes()){
                    Location location = n.getNodeLocation();
                    Coordinate coordinate = location.getCoordinate();
                    if(coordinate != null){
                        Double lat = coordinate.getY();
                        Double lon = coordinate.getX();
                        writer.println(n.getNodeId()+","+lat.toString()+","+lon.toString()+","+ routeId);
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
