package compression.model.structures;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * Represents Disjoint set node
 * @param <T>
 */
@RequiredArgsConstructor
final class DisjointSetNode<T> implements Comparable<DisjointSetNode<T>>{
    @Getter
    private final T element;
    @Getter @Setter
    private DisjointSetNode<T> parent = this;
    @Getter @Setter
    private Integer rank = 0;

    /**
     * Increases the rank of the node.
     */
    public void increaseRank()
    {
        rank++;
    }

    /**
     * Comparison mode.
     * @param o Object to compare.
     * @return Comparison result.
     */
    @Override
    public int compareTo( DisjointSetNode<T> o )
    {
        return rank.compareTo( o.getRank() );
    }

}
