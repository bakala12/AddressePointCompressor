package compression.model.structures;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
final class DisjointSetNode<T> implements Comparable<DisjointSetNode<T>>{
    @Getter
    private final T element;
    @Getter @Setter
    private DisjointSetNode<T> parent = this;
    @Getter @Setter
    private Integer rank = 0;

    public void increaseRank()
    {
        rank++;
    }

    @Override
    public int compareTo( DisjointSetNode<T> o )
    {
        return rank.compareTo( o.getRank() );
    }

}
