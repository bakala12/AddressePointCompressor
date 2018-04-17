package compression.graph.mst.helpers;

import compression.graph.IEdge;
import compression.graph.IGraph;
import compression.graph.IVertex;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;

public class GraphKeeper<TVertex extends IVertex, TEdge extends IEdge<TVertex>> {
    @Getter
    private List<VertexKeeper<TVertex>> vertices;
    @Getter
    private List<EdgeKeeper<TVertex, TEdge>> edges;
    private Map<TVertex, VertexKeeper<TVertex>> vertexMap;

    public GraphKeeper(Collection<TVertex> items, List<TEdge> edges) {
        vertexMap = new HashMap<>();
        vertices = new LinkedList<>();
        for(TVertex v : items){
            VertexKeeper<TVertex> vk = VertexKeeper.create(v);
            vertexMap.put(v, vk);
            vertices.add(vk);
        }
        this.edges = new LinkedList<>();
        for(TEdge e : edges){
            VertexKeeper<TVertex> fr = vertexMap.get(e.getFrom());
            VertexKeeper<TVertex> to = vertexMap.get(e.getTo());
            this.edges.add(new EdgeKeeper<>(fr, to, e.getWeight(), e));
        }
    }

    private GraphKeeper(List<VertexKeeper<TVertex>> vertices, Map<TVertex, VertexKeeper<TVertex>> map, List<EdgeKeeper<TVertex, TEdge>> edges){
        this.vertexMap = map;
        this.vertices = new LinkedList<>();
        for(VertexKeeper<TVertex> v : vertices){
            this.vertices.add(v);
        }
        this.edges = edges;

    }

    public void removeDestinationEdges(TVertex vertex){
        List<EdgeKeeper<TVertex, TEdge>> toRemove = new LinkedList<>();
        VertexKeeper<TVertex> vv = vertexMap.get(vertex);
        for(EdgeKeeper<TVertex, TEdge> e : edges){
            if(e.getTo() == vv){
                toRemove.add(e);
            }
        }
        for(EdgeKeeper<TVertex, TEdge> e : toRemove){
            edges.remove(e);
        }
    }

    private List<EdgeKeeper<TVertex, TEdge>> getEdgesTo(VertexKeeper<TVertex> v){
        List<EdgeKeeper<TVertex, TEdge>> list = new LinkedList<>();
        for(EdgeKeeper<TVertex, TEdge> e : edges) {
            if(e.getTo()==v){
                list.add(e);
            }
        }
        return list;
    }

    public EdgeKeeper<TVertex, TEdge> getSingleInputEdge(VertexKeeper<TVertex> v){
        List<EdgeKeeper<TVertex, TEdge>> edges = getEdgesTo(v);
        if(edges.size()!= 1)
            throw new RuntimeException();
        return edges.get(0);
    }

    private List<EdgeKeeper<TVertex, TEdge>> getEdgesFrom(VertexKeeper<TVertex> v){
        List<EdgeKeeper<TVertex, TEdge>> list = new LinkedList<>();
        for(EdgeKeeper<TVertex, TEdge> e : edges) {
            if(e.getFrom()==v){
                list.add(e);
            }
        }
        return list;
    }

    public GraphKeeper<TVertex, TEdge> takeSmallestWeightEdgesForEachVertex(){
        List<EdgeKeeper<TVertex, TEdge>> newEdges = new LinkedList<>();
        for(VertexKeeper<TVertex> v : vertices){
            double minWeight = Double.MAX_VALUE;
            EdgeKeeper<TVertex, TEdge> minEdge = null;
            for(EdgeKeeper<TVertex, TEdge> e : getEdgesTo(v)){
                if(e.getWeight() < minWeight){
                    minEdge = e;
                    minWeight = e.getWeight();
                }
            }
            if(minEdge != null)
                newEdges.add(minEdge);
        }
        return new GraphKeeper(vertices, vertexMap, newEdges);
    }

    public Boolean findCycle(List<VertexKeeper<TVertex>> potentialCycle){
        List<VertexKeeper<TVertex>> cycle = new LinkedList<>();
        Map<VertexKeeper<TVertex>, Boolean> visited = new HashMap<>();
        for (VertexKeeper<TVertex> v : vertices){
            visited.clear();
            cycle.clear();
            if(dfsFindCycle(v, v, cycle, visited)){
                for (VertexKeeper<TVertex> cv : cycle){
                    potentialCycle.add(cv);
                }
                return true;
            }
        }
        return false;
    }

    private Boolean dfsFindCycle(VertexKeeper<TVertex> start, VertexKeeper<TVertex> current, List<VertexKeeper<TVertex>> cycle,
                                 Map<VertexKeeper<TVertex>, Boolean> visited){
        visited.put(current, true);
        cycle.add(current);
        for(EdgeKeeper<TVertex, TEdge> e : getEdgesFrom(current)){
            VertexKeeper<TVertex> vv = e.getTo();
            if(vv == start) return true;
            if(!visited.getOrDefault(vv, false)){
                if(dfsFindCycle(start, vv, cycle, visited))
                    return true;
            }
        }
        cycle.remove(current);
        return false;
    }

