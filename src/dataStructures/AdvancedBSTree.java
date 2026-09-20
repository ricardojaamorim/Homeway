package dataStructures;
/**
 * Advanced Binary Search Tree
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
abstract class AdvancedBSTree <K extends Comparable<K>,V> extends BSTSortedMap<K,V>{

    /**
     * Does the operations that both rotations do equally
     * (replacing the root of the rotation (to y) and setting the parent of the savedSubTree(to z) ).
     * @param z root of the rotation.
     * @param y child of z.
     * @param savedSubTree subTree that has to be saved to be attached to z so we don't lose elements.
     */
    private void LeftAndRightSharedOperations(BTNode<Entry<K,V>> z,
                                              BTNode<Entry<K, V>> y,  BTNode<Entry<K, V>> savedSubTree){
        y.setParent(z.getParent());
        if(y.isRoot())
            root = y;
        else {
            if (z.isLeftChild())
                ((BTNode<Entry<K, V>>) z.getParent()).setLeftChild(y);
            else
                ((BTNode<Entry<K, V>>) z.getParent()).setRightChild(y);
        }
        z.setParent(y);
        if(savedSubTree != null)
            savedSubTree.setParent(z);
    }

    /**
     * Performs a single left rotation rooted at z node.
     * Node y was a  right  child  of z before the  rotation,
     * then z becomes the left child of y after the rotation.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     * @param z - root of the rotation
     * @pre: z has a right child
     */
    protected void rotateLeft( BTNode<Entry<K,V>> z){
        BTNode<Entry<K, V>> y = (BTNode<Entry<K, V>>) z.getRightChild();
        BTNode<Entry<K, V>> savedSubTree = (BTNode<Entry<K, V>>) y.getLeftChild();

        LeftAndRightSharedOperations(z,y,savedSubTree);

        z.setRightChild(savedSubTree);

        y.setLeftChild(z);
    }


    /**
     * Performs a single right rotation rooted at z node.
     * Node y was a left  child  of z before the  rotation,
     * then z becomes the right child of y after the rotation.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     * @param z - root of the rotation
     * @pre: z has a left child
     */
    protected void rotateRight( BTNode<Entry<K,V>> z){
        BTNode<Entry<K, V>> y = (BTNode<Entry<K, V>>) z.getLeftChild();
        BTNode<Entry<K, V>> savedSubTree = (BTNode<Entry<K, V>>) y.getRightChild();

        LeftAndRightSharedOperations(z,y,savedSubTree);

        z.setLeftChild(savedSubTree);

        y.setRightChild(z);
    }


    /**
     * Does the restructure operation for AVL nodes.
     * @param x - root of the rotation.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     * @return the new root of the restructured subtree.
     */
    private BTNode<Entry<K,V>> restructureAVL (BTNode<Entry<K,V>> x){
        AVLNode<Entry<K,V>> avlX = (AVLNode<Entry<K,V>>) x;
        if(avlX.isUnbalancedCaseLL()){
            rotateRight(x);
            return (BTNode<Entry<K,V>>)x.getParent();
        }
        if(avlX.isUnbalancedCaseRR()) {
            rotateLeft(x);
        }
        else {
            if (avlX.isUnbalancedCaseLR()) {
                BTNode<Entry<K,V>> xChild = (BTNode<Entry<K, V>>) x.getLeftChild();
                rotateLeft(xChild);
                rotateRight(x);
            }
            else { //isUnbalancedCaseRL()
                BTNode<Entry<K,V>> xChild = (BTNode<Entry<K, V>>) x.getRightChild();
                rotateRight(xChild);
                rotateLeft(x);
            }
        }
        return (BTNode<Entry<K,V>>)x.getParent();
    }

    /**
     * Does the restructure operation for RB nodes when uncle is not red.
     * @param x - root of the rotation(for this method the root will be the unbalanced node
     * which for rb is the first node to go against the rule against two red nodes in a row(bottom node between the 2)).
     * @return
     */
    private BTNode<Entry<K,V>> restructureRBUncleNotRed(BTNode<Entry<K,V>> x) {
        RBNode<Entry<K,V>> rbX = (RBNode<Entry<K,V>>) x;
        RBNode<Entry<K,V>> parent = (RBNode<Entry<K,V>>) x.getParent();
        RBNode<Entry<K,V>> grandpa = (RBNode<Entry<K,V>>) parent.getParent();

        if(rbX.uncleIsBlackCaseLL())
            rotateRight(grandpa);
        else if(rbX.uncleIsBlackCaseRR())
            rotateLeft(grandpa);
        else {
            if (rbX.uncleIsBlackCaseLR()) {
                rotateLeft(parent);
                rotateRight(grandpa);
            }
            else { //uncleIsBlackCaseRL()
                rotateRight(parent);
                rotateLeft(grandpa);
            }
            grandpa.setRedColour();
            rbX.setBlackColour();
            return rbX;
        }
        parent.setBlackColour();
        grandpa.setRedColour();
        return parent;
    }



    /**
     * Does the restructure operation for RB nodes.
     * If both parent and uncle are red, continues to check for unbalances after a restructure in the new root of rotation
     * @param x - root of the rotation(for this method the root will be the unbalanced node
     * which for rb is the first node to go against the rule against two red nodes in a row(bottom node between the 2)).
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(h) h being the tree height, h is O(log n), because if the uncle
     * is red the method continues but with nodes above.
     * @return the new root of the restructured subtree.
     */
    private BTNode<Entry<K,V>> restructureRB (BTNode<Entry<K,V>> x){
        RBNode<Entry<K,V>> rbX = (RBNode<Entry<K,V>>) x;

        if(!rbX.isUnbalanced())
            return x;

        if(rbX.uncleIsRed()){
            RBNode<Entry<K,V>> parent = (RBNode<Entry<K,V>>) x.getParent();
            RBNode<Entry<K,V>> grandpa = (RBNode<Entry<K,V>>) parent.getParent();

            parent.setBlackColour();
            ((RBNode<Entry<K,V>>) rbX.getUncle()).setBlackColour();
            grandpa.setRedColour();

            return restructureRB(grandpa);
        }
        else
            return restructureRBUncleNotRed(x);
    }

    /**
     * Performs a tri-node restructuring (a single or double rotation rooted at X node).
     * Assumes the nodes are in one of following configurations:
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(h) h being the tree height, h is O(log n).
     * @param x - root of the rotation
     * <pre>
     *          z=c       z=c        z=a         z=a
     *          /  \      /  \       /  \        /  \
     *        y=b  t4   y=a  t4    t1  y=c     t1  y=b
     *       /  \      /  \           /  \         /  \
     *     x=a  t3    t1 x=b        x=b  t4       t2 x=c
     *    /  \          /  \       /  \             /  \
     *   t1  t2        t2  t3     t2  t3           t3  t4
     * </pre>
     * @return the new root of the restructured subtree
     */
    protected BTNode<Entry<K,V>> restructure (BTNode<Entry<K,V>> x) {
        if(x instanceof AVLNode<Entry<K,V>>)
            return restructureAVL(x);
        else if(x instanceof RBNode<Entry<K,V>>)
            return restructureRB(x);

        return null;
    }

}
