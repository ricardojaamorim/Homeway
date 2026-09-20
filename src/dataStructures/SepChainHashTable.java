package dataStructures;
/**
 * SepChain Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class SepChainHashTable<K,V> extends HashTable<K,V> {

    //Load factors
    static final float IDEAL_LOAD_FACTOR =0.75f;
    static final float MAX_LOAD_FACTOR =0.9f;

    // The array of Map with singly linked list.
    private Map<K,V>[] table;

    public SepChainHashTable( ){
        this(DEFAULT_CAPACITY);
    }


    public SepChainHashTable( int capacity ){
        super(capacity);
        makeTable(capacity);
    }

    // Returns the hash value of the specified key.
    protected int hash( K key ){
        return Math.abs( key.hashCode() ) % table.length;
    }


    @SuppressWarnings("unchecked")
    private void makeTable(int nEntries){
        int arraySize = nextPrime((int) (nEntries / IDEAL_LOAD_FACTOR));
        table = (Map<K,V>[]) new Map[arraySize];
        for ( int i = 0; i < arraySize; i++ )
            table[i] = new MapSinglyList<>();
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
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
    public V get(K key) {
        return table[hash(key)].get(key);
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
        if (isFull())
            rehash();

        int code = hash(key);
        int initialSize = table[code].size();
        V previousValue = table[code].put(key,value);

        if(table[code].size() > initialSize)
            currentSize++;

        return previousValue;
    }

// ime_Complexity_Best_case  - O(n).
// Time_Complexity_Worst_case - O(n).
    private void rehash() {
        Map<K,V>[] oldTable = table;
        makeTable(currentSize * 2);
        currentSize = 0;
        for(Map<K,V> list : oldTable){
            Iterator<Entry<K, V>> it = list.iterator();
            while(it.hasNext()) {
                Entry<K, V> entry = it.next();
                put(entry.key(), entry.value());
            }
        }
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
        int code = hash(key);

        int initialSize = table[code].size();
        V value =  table[code].remove(key);
        if(initialSize > table[code].size())
            currentSize--;

        return value;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    public Iterator<Entry<K, V>> iterator() {
        return new SepChainHashTableIterator<>(table);
    }


}
