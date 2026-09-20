/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package dataStructures;

import java.io.Serializable;

public interface ListHashMap<E> extends Serializable {

    /**
     * Returns true iff the list contains no elements.
     * @return  true iff the list contains no elements.
     */
    boolean isEmpty( );

    /**
     * Returns the number of elements in the list.
     * @return the number of elements in the list.
     */
    int size( );

    //it doesn't make sense having a get operation.

    /**
     * Adds the element to the end of the list.
     */
    void add(E element);

    /**
     * Removes the element from the list.
     * @return the element removed.
     */
    E remove( E element );


    /**
     * Returns an iterator of the elements in the list.
     * @return an iterator of the elements in the list.
     */
    Iterator<E> iterator( );


    /**
     * Returns a two-way iterator of the elements in the list.
     *
     * @return a two-way iterator of the elements in the list.
     */
    TwoWayIterator<E> twoWayiterator();


}

