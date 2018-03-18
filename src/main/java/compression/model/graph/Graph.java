package compression.model.graph;

import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;


@RequiredArgsConstructor
public abstract class Graph<TVertex, TEdge> {
    private final TVertex[] vertices;
    private final List<TEdge>[] neighbours;

    public int getVerticesCount(){
        return vertices.length;
    }

    public TVertex getLocationFromId(int locationId){
        return vertices[locationId];
    }

    public List<TEdge> getNeighbours(int locationId){
        return neighbours[locationId];
    }

    public static abstract class GraphBuilder<TVertex, TEdge>{
        protected TVertex[] vertices;
        protected List<TEdge>[] edges;

        public void specifyLocations(List<? extends TVertex> vertices){
            this.vertices = (TVertex[]) vertices.toArray();
            this.edges = (List<TEdge>[]) new Object[vertices.size()];
            for(Integer i =0; i<vertices.size(); i++){
                edges[i] = new LinkedList<>();
            }
        }

        public void addEdge(int vertexId, TEdge edge){
            edges[vertexId].add(edge);
        }
    }
}
