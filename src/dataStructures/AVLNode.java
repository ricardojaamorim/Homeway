package dataStructures;
/**
 * AVL Tree Node
 * @author AED  Team
 * @version 1.0
 * @param <E> Generic Element
 */
class AVLNode<E> extends BTNode<E> {
    // Height of the node
    protected int height;

    public AVLNode(E elem) {
        super(elem);
        height=0;
    }

    public AVLNode( E element, AVLNode<E> parent,
                    AVLNode<E> left, AVLNode<E> right ){
        super(element,parent,left,right);
        height = 0;
    }
    public AVLNode( E element, AVLNode<E> parent){
        super(element, parent,null, null);
        height= 0;
    }

    /**
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     * @param no
     * @return
     */
    private int height(AVLNode<E> no) {
        if (no==null)	return -1;
        return no.getHeight();
    }

    /**
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     * Update the left child and height
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     * @param node
     */
    public void setLeftChild(AVLNode<E> node) {
        super.setLeftChild(node);
        if (node != null)
            node.setParent(this);
        updateHeight();
    }

    /**
     * Update the right child and height
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     * @param node
     */
    public void setRightChild(AVLNode<E> node) {
        super.setRightChild(node);
        if (node != null)
            node.setParent(this);
        updateHeight();
    }

    /**
     * Updates the new height.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     */
    protected void updateHeight(){
        int leftHeight = height( (AVLNode<E>)getLeftChild());
        int rightHeight = height((AVLNode<E>)getRightChild());
        height = Math.max(leftHeight, rightHeight) + 1;
    }



    /**
     * Returns true if the subtrees of this node are unbalanced.
     * Subtrees are unbalanced if their height(int) difference is greater than 1.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     * @return true if the subtrees are unbalanced, false otherwise.
     */
    protected boolean isUnbalanced(){
        return Math.abs(height((AVLNode<E>)getLeftChild()) -
                height((AVLNode<E>)getRightChild()) ) >= 2;
    }





    /**
     * Returns true if the unbalancement is from the left.
     * @param left left node of the unbalanced node.
     * @param right right node of the unbalanced node.
     * @return true if the unbalancement is from the left, false otherwise.
     */
    private boolean checkL(AVLNode<E> left,  AVLNode<E> right){
        if(left == null)
            return false;

        return height(left) > height(right);
    }


    /**
     * Returns true if the unbalancement is from the right.
     * @param left left son of the unbalanced node.
     * @param right right son of the unbalanced node.
     * @return true if the unbalancement is from the right, false otherwise.
     */
    private boolean checkR(AVLNode<E> left,  AVLNode<E> right){
        if(right == null)
            return false;

        return height(right) > height(left);
    }

    /**
     * Returns true if it's a case LL unbalancement.
     * @UnbalacedCaseLL the node has 2 left childs in a row.
     * @return true if it's a case LL unbalancement,false otherwise.
     */
    protected boolean isUnbalancedCaseLL(){
        AVLNode<E> left = (AVLNode<E>)getLeftChild();
        AVLNode<E> right = (AVLNode<E>)getRightChild();

        if(!checkL(left,right))
            return false;

        AVLNode<E> left2 = (AVLNode<E>)left.getLeftChild();
        AVLNode<E> right2 = (AVLNode<E>)left.getRightChild();

        return checkL(left2,right2);
    }

    /**
     * Returns true if it's a case RR unbalancement.
     * @UnbalacedCaseRR the node has 2 right childs in a row.
     * @return true if it's a case RR unbalancement,false otherwise.
     */
    protected boolean isUnbalancedCaseRR(){
        AVLNode<E> left = (AVLNode<E>)getLeftChild();
        AVLNode<E> right = (AVLNode<E>)getRightChild();

        if(!checkR(left,right))
            return false;

        AVLNode<E> left2 = (AVLNode<E>)right.getLeftChild();
        AVLNode<E> right2 = (AVLNode<E>)right.getRightChild();

        return checkR(left2,right2);
    }

    /**
     * Returns true if it's a case LR unbalancement.
     * @UnbalacedCaseLR the node has a left child, and that child has a right child.
     * @return true if it's a case LR unbalancement,false otherwise.
     */
    protected boolean isUnbalancedCaseLR(){
        AVLNode<E> left = (AVLNode<E>)getLeftChild();
        AVLNode<E> right = (AVLNode<E>)getRightChild();

        if(!checkL(left,right))
            return false;

        AVLNode<E> left2 = (AVLNode<E>)left.getLeftChild();
        AVLNode<E> right2 = (AVLNode<E>)left.getRightChild();

        return checkR(left2,right2);
    }

    /**
     * Returns true if it's a case RL unbalancement.
     * @UnbalacedCaseRL the node has a right child, and that child has a left child.
     * @return true if it's a case RL unbalancement,false otherwise.
     */
    protected boolean isUnbalancedCaseRL(){
        AVLNode<E> left = (AVLNode<E>)getLeftChild();
        AVLNode<E> right = (AVLNode<E>)getRightChild();

        if(!checkR(left,right))
            return false;

        AVLNode<E> left2 = (AVLNode<E>)right.getLeftChild();
        AVLNode<E> right2 = (AVLNode<E>)right.getRightChild();

        return checkL(left2,right2);
    }



}
