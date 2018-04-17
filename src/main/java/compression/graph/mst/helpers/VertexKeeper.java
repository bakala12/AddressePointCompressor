package compression.graph.mst.helpers;

import compression.graph.IVertex;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.LinkedList;
import java.util.List;

public abstract class VertexKeeper<TVertex> implements IVertex{
    @RequiredArgsConstructor
    public static class SingleVertexKeeper<TVertex> extends VertexKeeper<TVertex>{
        @Getter
        private final TVertex vertex;

        @Override
        public List<TVertex> getVertices(){
            List<TVertex> list = new LinkedList<>();
            list.add(vertex);
            return list;
        }

        @Override
        public String toString(){
            return "SingleVertexKeeper->"+vertex.toString();
        }

        
    }

    @RequiredArgsConstructor
    public static class MultipleVertexKeeper<TVertex> extends VertexKeeper<TVertex>{
        @Getter
        private final List<TVertex> vertices;

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("MultipleVertexKeeper->");
            for(TVertex v : vertices){
                builder.append(v.toString()+",");
            }
            return builder.toString();
        }
    }


    public static <T> VertexKeeper<T> create(T vertex){
        return new SingleVertexKeeper<T>(vertex);
    }

    public static <T> VertexKeeper<T> create(List<T> vertices){
        return new MultipleVertexKeeper<T>(vertices);
    }

    public abstract List<TVertex> getVertices();
}
