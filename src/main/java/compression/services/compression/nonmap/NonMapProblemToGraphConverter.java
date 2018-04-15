package compression.services.compression.nonmap;

import compression.model.vrp.*;
import compression.services.compression.IProblemToGraphConverter;
import compression.services.compression.ProblemGraph;
import compression.services.compression.nonmap.graph.LocationEdge;
import compression.services.compression.nonmap.graph.LocationGraph;
import compression.services.compression.nonmap.graph.LocationVertex;

import java.util.LinkedList;
import java.util.List;

public class NonMapProblemToGraphConverter implements IProblemToGraphConverter<LocationVertex, LocationEdge, LocationGraph>{

    @Override
    public ProblemGraph<LocationVertex, LocationEdge, LocationGraph> convert(VrpProblem problem) {
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
        return new ProblemGraph<>(problem, graph, graph.getVertex(locations.get(0)));
    }

    private void addEuclideanDistances(LocationGraph graph, List<Location> locations){
        for(Location from : locations){
            for(Location to : locations){
                if(from != to)
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
                if(cfrom.getId() != cto.getId())
                    graph.addExplicitEdge(cfrom.getLocation(), cto.getLocation(), dm.getDistance(cfrom.getId(), cto.getId()));
            }
            graph.addExplicitEdge(cfrom.getLocation(), depotLoc, dm.getDistance(cfrom.getId(), depotId));
            graph.addExplicitEdge(depotLoc, cfrom.getLocation(), dm.getDistance(depotId, cfrom.getId()));
        }
    }
}
