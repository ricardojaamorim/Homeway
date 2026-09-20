/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package dataStructures;


import dataStructures.exceptions.NoSuchElementException;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Mix of a map and list, that guarantees a constant removal of its elements.
 * @param <E> Element of the list.
 */
public class DoublyLinkedListHashMap<E> implements ListHashMap<E> {

    /**
     * Map that contains the nodes.
     */
    private transient Map<E,DoublyListNode<E>> map;

    /**
     *  Node at the head of the list.
     */
    private transient DoublyListNode<E> head;

    /**
     *  Node at the head of the list.
     */
    private transient DoublyListNode<E> tail;

    /**
     * Number of elements.
     */
    private transient int currentSize;

    /**
     * Constructor of LinkedHashMap.
     */
    public DoublyLinkedListHashMap(int capacity){
       map = new SepChainHashTable<>(capacity);
       head = null;
       tail = null;
       currentSize = 0;
    }



    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

    @Override
    public int size() {
        return currentSize;
    }


    //Time_Complexity_Best_case  - O(1).
    // Time_Complexity_Worst_case - O(1).
    @Override
    public void add(E element){
            DoublyListNode<E> newNode = new DoublyListNode<>(element);
            DoublyListNode<E> previousTail = tail;

            tail = newNode;
            tail.setPrevious(previousTail);
            if (previousTail != null)
                previousTail.setNext(tail);

            if (head == null)
                head = tail;

            map.put(element,newNode);

            currentSize ++;
    }

    /**
     * Removes the first node of the list and returns it's value.
     * @return the removed node value.
     */
    private E removeFirst( ) {

        E returnValue = head.getElement();

        head = head.getNext();
        if(head != null)
            head.setPrevious(null);

        currentSize--;
        if(isEmpty())
            tail = head;

        return returnValue;
    }


    /**
     * Removes the last node of the list and returns it's value.
     * @return the removed node value.
     */
    private E removeLast( ) {
        E returnValue = tail.getElement();

        tail = tail.getPrevious();
        if(tail != null)
            tail.setNext(null);

        currentSize--;
        if(isEmpty())
            head = tail;

        return returnValue;
    }

    //Time_Complexity_Best_case  - O(1).
    // Time_Complexity_Worst_case - O(1).
    @Override
    public E remove(E element){
        if(size() == 0)
            throw new NoSuchElementException();

        DoublyListNode<E> removedNode = map.remove(element);
        if(removedNode == null)
            return null;

        if(removedNode == head )
            return removeFirst();

        if(removedNode == tail)
            return removeLast();

        else {
            (removedNode.getPrevious()).setNext(removedNode.getNext());
            (removedNode.getNext()).setPrevious(removedNode.getPrevious());
            currentSize--;
            return removedNode.getElement();
        }
    }


    @Override
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    @Override
    public TwoWayIterator<E> twoWayiterator() {
        return new TwoWayDoublyIterator<>(head, tail);
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
        map = new SepChainHashTable<>(size * 2);
        for (int i = 0; i < size; i++) {
            @SuppressWarnings("unchecked")
            E element = (E) ois.readObject();
            add(element);
        }
    }






}
