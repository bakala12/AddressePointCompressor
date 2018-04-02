package compression.services.compression.nonmap;

import com.graphhopper.jsprit.core.problem.Location;
import com.graphhopper.jsprit.core.problem.job.Shipment;
import compression.graph.IGraph;
import compression.graph.IVertex;
import compression.graph.mst.IMinimalSpanningTreeFinder;
import compression.model.vrp.Client;
import compression.model.vrp.VrpProblem;
import compression.model.vrp.VrpProblemMetric;
import compression.services.compression.IProblemToGraphConverter;
import compression.services.compression.ProblemGraph;
import compression.services.compression.nonmap.graph.LocationEdge;
import compression.services.compression.nonmap.graph.LocationGraph;
import compression.services.compression.nonmap.graph.LocationVertex;
import compression.services.compression.nonmap.graph.TreeBranch;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class NonMapCompressionService {

    private final IProblemToGraphConverter<LocationGraph> graphConverter;
    private final IMinimalSpanningTreeFinder minimalSpanningTreeFinder;

    public List<Shipment> getShipmentsForCompressedProblem(VrpProblem problem){
        ProblemGraph<LocationGraph> problemGraph = graphConverter.convert(problem);
        LocationGraph graph = problemGraph.getGraph();
        IGraph<LocationVertex, LocationEdge> tree = minimalSpanningTreeFinder.findMinimalSpanningTree(graph);
        LocationVertex depotVertex = graph.getVertex(problem.getDepot().getLocation());
        List<TreeBranch<LocationVertex>> treeBranches = CompressionHelper.compress(tree, depotVertex);
        List<Shipment> shipments = new LinkedList<>();
        for(TreeBranch<LocationVertex> branch : treeBranches){
            shipments.add(getShipment(branch, problem));
        }
        return shipments;
    }

    private Shipment getShipment(TreeBranch<LocationVertex> branch, VrpProblem problem){
        Shipment.Builder builder = Shipment.Builder.newInstance("builder");
        int demand = 0;
        compression.model.vrp.Location start = branch.firstVertex().getLocation();
        compression.model.vrp.Location end = branch.getEndVertex().getLocation();
        builder.setPickupLocation(Location.newInstance(start.getLatitude(), start.getLongitude()));
        builder.setDeliveryLocation(Location.newInstance(start.getLatitude(), start.getLongitude()));
        //TODO: Shipments doesn't support the feature I need
        builder.addSizeDimension(0, demand);
        return builder.build();
    }
}
