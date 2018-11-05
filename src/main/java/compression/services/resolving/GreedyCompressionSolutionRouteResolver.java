package compression.services.resolving;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.solution.VehicleRoutingProblemSolution;
import com.graphhopper.jsprit.core.problem.solution.route.VehicleRoute;
import com.graphhopper.jsprit.core.problem.solution.route.activity.TourActivity;
import com.graphhopper.jsprit.core.util.Solutions;
import compression.model.jsprit.VrpProblemSolution;
import compression.model.vrp.DistanceMatrix;
import compression.model.vrp.Vehicle;
import compression.model.vrp.VrpProblem;
import compression.model.vrp.helpers.AggregatedService;
import compression.model.vrp.helpers.LocationVertex;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * Implements greedy decompression algorithm.
 */
public class GreedyCompressionSolutionRouteResolver implements ISolutionRouteResolver {

    @AllArgsConstructor
    private class ConvertedRoutes{
        @Getter
        private List<VrpSolutionRoute> routes;
        @Getter
        private Double cost;
    }

    /**
     * Resolves routes of compressed problem solution and decompresses them.
     * @param originalProblem Original VRP problem.
     * @param best Compressed problem solution.
     * @param compressionMap Compression map
     * @return Resolved original problem solution.
     */
    @Override
    public ResolvedSolution resolveRoutes(VrpProblem originalProblem, VehicleRoutingProblemSolution best, Map<Long, AggregatedService> compressionMap) {
        ConvertedRoutes routes = convertRoutes(originalProblem, best, originalProblem.getDepot().getId(), compressionMap);
        return new ResolvedSolution(originalProblem, routes.cost, routes.routes);
    }

    protected ConvertedRoutes convertRoutes(VrpProblem problem, VehicleRoutingProblemSolution best, Long depotId, Map<Long, AggregatedService> compressionMap){
        List<VrpSolutionRoute> routes = new ArrayList<>();
        Double cost = 0.0;
        for(VehicleRoute r : best.getRoutes()){
            List<AggregatedService> services = RouteSegmentHelper.convertSolutionRouteToAggregatedServicesList(r, compressionMap);
            ExtendedVrpSolutionRoute exRoute = RouteSegmentHelper.convertRoute(services, r.getVehicle().getId(),depotId, problem.getDistanceMatrix());
            cost += exRoute.getUpdatedCost();
            VrpSolutionRoute rr = new VrpSolutionRoute(exRoute.getVehicleId(), exRoute.getNodes());
            routes.add(rr);
        }
        return new ConvertedRoutes(routes, cost);
    }
}

@AllArgsConstructor
class ExtendedVrpSolutionRoute{
    @Getter
    private String vehicleId;
    @Getter
    private List<VrpSolutionRouteNode> nodes;
    @Getter
    private Double updatedCost;
}

@AllArgsConstructor
class AddNextServiceResult{
    @Getter
    private Long newLast;
    @Getter
    private Double updatedDistance;
}

class RouteSegmentHelper{
    public static List<AggregatedService> convertSolutionRouteToAggregatedServicesList(VehicleRoute route, Map<Long, AggregatedService> compressionMap){
        List<AggregatedService> list = new ArrayList<>();
        for(TourActivity a : route.getActivities()){
            Long activityId = Long.parseLong(a.getLocation().getId());
            AggregatedService s = compressionMap.get(activityId);
            list.add(s);
        }
        return list;
    }

    private static Location changeLocation(compression.model.vrp.Location location){
        return Location.newInstance(location.getLongitude(), location.getLatitude());
    }

    private static Boolean shouldRotateService(Long lastId, AggregatedService service, Long nextId, DistanceMatrix matrix){
        Double currentDistance = matrix.getDistance(lastId, service.getInputVertex().getId()) + service.getInternalDistance() + matrix.getDistance(service.getOutputVertex().getId(), nextId);
        Double revertedDistance = matrix.getDistance(lastId, service.getOutputVertex().getId()) + service.getInternalBackwardDistance() + matrix.getDistance(service.getInputVertex().getId(), nextId);
        return (currentDistance - revertedDistance) > 1e-10; //Normal comparison by > gives errors!!!
    }

    private static AddNextServiceResult addNextService(List<VrpSolutionRouteNode> nodes, Long lastId, AggregatedService service, Boolean shouldRevert, DistanceMatrix matrix, Double currentDistance){
        List<LocationVertex> vertices = service.getVertices();
        Long newlast = service.getOutputVertex().getId();
        Double cost = currentDistance;
        if(shouldRevert) {
            Collections.reverse(vertices);
            newlast = service.getInputVertex().getId();
        }
        for(LocationVertex v : vertices){
            VrpSolutionRouteNode n = new VrpSolutionRouteNode(v.getId(), changeLocation(v.getLocation()));
            nodes.add(n);
            Double d = matrix.getDistance(lastId, v.getId());
            cost += d;
            lastId = v.getId();
        }
        return new AddNextServiceResult(newlast, cost);
    }

    public static ExtendedVrpSolutionRoute convertRoute(List<AggregatedService> route, String vehicleId, Long depotId, DistanceMatrix matrix){
        List<VrpSolutionRouteNode> nodes = new ArrayList<>();
        Double updatedDistance = 0.0;
        Long lastId = depotId;
        AggregatedService prev = null;
        for(AggregatedService s : route){
            if(prev == null){
                prev = s;
                continue;
            }
            Long nextId = s.getInputVertex().getId();
            Boolean shouldReverse = shouldRotateService(lastId, prev, nextId, matrix);
            AddNextServiceResult r = addNextService(nodes, lastId, prev, shouldReverse, matrix, updatedDistance);
            updatedDistance = r.getUpdatedDistance();
            lastId = r.getNewLast();
            prev = s;
        }
        Boolean shouldReverse = shouldRotateService(lastId, prev, depotId, matrix);
        AddNextServiceResult r = addNextService(nodes, lastId, prev, shouldReverse, matrix, updatedDistance);
        lastId = r.getNewLast();
        updatedDistance = r.getUpdatedDistance() + matrix.getDistance(lastId, depotId);
        return new ExtendedVrpSolutionRoute(vehicleId, nodes, updatedDistance);
    }
}