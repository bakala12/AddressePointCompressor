package compression.model.structures;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents disjoint set (union-find structure).
 * @param <T> Type of elements.
 */
public final class DisjointSet<T>
{
    private final Map<T, DisjointSetNode<T>> disjointSets = new HashMap<>();

    /**
     * Initializes a new instance of Disjoint set.
     * @param items Items.
     */
    public DisjointSet(Collection<T> items){
        for(T item : items){
            disjointSets.put(item, new DisjointSetNode<>(item));
        }
    }

    /**
     * Finds the set in which element is stored.
     * @param e Element.
     * @return The set in which element is stored.
     */
    public T find( T e ) {
        DisjointSetNode<T> node = find( getNode( e ) );
        if ( node == node.getParent() )
            return node.getElement();
        node.setParent( find( node.getParent() ) );
        return node.getParent().getElement();
    }

    private DisjointSetNode<T> find( DisjointSetNode<T> node ) {
        if ( node == node.getParent() )
            return node;
        return find( node.getParent() );
    }

    /**
     * Joins two sets.
     * @param e1 First set.
     * @param e2 Second set.
     */
    public void union( T e1, T e2 ) {
        DisjointSetNode<T> e1Root = find( getNode( e1 ) );
        DisjointSetNode<T> e2Root = find( getNode( e2 ) );
        if ( e1Root == e2Root )
            return;
        int comparison = e1Root.compareTo( e2Root );
        if ( comparison < 0 )
            e1Root.setParent( e2Root );
        else if ( comparison > 0 )
            e2Root.setParent( e1Root );
        else {
            e2Root.setParent( e1Root );
            e1Root.increaseRank();
        }
    }

    private DisjointSetNode<T> getNode( T e ) {
        DisjointSetNode<T> node = disjointSets.get( e );
        if ( node == null ) {
            node = new DisjointSetNode<T>( e );
            disjointSets.put( e, node );
        }
        return node;
    }
}