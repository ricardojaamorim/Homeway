package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * Iterator Abstract Data Type with Filter
 * Includes description of general methods for one way iterator.
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class FilterIterator<E> implements Iterator<E> {

    /**
     *  Iterator of elements to filter.
     */
    Iterator<E> iterator;

    /**
     *  Filter.
     */
    Predicate<E> criterion;

    /**
     * Node with the next element in the iteration.
     */
    E nextToReturn;


    /**
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(n).
     * @param list to be iterated
     * @param criterion filter
     */
    public FilterIterator(Iterator<E> list, Predicate<E> criterion) {
        iterator = list;
        this.criterion = criterion;
        nextToReturn = null;
        advance();
    }

    /**
     * Advances to the next valid element.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(n).
     */
    private void advance(){
        while(iterator.hasNext() && !criterion.check(nextToReturn) )
            nextToReturn = iterator.next();
        if(!criterion.check(nextToReturn) )  //Verifies the last element.
            nextToReturn = null;
    }

    /**
     * Returns true if next would return an element
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        return nextToReturn != null;
    }

    /**
     * Returns the next element in the iteration.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(n).
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next() {
        if(!hasNext() )
            throw new NoSuchElementException();

        E element = nextToReturn;
        if(iterator.hasNext())
            nextToReturn = iterator.next();
        else
            nextToReturn = null;
        advance();
        return element;
    }

    /**
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(n).
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     */
    public void rewind() {
        iterator.rewind();
        advance();
    }

}
