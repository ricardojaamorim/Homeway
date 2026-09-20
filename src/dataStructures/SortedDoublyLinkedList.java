package dataStructures;

import dataStructures.exceptions.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


/**
 * Sorted Doubly linked list Implementation
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
public class SortedDoublyLinkedList<E> implements SortedList<E> {

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
     * Comparator of elements.
     */
    private final Comparator<E> comparator;
    /**
     * Constructor of an empty sorted double linked list.
     * head and tail are initialized as null.
     * currentSize is initialized as 0.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     */
    public SortedDoublyLinkedList(Comparator<E> comparator) {
        head = null;
        tail = null;
        this.comparator = comparator;
        currentSize = 0;
    }

    /**
     * Returns true iff the list contains no elements.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @return true if list is empty
     */
    public boolean isEmpty() {
        return currentSize==0;
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
     * Returns an iterator of the elements in the list (in proper sequence).
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @return Iterator of the elements in the list
     */
    public Iterator<E> iterator() {
        return new DoublyIterator<>(head);
    }

    /**
     * Returns the first element of the list.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(1).
     * @return first element in the list
     * @throws NoSuchElementException - if size() == 0
     */
    public E getMin( ) {
        if(isEmpty() )
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
    public E getMax( ) {
        if(isEmpty() )
            throw new NoSuchElementException();

        return tail.getElement();
    }
    /**
     * Returns the first occurrence of the element equals to the given element in the list.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(n).
     * @return element in the list or null
     */
    public E get(E element) {
        if(isEmpty() )
            return null;

        DoublyListNode<E> node = head;
        for(int i = 0; i < size(); i++){
            E nodeElement = node.getElement();
            if(comparator.compare(element,nodeElement) == 0)
                return nodeElement;
            else
                node = node.getNext();
        }
        return null;
    }

    /**
     * Returns true iff the element exists in the list.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(n).
     * @param element to be found
     * @return true iff the element exists in the list.
     */
    public boolean contains(E element) {
        return get(element) != null;
    }

    /**
     * Inserts the specified element at the middle of the list, according to the natural order.
     * If there is an equal element, the new element is inserted after it.
     * @param element to be inserted.
     * @param newNode node being inserted.
     */
    private void addMiddle(E element, DoublyListNode<E> newNode){
        DoublyListNode<E> listNode = head;
        boolean endCycle = false;
        while(!endCycle && listNode != null) {
            if (comparator.compare(element, listNode.getElement()) < 0) {
                newNode.setPrevious(listNode.getPrevious());
                listNode.setPrevious(newNode);
                newNode.setNext(listNode);
                if (listNode == head)
                    head = newNode;
                else
                    newNode.getPrevious().setNext(newNode);
                endCycle = true;
            }
            listNode = listNode.getNext();
        }
    }

    /**
     * Inserts the specified element at the list, according to the natural order.
     * If there is an equal element, the new element is inserted after it.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(n).
     * @param element to be inserted
     */
    public void add(E element) {
        DoublyListNode<E> newNode = new DoublyListNode<>(element);
        if(isEmpty())
            tail = head = newNode;
        else if(comparator.compare(element,getMax() ) >= 0) {
            tail.setNext(newNode);
            newNode.setPrevious(tail);
            tail = newNode;
        }
        else
            addMiddle(element,newNode);
        currentSize ++;
    }


    /**
     * Removes and returns the first occurrence of the element equals to the given element in the list.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(n).
     * @param element to be removed.
     * @return element removed from the list or null if !belongs(element)
     */
    private E removeElement(E element){
        DoublyListNode<E> listNode = head;
        E elementToReturn = null;
        boolean endCycle = false;
        while (!endCycle && listNode != null) {
            if (comparator.compare(element, listNode.getElement()) == 0) {
                if (listNode == head) {
                    listNode.getNext().setPrevious(null);
                    head = listNode.getNext();
                } else if (listNode == tail) {
                    listNode.getPrevious().setNext(null);
                    tail = listNode.getPrevious();
                } else {
                    listNode.getPrevious().setNext(listNode.getNext());
                    listNode.getNext().setPrevious(listNode.getPrevious());
                }
                elementToReturn = listNode.getElement();
                endCycle = true;
            }
            listNode = listNode.getNext();
        }
        return elementToReturn;
    }

    /**
     * Removes and returns the first occurrence of the element equals to the given element in the list.
     * TimeComplexity Best case - O(1).
     * TimeComplexity Worst case - O(n).
     * @return element removed from the list or null if !belongs(element)
     */
    public E remove(E element) {
        E elementToReturn = null;
        if(size() == 1 && comparator.compare(element,getMin() ) == 0){
            elementToReturn = getMin();
            head = tail = null;
        }
        else
            elementToReturn = removeElement(element);

        if(elementToReturn != null){ //size is only decremented if a element is removed.
            currentSize--;
            return elementToReturn;
        }
        return null;
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
            add(element);
        }
    }

}
