package compression.services.aggregation.nonmap;

import compression.model.vrp.*;
import compression.services.aggregation.IProblemToGraphConverter;
import compression.services.aggregation.ProblemGraph;
import compression.services.aggregation.nonmap.graph.LocationGraph;

import java.util.LinkedList;
import java.util.List;

public class NonMapProblemToGraphConverter implements IProblemToGraphConverter<LocationGraph>{

    @Override
    public ProblemGraph<LocationGraph> convert(VrpProblem problem) {
        List<Location> locations = new LinkedList<>();
        locations.add(problem.getDepot().getLocation());
        for(Client c : problem.getClients()){
            locations.add(c.getLocation());
        }
        LocationGraph graph = new LocationGraph(locations);
        if(problem.getProblemMetric() == VrpProblemMetric.Explicit){
            addDistancesFromDistanceMatrix(graph, problem);
        }
        if (problem.getProblemMetric() == VrpProblemMetric.Euclidean){
            addEuclideanDistances(graph, locations);
        }
        return new ProblemGraph<>(problem, graph);
    }

    private void addEuclideanDistances(LocationGraph graph, List<Location> locations){
        for(Location from : locations){
            for(Location to : locations){
                graph.addEuclideanEdge(from, to);
            }
        }
    }

    private void addDistancesFromDistanceMatrix(LocationGraph graph, VrpProblem problem){
        SymmetricalDistanceMatrix dm = problem.getDistanceMatrix();
        Long depotId = problem.getDepot().getId();
        Location depotLoc = problem.getDepot().getLocation();
        for(Client cfrom : problem.getClients()){
            for(Client cto : problem.getClients()){
                graph.addExplicitEdge(cfrom.getLocation(), cto.getLocation(), dm.getDistance(cfrom.getId(), cto.getId()));
            }
            graph.addExplicitEdge(cfrom.getLocation(), depotLoc, dm.getDistance(cfrom.getId(), depotId));
            graph.addExplicitEdge(depotLoc, cfrom.getLocation(), dm.getDistance(depotId, cfrom.getId()));
        }
    }
}