    @AllArgsConstructor
    private class EdgeKeeperHelper {
        @Getter
        private EdgeKeeper<TVertex, TEdge> oldE;
        @Getter
        private EdgeKeeper<TVertex, TEdge> newE;
    }

    public GraphKeeper<TVertex, TEdge> shrinkCycle(List<VertexKeeper<TVertex>> cycle,
                                                   Map<EdgeKeeper<TVertex, TEdge>, EdgeKeeper<TVertex, TEdge>> edgesMap,
                                                   GraphKeeper<TVertex, TEdge> p){
        Map<TVertex, VertexKeeper<TVertex>> newMap = new HashMap<>();
        for(Map.Entry<TVertex, VertexKeeper<TVertex>> e : vertexMap.entrySet()){
            if(!cycle.contains(e.getValue())){
                newMap.put(e.getKey(), e.getValue());
            }
        }
        List<VertexKeeper<TVertex>> newVertices = new LinkedList<>();
        for(VertexKeeper<TVertex> v : vertices){
            if(!cycle.contains(v)){
                newVertices.add(v);
            }
        }
        List<TVertex> cycleVerticles = new LinkedList<>();
        for(VertexKeeper<TVertex> cv : cycle){
            cycleVerticles.addAll(cv.getVertices());
        }
        VertexKeeper<TVertex> cycleVertex = VertexKeeper.create(cycleVerticles);
        newVertices.add(cycleVertex);
        List<EdgeKeeper<TVertex, TEdge>> newEdges = new LinkedList<>();
        edgesMap.clear();
        HashMap<VertexKeeper<TVertex>, EdgeKeeperHelper> shortestIncoming = new HashMap<>();
        HashMap<VertexKeeper<TVertex>, EdgeKeeperHelper> shortestOutcomming = new HashMap<>();
        for(EdgeKeeper<TVertex, TEdge> e : edges){
            if(!cycle.contains(e.getFrom()) && cycle.contains(e.getTo())) {
                List<EdgeKeeper<TVertex, TEdge>> edgesTo = p.getEdgesTo(e.getTo());
                if (edgesTo.size() != 1) {
                    throw new RuntimeException("Algorithm error - not exactly one incoming edge to vertex.");
                }
                EdgeKeeper<TVertex, TEdge> newE = new EdgeKeeper<>(e.getFrom(), cycleVertex, e.getWeight() - edgesTo.get(0).getWeight(), e.getEdge());
                EdgeKeeperHelper minIn = shortestIncoming.getOrDefault(e.getFrom(), null);
                if (minIn == null || newE.getWeight() < minIn.newE.getWeight()){
                    shortestIncoming.put(e.getFrom(), new EdgeKeeperHelper(e, newE));
                }
            }
            else if(cycle.contains(e.getFrom()) && !cycle.contains(e.getTo())){
                EdgeKeeper<TVertex, TEdge> newE = new EdgeKeeper<>(cycleVertex, e.getTo(), e.getWeight(), e.getEdge());
                EdgeKeeperHelper minOut = shortestOutcomming.getOrDefault(e.getTo(), null);
                if(minOut == null || newE.getWeight() < minOut.newE.getWeight()){
                    shortestOutcomming.put(e.getTo(), new EdgeKeeperHelper(e, newE));
                }
            }
            else if(!cycle.contains(e.getFrom()) && !cycle.contains(e.getTo())){
                EdgeKeeper<TVertex, TEdge> newE = new EdgeKeeper<>(e.getFrom(), e.getTo(), e.getWeight(), e.getEdge());
                newEdges.add(newE);
                edgesMap.put(newE, e);
            }
        }
        for(Map.Entry<VertexKeeper<TVertex>, EdgeKeeperHelper> e : shortestIncoming.entrySet()){
            newEdges.add(e.getValue().newE);
            edgesMap.put(e.getValue().newE, e.getValue().oldE);
        }
        for(Map.Entry<VertexKeeper<TVertex>, EdgeKeeperHelper> e : shortestOutcomming.entrySet()){
            newEdges.add(e.getValue().newE);
            edgesMap.put(e.getValue().newE, e.getValue().oldE);
        }
        return new GraphKeeper(newVertices, newMap, newEdges);
    }

    public void markEdges(GraphKeeper<TVertex, TEdge> recursiveResult, Map<EdgeKeeper<TVertex, TEdge>, EdgeKeeper<TVertex, TEdge>> recursiveMap,
                          List<VertexKeeper<TVertex>> cycle, VertexKeeper<TVertex> v){
        List<EdgeKeeper<TVertex, TEdge>> newEdges = new LinkedList<>();
        for(EdgeKeeper<TVertex, TEdge> e : recursiveResult.edges){
            newEdges.add(recursiveMap.get(e));
        }
        VertexKeeper<TVertex> prev = cycle.get(cycle.size()-1);
        for(VertexKeeper<TVertex> cv : cycle){
            if(cv != v){
                for(EdgeKeeper<TVertex, TEdge> ed : getEdgesTo(cv)){
                    if(ed.getFrom() == prev){
                        newEdges.add(ed);
                    }
                }
            }
            prev = cv;
        }
        edges = newEdges;
    }
}
