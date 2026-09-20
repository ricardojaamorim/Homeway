/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */


package dataStructures;


public class RBSortedMap <K extends Comparable<K>,V> extends AdvancedBSTree<K,V>{

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     * @Time_Complexity_Best_case  - O(h) h being the tree height which is O(log n).
     * @Time_Complexity_Worst_case - O(h) h being the tree height which is O(log n).
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    public V put(K key, V value) {
        V returnValue = null;
        Entry<K,V> newEntry = new Entry<>(key,value);
        RBNode<Entry<K,V>> newNode = new RBNode<>(newEntry);
        returnValue = super.putNode(key,newNode);
        if(returnValue != null)
            return returnValue;
        else{
            if(newNode.isUnbalanced())
                restructure(newNode);

            ((RBNode<Entry<K,V>>) root).setBlackColour();
            return null;
        }
    }

    /**
     * Colour the new root of the subtree with the previous root color.
     * @param newRoot New root of the subtree.
     * @param oldRoot OLd root of the subtree.
     */
    private void colourNewRoot(RBNode<Entry<K,V>> newRoot,RBNode<Entry<K,V>> oldRoot){
        if(oldRoot.isRed())
            newRoot.setRedColour();
        else
            newRoot.setBlackColour();
    }

    /**
     * Do the rotations in the case of the sibling being black and having a red child.
     * @param brother Brother of the double black.
     * @param child Red child of the brother.
     */
    private void doBlackSiblingRedChildRotations(RBNode<Entry<K,V>> brother,RBNode<Entry<K,V>> child){
        RBNode<Entry<K,V>> parent = (RBNode<Entry<K,V>>)brother.getParent();
        if(brother.isLeftChild() && child.isLeftChild()) {  //LL
            super.rotateRight(parent);
            colourNewRoot(brother,parent);
            child.setBlackColour();
        }
        else if(brother.isRightChild() && child.isRightChild()) { //RR
            super.rotateLeft(parent);
            colourNewRoot(brother,parent);
            parent.setBlackColour();
            child.setBlackColour();
        }
        else{
            if(brother.isLeftChild() && child.isRightChild()) { //LR
                rotateLeft(brother);
                rotateRight(parent);
            }
            else{ //RL
                rotateRight(brother);
                rotateLeft(parent);
            }
            colourNewRoot(child,parent);
            parent.setBlackColour();
            brother.setBlackColour();
        }
    }

    /**
     * Do the operations in case of the sibling being black and having no red children.
     * @param doubleBlack DoubleBlack.
     * @param brother Brother of the doubleBlack.
     */
    private void doBlackSiblingNoRedChildrenOperations(RBNode<Entry<K,V>> doubleBlack,
                                                       RBNode<Entry<K,V>>brother ){
        RBNode<Entry<K,V>> parent = (RBNode<Entry<K,V>>)doubleBlack.getParent();
        if(parent.isRed()){ //case 1a red parent.
            parent.setBlackColour();
            brother.setRedColour();
        }
        else{ //case 1b black parent. novo double black perguntar
            brother.setRedColour();
            doDoubleBlackOperations(parent, (RBNode<Entry<K,V>>)parent.getBrother());
        }
    }

    /**
     * Do the double black operations.
     * a node is double black if it's black and has no child.
     * @param removedNode Node removed.
     */
    private void doDoubleBlackOperations(RBNode<Entry<K,V>> removedNode,RBNode<Entry<K,V>> brother){
        RBNode<Entry<K,V>> child;
        if(!brother.isRed()) {
            if (brother.getLeftChild() != null &&
                    ((RBNode<Entry<K, V>>)brother.getLeftChild()).isRed()) {
                child = (RBNode<Entry<K, V>>) brother.getLeftChild();
                doBlackSiblingRedChildRotations(brother,child);
            }
            else if (brother.getRightChild() != null &&
                    ((RBNode<Entry<K, V>>) brother.getRightChild()).isRed()) {
                child = (RBNode<Entry<K, V>>) brother.getRightChild();
                doBlackSiblingRedChildRotations(brother,child);
            }
            else
                doBlackSiblingNoRedChildrenOperations(removedNode,brother);
        }
        else{ //brother is red
            RBNode<Entry<K,V>> parent = (RBNode<Entry<K, V>>)brother.getParent();
            if(brother.isLeftChild())
                rotateRight(parent);
            else
                rotateLeft(parent);
            parent.setRedColour();
            brother.setBlackColour();
        }
    }

    /**
     * Returns the node of the brother of the node being physically removed.
     * @param node node being removed(this node is not the one that was physically removed in the case of the removal with 2 sons
     * meaning that in that case this node is the one with its value replaced).
     * @return the node of the brother of the node being physically removed, or null if the dictionary does not an entry with that key.
     */
    private RBNode<Entry<K,V>> getRemovedBrother(RBNode<Entry<K,V>> node){
        if(node == null)
            return null;

        if (node.getLeftChild() != null && node.getRightChild() != null) {
            RBNode<Entry<K,V>> nodeRemoved =(RBNode<Entry<K, V>>) ((RBNode<Entry<K, V>>) node.getLeftChild())
                    .furtherRightElement();
            return (RBNode<Entry<K, V>>) nodeRemoved.getBrother();
        }
        else
            return (RBNode<Entry<K, V>>)node.getBrother();
    }


    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     * @Time_Complexity_Best_case  - O(h) h being the tree height which is O(log n).
     * @Time_Complexity_Worst_case - O(h) h being the tree height which is O(log n).
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    public V remove(K key) {
        RBNode<Entry<K,V>> node = (RBNode<Entry<K,V>>)getNode((BTNode<Entry<K,V>>)root,key);
        if(node == null)
            return null;

        V returnValue = node.getElement().value();

        RBNode<Entry<K,V>> removedBrother = getRemovedBrother(node);//it's necessary to keep the brother of the node being physically removed.
                                                                   //so we need to get the brother before the removal.
        RBNode<Entry<K,V>> removedNode = (RBNode<Entry<K,V>>)super.removeNode(node);
        if(removedNode == null)
            return null;

        RBNode<Entry<K,V>> leftChild =(RBNode<Entry<K,V>>)removedNode.getLeftChild();
        RBNode<Entry<K,V>> rightChild =(RBNode<Entry<K,V>>)removedNode.getRightChild();

        if(!removedNode.isRed()){
            if(leftChild != null)
                leftChild.setBlackColour();
            else if(rightChild != null)
                rightChild.setBlackColour();
            else
                doDoubleBlackOperations(removedNode,removedBrother);
        }
        return returnValue;
    }


}
