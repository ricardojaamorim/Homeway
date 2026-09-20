package dataStructures;

import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Binary Tree
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
abstract class BTree<E> extends Tree<E> {


    /**
     * Returns the height of the tree.
     * @Time_Complexity_Best_case  - O(n).
     * @Time_Complexity_Worst_case - O(n).
     */
    public int getHeight() {
        if(isEmpty())
            return -1;
        return ((BTNode<E>)root).getHeight();
    }

    /**
     * Return the further left node of the tree
     * @Time_Complexity_Best_case  - O(1) for BSt O(h) for AVL and RB
     * h being O(log n).
     * @Time_Complexity_Worst_case - O(h) h being O(n) in this case(worst case) for BST
     * h being O(log n) for AVL and RB.
     * @return
     */
    BTNode<E> furtherLeftElement() {
        return ((BTNode<E>)root).furtherLeftElement();

    }

    /**
     * Return the further right node of the tree
     * @Time_Complexity_Best_case  - O(1) for BSt O(h) for AVL and RB
     * h being O(log n).
     * @Time_Complexity_Worst_case - O(h) h being O(n) in this case(worst case) for BST
     * h being O(log n) for AVL and RB.
     * @return
     */
    BTNode<E> furtherRightElement() {
        return ((BTNode<E>)root).furtherRightElement();
    }

}
