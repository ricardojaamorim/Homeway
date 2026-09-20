package dataStructures;

import java.io.Serializable;

/**
 * Double List Node Implementation
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 *
 */
class DoublyListNode<E> implements Serializable {
        /**
         * Serial Version UID of the Class
         */
        static final long serialVersionUID = 0L;

        /**
         * Element stored in the node.
         */
        private E element;

        /**
         * (Pointer to) the previous node.
         */
        private DoublyListNode<E> previous;

        /**
         * (Pointer to) the next node.
         */
        private DoublyListNode<E> next;

        /**
         * TimeComplexity Best case - O(1).
         * TimeComplexity Worst case - O(1).
         * @param theElement - The element to be contained in the node
         * @param thePrevious - the previous node
         * @param theNext - the next node
         */
        public DoublyListNode(E theElement, DoublyListNode<E> thePrevious,
                              DoublyListNode<E> theNext ) {
                element = theElement;
                previous = thePrevious;
                next = theNext;
        }

        /**
         * TimeComplexity Best case - O(1).
         * TimeComplexity Worst case - O(1).
         * @param theElement to be contained in the node
         */
        public DoublyListNode(E theElement ) {
                element = theElement;
        }

        /**
         * TimeComplexity Best case - O(1).
         * TimeComplexity Worst case - O(1).
         * @return the element contained in the node
         */
        public E getElement( ) {
                return element;
        }

        /**
         * TimeComplexity Best case - O(1).
         * TimeComplexity Worst case - O(1).
         * @return the previous node
         */
        public DoublyListNode<E> getPrevious( ) {
                return previous;
        }

        /**
         * TimeComplexity Best case - O(1).
         * TimeComplexity Worst case - O(1).
         * @return the next node
         */
        public DoublyListNode<E> getNext( ) {
                return next;
        }

        /**
         * TimeComplexity Best case - O(1).
         * TimeComplexity Worst case - O(1).
         * @param newElement - New element to replace the current element
         */
        public void setElement( E newElement ) {
                element = newElement;
        }

        /**
         * TimeComplexity Best case - O(1).
         * TimeComplexity Worst case - O(1).
         * @param newPrevious - node to replace the current previous node
         */
        public void setPrevious( DoublyListNode<E> newPrevious ) {
                previous = newPrevious;
        }

        /**
         * TimeComplexity Best case - O(1).
         * TimeComplexity Worst case - O(1).
         * @param newNext - node to replace the next node
         */
        public void setNext( DoublyListNode<E> newNext ) {
                next = newNext;
        }
}