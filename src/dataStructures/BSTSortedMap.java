package dataStructures;

import dataStructures.exceptions.EmptyMapException;


/**
 * Binary Search Tree Sorted Map
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class BSTSortedMap<K extends Comparable<K>,V> extends BTree<Map.Entry<K,V>> implements SortedMap<K,V>{

    /**
     * Constructor
     */
    public BSTSortedMap(){
        super();
    }
    /**
     * Returns the entry with the smallest key in the dictionary.
     * @Time_Complexity_Best_case  - O(1) for BST maps
     * O(h) for AVL e RB, h being the tree height, h is O(log n).
     * @Time_Complexity_Worst_case - O(h) h being the tree height, h is O(log n) for AVL and RB
     * h is O(n) for BST in this case(worst case).
     * @return
     * @throws EmptyMapException
     */
    @Override
    public Entry<K, V> minEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherLeftElement().getElement();
    }

    /**
     * Returns the entry with the largest key in the dictionary.
     * @Time_Complexity_Best_case  - O(1) for BST maps
     * O(h) for AVL e RB, h being the tree height, h is O(log n).
     * @Time_Complexity_Worst_case - O(h) h being the tree height, h is O(log n) for AVL and RB
     * h is O(n) for BST in this case(worst case).
     * @return
     * @throws EmptyMapException
     */
    @Override
    public Entry<K, V> maxEntry() {
        if (isEmpty())
            throw new EmptyMapException();
        return furtherRightElement().getElement();
    }


    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(h) h being the tree height, h is O(log n) for AVL and RB
     * h is O(n) for BST in this case(worst case).
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        Node<Entry<K,V>> node=getNode((BTNode<Entry<K,V>>)root,key);
        if (node!=null)
            return node.getElement().value();
        return null;
    }


    /**
     * Returns true if the node has the key being searched.
     * @param node NOde being checked.
     * @param key Key of the desired node.
     * @return true if the node has the key being searched, false otherwise.
     */
    private boolean isTheCorrectNode(BTNode<Entry<K,V>> node, K key){
        if(node == null)
            return false;
        return (node.getElement().key() ).compareTo(key) == 0;
    }

    /**
     *
     * Returns true if the key is lower than the node's element key.
     * @param node Node being compared.
     * @param key Key to compare to the node's element key.
     * @return true if the key is lower than the node's element key, false otherwise.
     */
    private boolean isLowerThanNode(K key, BTNode<Entry<K,V>> node) {
        return key.compareTo(node.getElement().key()) < 0;
    }

    /**
     * Returns the node with this key.
     * @param node Node where the search starts.
     * @param key Key of the wanted node.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(h) h being the tree height, h is O(log n) for AVL and RB
     * h is O(n) for BST in this case(worst case).
     * @return the node with this key.
     */
    BTNode<Entry<K,V>> getNode(BTNode<Entry<K,V>> node, K key) { //package private because BTNode is package private.
        while(node != null && !isTheCorrectNode(node, key)) {
            if(isLowerThanNode(key,node))
                node = (BTNode<Entry<K,V>>) node.getLeftChild();
            else
                node = (BTNode<Entry<K,V>>) node.getRightChild();
        }
        return node;
    }

    /**
     * Replaces a node's entry with other entry, and returns the previous entry value,
     * @param node Node with the entry replaced.
     * @param newEntry New entry.
     * @return the previous entry's value.
     */
    private V replacesNode(BTNode<Entry<K,V>> node, Entry<K,V> newEntry){
        V oldValue = node.getElement().value();
        node.setElement(newEntry);
        return oldValue;
    }

    /**
     * Adds a new node to the non-empty tree.
     * @param newNode Node being added.
     * @param parent Parent of the added node.
     * @param newNodeKey Key of the added node.
     */
    private void addNodeToNonEmptyTree(Node<Entry<K,V>> newNode,
                                       BTNode<Entry<K,V>> parent, K newNodeKey){
        BTNode<Entry<K,V>> node = (BTNode<Entry<K,V>>)newNode;
        node.setParent(parent);
        if(isLowerThanNode(newNodeKey,parent))
            parent.setLeftChild(node);
        else
            parent.setRightChild(node);
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(h) h being the tree height, h is O(n) in this case (worst case).
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V put(K key, V value) {
        Entry<K,V> newEntry = new Entry<>(key,value);
        BTNode<Entry<K,V>> newNode = new BTNode<>(newEntry);
        return putNode(key,newNode);
    }

    /**
     * Put method
     * @param key Key of the new element.
     * @param newNode Node being inserted.
     * @Time_Complexity_Best_case  - O(1) for BST, O(h) for AVL and RB.
     * h being the tree height, h is O(log n)
     * @Time_Complexity_Worst_case - O(h) h being the tree height, h is O(n) in this case (worst case) for BST
     * h is O(log n) for AVL and RB.
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key.
     */
    V putNode(K key,Node<Entry<K,V>> newNode){ //package private because Node is package private.
        if(isEmpty())
            root = newNode;
        else {
            BTNode<Entry<K,V>> parent =(BTNode<Entry<K,V>>) root();
            BTNode<Entry<K,V>> nextNode =(BTNode<Entry<K,V>>) root();
            while(nextNode != null && !isTheCorrectNode(nextNode, key)){
                parent = nextNode;
                if(isLowerThanNode(key,nextNode))
                    nextNode = (BTNode<Entry<K,V>>) nextNode.getLeftChild();
                else
                    nextNode = (BTNode<Entry<K,V>>) nextNode.getRightChild();
            }
            if(isTheCorrectNode(nextNode, key))
                return replacesNode(nextNode,newNode.getElement());

            addNodeToNonEmptyTree(newNode,parent,key);
        }
        currentSize ++;
        return null;
    }

    /**
     * Case 1 of the remove command.
     * @Case1 When the node is a leaf (doesn't have children).
     * @param node Node being removed.
     */
    private void removeCase1(BTNode<Entry<K,V>> node){
        if(node == root())
            root = null;
        else {
            BTNode<Entry<K, V>> parent = (BTNode<Entry<K, V>>) node.getParent();
            if (node.isRightChild())
                parent.setRightChild(null);
            else
                parent.setLeftChild(null);
        }
    }

    /**
     * Case 2 of the remove command.
     * @Case2 When the node only has 1 son.
     * @param node Node being removed.
     */
    private void removeCase2(BTNode<Entry<K,V>> node){
        BTNode<Entry<K,V>> child;
        BTNode<Entry<K,V>> parent = (BTNode<Entry<K,V>>)node.getParent();
        if(node.getLeftChild() != null)
            child = (BTNode<Entry<K,V>>)node.getLeftChild();
        else
            child = (BTNode<Entry<K,V>>)node.getRightChild();

        child.setParent(parent);
        if(node.isRoot())
            root = child;
        else{
            if(node.isLeftChild())
                parent.setLeftChild(child);
            else
                parent.setRightChild(child);
        }
    }


    /**
     * Case 3 of the remove command.
     * @Case3 When the node has 2 sons.
     * @param node Node being removed.
     * @return the removed node.
     */
    private BTNode<Entry<K,V>> removeCase3(BTNode<Entry<K,V>> node){
        BTNode<Entry<K,V>> remNode =
                ((BTNode<Entry<K,V>>) node.getLeftChild()).furtherRightElement();
        BTNode<Entry<K,V>> remNodeParent = (BTNode<Entry<K,V>>) remNode.getParent();
        BTNode<Entry<K,V>> remNodeChild =  (BTNode<Entry<K,V>>) remNode.getLeftChild();

        node.setElement(remNode.getElement());

        if(remNodeChild != null)
            remNodeChild.setParent(remNodeParent);
        if(remNode.isLeftChild())
            remNodeParent.setLeftChild(remNodeChild);
        else
            remNodeParent.setRightChild(remNodeChild);

        currentSize --;
        return remNode;
    }

    /**
     * If there is a node with an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns it,
     * otherwise, returns null.
     * @Time_Complexity_Best_case  - O(1) for BSt, O(h) for AVl and RB, h being the tree height h is O(log n).
     * @Time_Complexity_Worst_case - O(h) h being the tree height, h is O(n) in this case (worst case) for BST
     * and h is O(log n) for AVL and RB.
     * @param node Node being removed.
     * @return the removed Node or null if there's no node with an entry with that key.
     */
    BTNode<Entry<K,V>> removeNode(BTNode<Entry<K,V>> node){  //package private because BTNode is package private.
        if(node.isLeaf()){
            removeCase1(node);
        }
        else {
            if (node.getLeftChild() != null && node.getRightChild() != null)
                return removeCase3(node);
            else
                removeCase2(node);
        }
        currentSize --;
        return node;
    }
    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(h) h being the tree height, h is O(n) in this case (worst case).
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    @Override
    public V remove(K key) {
        BTNode<Entry<K,V>> node = getNode((BTNode<Entry<K,V>>)root,key);
        if(node == null)
            return null;

        V returnValue = node.getElement().value();

        removeNode(node);
        return returnValue;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new InOrderIterator<>((BTNode<Entry<K,V>>) root);
    }


    /**
     * Returns an iterator of the values in the dictionary.
     *
     * @return iterator of the values in the dictionary
     */
    @Override
    @SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<V> values() {
        return new ValuesIterator(iterator());
    }

    /**
     * Returns an iterator of the keys in the dictionary.
     *
     * @return iterator of the keys in the dictionary
     */
    @Override
    @SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<K> keys() {
        return new KeysIterator(iterator());
    }




}
