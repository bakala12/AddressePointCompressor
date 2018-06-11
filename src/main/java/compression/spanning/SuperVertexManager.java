package compression.spanning;

import compression.model.graph.Edge;

import java.util.ArrayList;
import java.util.List;

final class SuperVertexManager {
    private ArrayList<SuperVertexKeeper> list;
    private int vertices;

    public SuperVertexManager(){
        list = new ArrayList<>();
        list.add(new SuperVertexKeeper(-1)); //guard
        vertices = 0;
    }

    public SuperVertexKeeper get(Integer v){
        return list.get(v);
    }

    public SuperVertexKeeper add(){
        Integer c = vertices +1;
        vertices++;
        SuperVertexKeeper vk = new SuperVertexKeeper(c);
        list.add(vk);
        return vk;
    }

    protected void add(SuperVertexKeeper keeper){
        list.add(keeper);
        vertices++;
    }

    public List<Edge> getAllInEdgesExceptRoot(Integer root, Integer n){
        List<Edge> e = new ArrayList<>();
        int i=0;
        for(SuperVertexKeeper vk : list){
            if(i==0) {
                i++;
                continue;
            }
            if(i>n) break;
            if(vk.getV() != root){
                e.add(vk.getIn());
            }
            i++;
        }
        return e;
    }
}
