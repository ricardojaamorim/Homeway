package dataStructures;
/**
 * AVL Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class AVLSortedMap <K extends Comparable<K>,V> extends AdvancedBSTree<K,V>{


    /**
     * Checks the tree for unbalances, and corrects them when encounters one.
     * Also updates the node's heights.
     * @Time_Complexity_Best_case  - O(h)h being the tree height, h is O(log n).
     * @Time_Complexity_Worst_case - O(h) h being the tree height, h is O(log n).
     * @param current Node being checked(the node given in the argument it's the first being checked, then climbs up).
     */
    private void checkTreeBalance(AVLNode<Entry<K,V>> current){
        while(current != null) {
            if (current.isUnbalanced()) {
                restructure(current);

                ((AVLNode<Entry<K,V>>) current.getBrother()).updateHeight();
            }                          //current(we can call it z) after the rotation is no longer the parent of y and x.
            current.updateHeight();    //we update z height here and his current brother height, and the remaining node height
            current = (AVLNode<Entry<K, V>>) current.getParent();    //(the now parent of the 2) in the next iteration of the cycle.
        }
    }

    /**
     *
     * @param key
     * @param value
     * @Time_Complexity_Best_case  - O(h) h being the tree height, h is O(log n).
     * @Time_Complexity_Worst_case - O(h) h being the tree height, h is O(log n).
     * @return
     */
    public V put(K key, V value) {
        V returnValue = null;
        Entry<K,V> newEntry = new Entry<>(key,value);
        AVLNode<Entry<K,V>> newNode = new AVLNode<>(newEntry);
        returnValue = super.putNode(key,newNode);
        if(returnValue != null)
            return returnValue;
        else{
            checkTreeBalance((AVLNode<Entry<K, V>>) newNode.getParent());
            return null;
        }
    }

    /**
     *
     * @param key whose entry is to be removed from the map
     * @Time_Complexity_Best_case  - O(h) h being the tree height, h is O(log n).
     * @Time_Complexity_Worst_case - O(h) h being the tree height, h is O(log n).
     * @return
     */
    public V remove(K key) {
        AVLNode<Entry<K,V>> node = (AVLNode<Entry<K,V>>)getNode((BTNode<Entry<K,V>>)root,key);
        if(node == null)
            return null;

        V returnValue = node.getElement().value();

        AVLNode<Entry<K,V>> removedNode =  (AVLNode<Entry<K,V>>)super.removeNode(node);

        AVLNode<Entry<K,V>> current = (AVLNode<Entry<K,V>>)removedNode.getParent();
        checkTreeBalance(current);

        return returnValue;
    }


}
