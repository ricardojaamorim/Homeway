/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package dataStructures;

class RBNode<E> extends BTNode<E> { //package private just like the other nodes.

    /**
     * Indicates if the node is red.
     * @RED - true.
     * @BLACK - false.
     */
    private boolean isRed;

    /**
     * Constructor without node's relationships.
     * @param elem Element of the node.
     */
    public RBNode(E elem) {
        super(elem);
        isRed = true;
    }

    /**
     * Constructor with only father relationship.
     * @param element Node's element.
     * @param parent Node's parent.
     */
    public RBNode( E element, RBNode<E> parent){
        super(element, parent,null, null);
        isRed = true;
    }

    /**
     * Constructor with all relationships.
     * @param element Node's element.
     * @param parent Node's parent.
     * @param left Node's left child.
     * @param right Node's right child.
     */
    public RBNode( E element, RBNode<E> parent,
                   RBNode<E> left, RBNode<E> right ){
        super(element,parent,left,right);
        isRed = true;
    }


    /**
     * Changes the node's colour to black.
     */
    public void setBlackColour(){
        isRed = false;
    }


    /**
     * Changes the node's colour to red.
     */
    public void setRedColour(){
        isRed = true;
    }

    /**
     * Returns true if the node is red.
     * @return true if the node is red,false otherwise.
     */
    public boolean isRed(){
        return isRed;
    }



    /**
     * Returns true if a node is unbalanced.
     * A RBNode is unbalanced if it's colour is red and his father is also red.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     * @return  true if a node is unbalanced, false otherwise.
     */
    protected boolean isUnbalanced(){
        RBNode<E> parent = (RBNode<E>)super.getParent();
        if(parent == null)
            return false;

        return isRed && parent.isRed;
    }

    /**
     * Returns true if the Uncle of the node is red.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     * @return true if the Uncle of the node is red.
     */
    protected boolean uncleIsRed(){
        RBNode<E> uncle = (RBNode<E>)super.getUncle();
        if(uncle == null)
            return false;

        return uncle.isRed();
    }


    /**
     * Returns true if it's a case LL of unbalancement when uncle is black
     * @UnbalacedCaseLL when the node is a left son and his father is a left son.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     * @pre getParent() != null
     * @return true if it's a case LL of unbalancement when uncle is black, false otherwise.
     */
    protected boolean uncleIsBlackCaseLL(){
        if(!isLeftChild())
            return false;

        RBNode<E> parent = (RBNode<E>)super.getParent();
        return parent.isLeftChild();
    }


    /**
     * Returns true if it's a case RR of unbalancement when uncle is black
     * @UnbalacedCaseRR when the node is a right son and his father is a right son.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     * @pre getParent() != null
     * @return true if it's a case RR of unbalancement when uncle is black, false otherwise.
     */
    protected boolean uncleIsBlackCaseRR(){
        if(!isRightChild())
            return false;

        RBNode<E> parent = (RBNode<E>)super.getParent();
        return parent.isRightChild();
    }

    /**
     * Returns true if it's a case LR of unbalancement when uncle is black
     * @UnbalacedCaseLR when the node is a left son and his father is a right son.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     * @pre getParent() != null
     * @return true if it's a case LR of unbalancement when uncle is black, false otherwise.
     */
    protected boolean uncleIsBlackCaseLR(){
        if(!isRightChild())
            return false;

        RBNode<E> parent = (RBNode<E>)super.getParent();
        return parent.isLeftChild();
    }

    /**
     * Returns true if it's a case RL of unbalancement when uncle is black
     * @UnbalacedCaseRL when the node is a right son and his father is a left son.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(1).
     * @pre getParent() != null
     * @return true if it's a case RL of unbalancement when uncle is black, false otherwise.
     */
    protected boolean uncleIsBlackCaseRL(){
        if(!isLeftChild())
            return false;

        RBNode<E> parent = (RBNode<E>)super.getParent();
        return parent.isRightChild();
    }




}
