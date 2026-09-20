package dataStructures;

/**
 * Binary Tree Node
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
class BTNode<E> implements Node<E> {
    // Element stored in the node.
    private E element;

    // (Pointer to) the father.
    private Node<E> parent;

    // (Pointer to) the left child.
    private Node<E> leftChild;

    // (Pointer to) the right child.
    private Node<E> rightChild;

    /**
     * Constructor
     * @param elem
     */
    BTNode(E elem){
        this(elem,null,null,null);
    }

    /**
     * Constructor
     * @param elem
     * @param parent
     */
    BTNode(E elem, BTNode<E> parent) {
        this(elem,parent,null,null);
    }
    /**
     * Constructor
     * @param elem
     * @param parent
     * @param leftChild
     * @param rightChild
     */
    BTNode(E elem, BTNode<E> parent, BTNode<E> leftChild, BTNode<E> rightChild){
        element = elem;
        this.parent = parent;
        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }

    /**
     *  Returns the element of the node
     * @return
     */
    public E getElement() {
        return element;
    }
    /**
     * Returns the left son of node
     * @return
     */
    public Node<E> getLeftChild(){
        return leftChild;
    }
    /**
     * Returns the right son of node
     * @return
     */
    public Node<E> getRightChild(){
        return rightChild;
    }
    /**
     * Returns the parent of node
     * @return
     */
    public Node<E> getParent(){
        return parent;
    }

    /**
     * Returns true if node n does not have any children.
     * @return
     */
    boolean isLeaf() {
        return getLeftChild()== null && getRightChild()==null;
    }

    /**
     * Update the element
     * @param elem
     */
    public void setElement(E elem) {
        element=elem;
    }

    /**
     * Update the left child
     * @param node
     */
    public void setLeftChild(Node<E> node) {
        leftChild=node;
    }

    /**
     * Update the right child
     * @param node
     */
    public void setRightChild(Node<E> node) {
        rightChild=node;
    }

    /**
     * Update the parent
     * @param node
     */
    public void setParent(Node<E> node) {
        parent=node;
    }

    /**
     * Returns true if is the root
     */
    boolean isRoot() {
        return getParent()==null;
    }

    /**
     * Returns the height of the subtree rooted at this node.
     * @Time_Complexity_Best_case  - O(1) is a leaf.
     * @Time_Complexity_Worst_case - O(m) m being the nodes below this node.
     */

    public int getHeight() {
        if (isLeaf())
            return 0;
        BTNode<E> left= (BTNode<E>)getLeftChild();
        BTNode<E> right= (BTNode<E>)getRightChild();
        if (left==null)
            return 1+right.getHeight();
        if (right == null)
            return 1+left.getHeight();
        return 1 + Math.max(left.getHeight(),right.getHeight());
    }

    /**
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(h) being h de tree height.
     * h is O(log n) for AVL and RB ,h is O(n) in this case(worst case) for BST.
     * @return
     */
    BTNode<E> furtherLeftElement() {
        BTNode<E> current = this;
        while(current.getLeftChild() != null )
            current = (BTNode<E>)current.getLeftChild();
        return current;
    }

    /**
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(h) being h de tree height.
     * h is O(log n) for AVL and RB ,h is O(n) in this case(worst case) for BST.
     * @return
     */
    BTNode<E> furtherRightElement() {
        BTNode<E> current = this;
        while(current.getRightChild() != null )
            current = (BTNode<E>)current.getRightChild();
        return current;
    }


    /**
     * Returns true if this is the right child of his parent.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     * @return true if this is the right child of his parent, false otherwise.
     */
    protected boolean isRightChild(){
        if(isRoot())
            return false;

        return ((BTNode<E>)getParent() ).getRightChild() == this;
    }

    /**
     * Returns true if this is the left child of his parent.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     * @return true if this is the left child of his parent, false otherwise.
     */
    protected boolean isLeftChild(){
        if(isRoot())
            return false;

        return ((BTNode<E>)getParent() ).getLeftChild() == this;
    }

    /**
     * Returns the uncle of the node, or null if he doesn't exist.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     * @return the uncle of the node, or null if he doesn't exist.
     */
    protected BTNode<E> getUncle(){
        BTNode<E> parent = (BTNode<E>)getParent();
        if(parent == null)
            return null;

        BTNode<E> grandpa = (BTNode<E>) parent.getParent();
        if(grandpa == null)
            return null;

        if(parent.isLeftChild())
            return (BTNode<E>)grandpa.getRightChild();
        else
            return (BTNode<E>)grandpa.getLeftChild();
    }

    /**
     * Returns the brother of the node, or null if he doesn't exist.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     * @return the brother of the node, or null if he doesn't exist.
     */
    protected BTNode<E> getBrother(){
        BTNode<E> parent = (BTNode<E>)getParent();
        if(parent == null)
            return null;

        if(isLeftChild())
            return (BTNode<E>)parent.getRightChild();
        else
            return (BTNode<E>)parent.getLeftChild();
    }

}
