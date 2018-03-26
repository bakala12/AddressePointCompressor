package compression.graph.simple;

import compression.graph.IVertex;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleVertex<T> implements IVertex {
    @Getter
    private final T element;

    @Override
    public boolean equals(Object other){
        if(other == null || !(other instanceof SimpleVertex))
            return false;
        return element.equals(((SimpleVertex<T>) other).getElement());
    }
}
