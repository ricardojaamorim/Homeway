package dataStructures;

import dataStructures.exceptions.NoSuchElementException;
/**
 * Iterator of keys
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic element
 */
class KeysIterator<E> implements Iterator<E> {

    /**
     * Map iterator.
     */
    private final Iterator<Map.Entry<E,?>> iterator;


    public KeysIterator(Iterator<Map.Entry<E,?>> it) {
        iterator = it;
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(n).
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        return iterator.hasNext();
    }

    /**
     * Returns the next element in the iteration.
     * @return the next element in the iteration
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(n).
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public E next() {
        return iterator.next().key();
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(n).
     */
    public void rewind() {
        iterator.rewind();
    }
}
