package dataStructures;

import dataStructures.exceptions.InvalidPositionException;
import dataStructures.exceptions.NoSuchElementException;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;

/**
 * Implementation of Doubly Linked List
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class DoublyLinkedList<E> implements TwoWayList<E> {
    /**
     *  Node at the head of the list.
     */
    private transient DoublyListNode<E> head;
    /**
     * Node at the tail of the list.
     */
    private transient DoublyListNode<E> tail;
    /**
     * Number of elements in the list.
     */
    private transient int currentSize;

    /**
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * Constructor of an empty double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     */
    public DoublyLinkedList( ) {
        head = null;
        tail = null;
        currentSize = 0;
    }

    /**
     * Returns true iff the list contains no elements.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return currentSize == 0;
    }

    /**
     * Returns the number of elements in the list.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @return number of elements in the list
     */

    public int size() {
        return currentSize;
    }

    /**
     * Returns a two-way iterator of the elements in the list.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @return Two-Way Iterator of the elements in the list
     */

    public TwoWayIterator<E> twoWayiterator() {
        return new TwoWayDoublyIterator<>(head, tail);
    }
    /**
     * Returns an iterator of the elements in the list (in proper sequence).
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Inserts the element at the first position in the list.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @param element - Element to be inserted
     */
    public void addFirst( E element ) {
        DoublyListNode<E> previousHead = head;
        head = new DoublyListNode<E>(element);
        head.setNext(previousHead);
        if(previousHead != null)
            previousHead.setPrevious(head);
        currentSize++;
        if(size() == 1)
            tail = head;
    }

    /**
     * Inserts the element at the last position in the list.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @param element - Element to be inserted
     */
    public void addLast( E element ) {
        DoublyListNode<E> previousTail = tail;
        tail = new DoublyListNode<E>(element);
        tail.setPrevious(previousTail);
        if(previousTail != null)
            previousTail.setNext(tail);
        currentSize++;
        if(size() == 1)
            head = tail;
    }

    /**
     * Returns the first element of the list.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getFirst( ) {
        if(size() == 0)
            throw new NoSuchElementException();
        return head.getElement();
    }

    /**
     * Returns the last element of the list.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @return last element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getLast( ) {
        if(size() == 0)
            throw new NoSuchElementException();
        return tail.getElement();
    }



    /**
     * Returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, get corresponds to getFirst.
     * If the specified position is size()-1, get corresponds to getLast.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(n).
     * @param position - position of element to be returned
     * @return element at position
     * @throws InvalidPositionException if position is not valid in the list
     */
    public E get( int position ) {
        if(position < 0 || position >= size() )
            throw new InvalidPositionException();

        if(position == 0)
            return getFirst();
        if(position == size()-1)
            return getLast();

        DoublyListNode<E> node = head;
        for(int i = 0; i < position; i++){
            node = node.getNext();
        }
        return node.getElement();
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
    public int indexOf( E element ) {
        if(isEmpty())
            return -1;

        DoublyListNode<E> node = head;
        for(int i = 0; i < size() ; i++){
            if(node.getElement().equals(element) )
                return i;
            node = node.getNext();
        }
        return -1;
    }

    /**
     * inserts a node in the middle of the list.
     * @param position Insert position.
     * @param element Element of the node.
     */
    private void addMiddle(int position,E element){
        DoublyListNode<E> node = head;
        for (int i = 0; i < position; i++) {
            node = node.getNext();
        }
        DoublyListNode<E> newNode = new DoublyListNode<>(element);

        newNode.setNext(node);
        newNode.setPrevious(node.getPrevious() );

        node.setPrevious(newNode);
        newNode.getPrevious().setNext(newNode);

        currentSize++;
    }


    /**
     * Inserts the specified element at the specified position in the list.
     * Range of valid positions: 0, ..., size().
     * If the specified position is 0, add corresponds to addFirst.
     * If the specified position is size(), add corresponds to addLast.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(n).
     * @param position - position where to insert element
     * @param element - element to be inserted
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public void add( int position, E element ) {
        if(position < 0 || position > size())
            throw new InvalidPositionException();

        if(position == 0)
            addFirst(element);
        else if(position == size())
            addLast(element);
        else
            addMiddle(position,element);
    }

    /**
     * Removes and returns the element at the first position in the list.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @return element removed from the first position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeFirst( ) {
        if(size() == 0)
            throw new NoSuchElementException();

        E element = head.getElement();
        head = head.getNext();
        if(head != null)
            head.setPrevious(null);
        currentSize--;
        if(isEmpty())
            tail = head;
        return element;
    }

    /**
     * Removes and returns the element at the last position in the list.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @return element removed from the last position of the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E removeLast( ) {
        if(size() == 0)
            throw new NoSuchElementException();

        E element = tail.getElement();
        tail = tail.getPrevious();
        if(tail != null)
            tail.setNext(null);
        currentSize--;
        if(isEmpty())
            head = tail;
        return element;
    }

    private E removeMiddle(int position) {
        DoublyListNode<E> node = null;
        node = head;
        for (int i = 0; i < position; i++) {
            node = node.getNext();
        }
        (node.getPrevious()).setNext(node.getNext());
        (node.getNext()).setPrevious(node.getPrevious());
        currentSize--;
        return node.getElement();
    }


    /**
     *  Removes and returns the element at the specified position in the list.
     * Range of valid positions: 0, ..., size()-1.
     * If the specified position is 0, remove corresponds to removeFirst.
     * If the specified position is size()-1, remove corresponds to removeLast.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(n).
     * @param position - position of element to be removed
     * @return element removed at position
     * @throws InvalidPositionException - if position is not valid in the list
     */
    public E remove( int position ) {
        if(position < 0 || position >= size())
            throw new InvalidPositionException();

        if(position == 0)
            return removeFirst();
        else if(position == size()-1 )
            return removeLast();
        else
            return removeMiddle(position);
    }

    /**
     * Manual Serialization.
     * @param oos Object that does the serialization.
     * @throws IOException
     */
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
        oos.writeInt(currentSize);
        DoublyListNode<E> node = head;
        while (node != null) {
            oos.writeObject(node.getElement());
            node = node.getNext();
        }
        oos.flush();
    }

    /**
     * Manual Deserialization.
     * @param ois Object that does the deserialization.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        int size = ois.readInt();
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            E element = (E) ois.readObject();
            addLast(element);
        }
    }

}
