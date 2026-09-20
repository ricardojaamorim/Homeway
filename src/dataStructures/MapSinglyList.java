package dataStructures;
/**
 * Map with a singly linked list with head and size
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
class MapSinglyList<K,V> implements Map<K, V> {


    private SinglyListNode<Entry<K,V>> head;

    private int size;

    public MapSinglyList() {
        head = null;
        size = 0;
    }

    /**
     * Returns true iff the dictionary contains no entries.
     *
     * @return true if dictionary is empty
     */

    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns the number of entries in the dictionary.
     *
     * @return number of elements in the dictionary
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Returns true if an entry has a certain key.
     * @param entry Entry.
     * @param key Key.
     * @return true if an entry has a certain key,false otherwise.
     */
    private boolean entryWithKey(Entry<K,V> entry, K key){
        return entry.key().equals(key);
    }

    /**
     * Returns the node with an entry with that key, or null if there's no node with an entry with that key.
     * @param key Entry key.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(n).
     * @return Returns the node with an entry with that key, or null if there's no node with an entry with that key.
     */
    private SinglyListNode<Entry<K,V>> getNode(K key){
        SinglyListNode<Entry<K,V>> node = head;
        while(node != null){
            if(entryWithKey(node.getElement(),key))
                return node;
            node = node.getNext();
        }
        return null;
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(n).
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        SinglyListNode<Entry<K,V>> node = getNode(key);
        if(node == null)
            return null;
        return node.getElement().value();
    }


    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(n).
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */

    public V put(K key, V value) {
        Entry<K,V> entry = new Entry<>(key,value);
        SinglyListNode<Entry<K,V>> node = getNode(key);

        if(node != null){
            V oldValue = node.getElement().value();
            node.setElement(entry);
            return oldValue;
        }

        SinglyListNode<Entry<K,V>> newNode = new SinglyListNode<>(entry);
        SinglyListNode<Entry<K,V>> mapNode = head;
        if(isEmpty())
            head = newNode;
        else {
            while (mapNode.getNext() != null) {
                mapNode = mapNode.getNext();
            }
            mapNode.setNext(newNode);
        }
        size++;
        return null;
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(n).
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    public V remove(K key) {
        if(head == null)
            return null;
        if(entryWithKey(head.getElement(),key)){
            V value = head.getElement().value();
            head = head.getNext();
            size--;
            return value;
        }

        SinglyListNode<Entry<K,V>> previousNode = head;
        SinglyListNode<Entry<K,V>> node = previousNode.getNext();
        while(node != null){
            if(entryWithKey(node.getElement(),key)){
                previousNode.setNext(node.getNext() );
                size--;
                return node.getElement().value();
            }
            previousNode = previousNode.getNext();
            node = node.getNext();
        }
        return null;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    public Iterator<Entry<K, V>> iterator() {
        return new SinglyIterator<>(head);
    }

    /**
     * Returns an iterator of the values in the dictionary.
     *
     * @return iterator of the values in the dictionary
     */
    @SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<V> values() {
        return new ValuesIterator(iterator());
    }

    /**
     * Returns an iterator of the keys in the dictionary.
     *
     * @return iterator of the keys in the dictionary
     */
    @SuppressWarnings({"unchecked","rawtypes"})
    public Iterator<K> keys() {
        return new KeysIterator(iterator());
    }

}
