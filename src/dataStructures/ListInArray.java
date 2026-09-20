package dataStructures;

import dataStructures.exceptions.*;
/**
 * dataStructures.List in Array
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class ListInArray<E> implements List<E> {

    private static final int FACTOR = 2;
    /**
     * Array of generic elements E.
     */
    private E[] elems;

    /**
     * Number of elements in array.
     */
    private int counter;


    /**
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * Construtor with capacity.
     * @param dimension - initial capacity of array.
     */
    @SuppressWarnings("unchecked")
    public ListInArray(int dimension) {
        elems = (E[]) new Object[dimension];
        counter = 0;
    }
    /**
     * Returns true iff the list contains no elements.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return counter==0;
    }

    /**
     * Returns the number of elements in the list.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @return number of elements in the list
     */
    public int size() {
        return counter;
    }

    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @return dataStructures.Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new ArrayIterator<>(elems,counter);
    }

    /**
     * Checks if the array is full;
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     */
    private boolean isFull(){
        return elems.length == size();
    }

    /**
     * Grows the array if needed.
     * TimeComplexity Best case - O(n).
     * TimeComplexity Worst case - O(n).
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        E[] newArray = (E[]) new Object[elems.length * FACTOR];
        for (int i = 0; i < counter; i++){
            newArray[i] = elems[i];
        }
        elems = newArray;
    }

    /**
     * Move the elements of the array 1 position forward.
     * @param position The elements in this position and the ones forward are the elems that advance.
     */
    private void moveElementsForward(int position){
        for(int i = counter-1; i >= position; i--)
            elems[i + 1] = elems[i];
    }

    /**
     * Move the elements of the array 1 position backwards.
     * @param position The elements in this position and the ones backward are the elems that go back.
     */
    private void moveElementsBackwards(int position){
        for(int i = position ; i < size() - 1; i++)
            elems[i] = elems[i + 1];
    }

    /**
     * Returns the first element of the list.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getFirst() {
        if(isEmpty() )
            throw new NoSuchElementException();

        return elems[0];
    }

    /**
     * Returns the last element of the list.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getLast() {
        if(isEmpty() )
            throw new NoSuchElementException();

        return elems[counter - 1];
    }

    /**
     * Checks if the position is valid.
     * Range of valid positions: 0, ..., size()-1.
     * @param position - position of the element.
     * @return true if the position is invalid, false if it's valid.
     */
    private boolean invalidPosition(int position){
        return position < 0 || position >= size();
    }

    /**
     * Returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, get corresponds to getFirst.
     * If the specified position is size()-1, get corresponds to getLast.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @param position - position of element to be returned
     * @return element at position
     * @throws InvalidPositionException if position is not valid in the list
     */
    public E get(int position) {
        if(invalidPosition(position))
            throw new InvalidPositionException();

        return elems[position];
    }

    /**
     * Returns the position of the first occurrence of the specified element
     * in the list, if the list contains the element.
     * Otherwise, returns -1.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(n).
     * @param element - element to be searched in list
     * @return position of the first occurrence of the element in the list (or -1)
     */
    public int indexOf(E element) {
        for(int i = 0; i < size(); i++){
            if(element.equals(elems[i]) )
                return i;
        }
        return -1;
    }

    /**
     * Inserts the specified element at the first position in the list.
     * TimeComplexity Best case - O(n).
     * TimeComplexity Worst case - O(n).
     * @param element to be inserted
     */
    public void addFirst(E element) {
        if(isFull())
            resize();

        moveElementsForward(0);
        elems[0] = element;
        counter++;
    }

    /**
     * Inserts the specified element at the last position in the list.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(n).
     * @param element to be inserted
     */
    public void addLast(E element) {
        if(isFull())
            resize();

        elems[counter] = element;
        counter++;
    }

    /**
     * Inserts the specified element at the specified position in the list.
     * Range of valid positions: 0, ..., size().
     * If the specified position is 0, add corresponds to addFirst.
     * If the specified position is size(), add corresponds to addLast.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(n).
     * @param position - position where to insert element
     * @param element  - element to be inserted
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public void add(int position, E element) {
        if(position < 0 || position > size())
            throw new InvalidPositionException();


        if(position == 0)
            addFirst(element);
        else if(position == size() )
            addLast(element);
        else {
            if(isFull())
                resize();

            moveElementsForward(position);
            elems[position] = element;
            counter++;
        }
    }

    /**
     * Removes and returns the element at the first position in the list.
     * TimeComplexity Best case - O(n).
     * TimeComplexity Worst case - O(n).
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeFirst() {
        if(isEmpty() )
            throw new NoSuchElementException();

        E element = getFirst();
        elems[0] = null;
        moveElementsBackwards(0);
        counter--;
        return element;
    }

    /**
     * Removes and returns the element at the last position in the list.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @return element removed from the last position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeLast() {
        if(isEmpty() )
            throw new NoSuchElementException();

        E element = getLast();
        elems[size() - 1] = null;
        counter--;
        return element;
    }

    /**
     * Removes and returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, remove corresponds to removeFirst.
     * If the specified position is size()-1, remove corresponds to removeLast.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(n).
     * @param position - position of element to be removed
     * @return element removed at position
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public E remove(int position) {
        if(invalidPosition(position))
            throw new InvalidPositionException();

        E element = elems[position];
        if(position == 0)
            return removeFirst();
        if(position == size()-1)
            return removeLast();

        elems[position] = null;
        moveElementsBackwards(position);
        counter--;

        return element;
    }
}
