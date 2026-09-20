package dataStructures;

import dataStructures.exceptions.NoSuchElementException;

/**
 * In-order Binary Tree iterator
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
public class InOrderIterator<E> implements Iterator<E> {

    /**
     * Node with the current element
     */
    private BTNode<E> next;

    /**
     * Root Node
     */
    private BTNode<E> root;

    /**
     *
     * @param root
     */
    public  InOrderIterator(BTNode<E> root) {
        this.root=root;
        rewind();
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     * and is O(n) in this case (worst case) for BST trees
     * @return true iff the iteration has more elements
     */
    @Override
    public boolean hasNext() {
        return next!=null;
    }

    /**
     * Returns the next element in the iteration.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(h) h being the tree height, h is O(log n) for AVL and RB trees
     * and is O(n) in this case (worst case) for BST trees.
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    @Override
    public E next() {
        if (!hasNext())
            throw new NoSuchElementException();
        E elem=next.getElement();
        advance();
        return elem;
    }

    /**
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(h) h being the tree height, h is O(log n) for AVL and RB trees
     * and is O(n) in this case (worst case) for BST trees.
     */
    private void advance() {
        if(next.getRightChild() != null)
            next = ((BTNode<E>)next.getRightChild() ).furtherLeftElement();
        else{
            BTNode<E> parent = (BTNode<E>)next.getParent();
            while(parent != null && next.isRightChild()){
                next = parent;
                parent = (BTNode<E>)parent.getParent();
            }
            next = parent;
        }
    }


    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(h) h being the tree height, h is O(log n) for AVL and RB trees
     * and is O(n) in this case (worst case) for BST trees
     */
    public void rewind() {
        if (root==null)
            next=null;
        else
            next=root.furtherLeftElement();
    }
}
