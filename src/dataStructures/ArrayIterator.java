package dataStructures;

/**
 * Array dataStructures.Iterator
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
class ArrayIterator<E> implements Iterator<E> {
    private E[] elems;
    private int counter;
    private int current;

    public ArrayIterator(E[] elems, int counter) {
        this.elems = elems;
        this.counter = counter;
        rewind();
    }

    /**
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     */
    @Override
    public void rewind() {
        current = 0;
    }

    /**
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     */
    @Override
    public boolean hasNext() {
        return current < counter;
    }

    /**
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     */
    @Override
    public E next() {
        return elems[current++];
    }

}
