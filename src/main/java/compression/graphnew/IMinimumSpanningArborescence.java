package compression.graphnew;

import java.util.Set;

public interface IMinimumSpanningArborescence<V,E> {
    V getRoot();
    Set<E> getEdges();
}
