package compression.services.resolving;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.solution.route.VehicleRoute;
import com.graphhopper.jsprit.core.problem.solution.route.activity.TourActivity;
import com.graphhopper.jsprit.core.util.Solutions;
import compression.model.jsprit.VrpProblemSolution;
import compression.model.vrp.VrpProblem;
import compression.model.vrp.helpers.AggregatedService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Serves common methods for solution decompression and resolving routes.
 */
public abstract class BaseSolutionRouteResolver implements ISolutionRouteResolver {

    /**
     * Resolves routes of compressed problem solution and decompresses them.
     * @param originalProblem Original VRP problem.
     * @param best Compressed problem solution.
     * @param compressionMap Compression map
     * @return Resolved original problem solution.
     */
    @Override
    public ResolvedSolution resolveRoutes(VrpProblem originalProblem, VehicleRoutingProblemSolution best, Map<Long, AggregatedService> compressionMap){
        Double cost = best.getCost();
        List<VrpSolutionRoute> routes = convertRoutes(originalProblem, best, compressionMap);
        return new ResolvedSolution(originalProblem, cost, routes);
    }

    /**
     * Converts routes of compressed problem.
     * @param problem Original VRP problem.
     * @param best Best solution for compressed problem.
     * @param compressionMap Compression map.
     * @return Decompressed routes.
     */
    protected List<VrpSolutionRoute> convertRoutes(VrpProblem problem, VehicleRoutingProblemSolution best, Map<Long, AggregatedService> compressionMap){
        List<VrpSolutionRoute> routes = new ArrayList<>();
        for(VehicleRoute r : best.getRoutes()){
            String vehicleId = r.getVehicle().getId();
            List<VrpSolutionRouteNode> nodes = new ArrayList<>();
            for(TourActivity a : r.getActivities()){
                Location location = a.getLocation();
                List<VrpSolutionRouteNode> n = convertLocationToNodes(problem, location, compressionMap);
                nodes.addAll(n);
            }
            VrpSolutionRoute route = new VrpSolutionRoute(vehicleId, nodes);
            routes.add(route);
        }
        return routes;
    }

    /**
     * Decompresses aggregated vertex to part of route.
     * @param problem Original VRP problem
     * @param location Compressed problem vertex.
     * @param aggregatedService Map of aggregated vertices.
     * @return List of route nodes.
     */
    protected abstract List<VrpSolutionRouteNode> convertLocationToNodes(VrpProblem problem, Location location, Map<Long, AggregatedService> aggregatedService);
}
