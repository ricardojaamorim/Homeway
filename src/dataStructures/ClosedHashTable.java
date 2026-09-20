package dataStructures;
/**
 * Closed Hash Table
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
public class ClosedHashTable<K,V> extends HashTable<K,V> {

    //Load factors
    static final float IDEAL_LOAD_FACTOR =0.5f;
    static final float MAX_LOAD_FACTOR =0.8f;
    static final int NOT_FOUND=-1;

    // removed cell
    static final Entry<?,?> REMOVED_CELL = new Entry<>(null,null);

    // The array of entries.
    private Entry<K,V>[] table;

    /**
     * Constructors
     */

    public ClosedHashTable( ){
        this(DEFAULT_CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public ClosedHashTable(int capacity ){
        super(capacity);
        int arraySize = HashTable.nextPrime((int) (capacity / IDEAL_LOAD_FACTOR));
        // Compiler gives a warning.
        table = (Entry<K,V>[]) new Entry[arraySize];
        for ( int i = 0; i < arraySize; i++ )
            table[i] = null;
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
    }

    //Methods for handling collisions.
    // Returns the hash value of the specified key.
    int hash( K key, int i ){
        return Math.abs( key.hashCode() + i) % table.length;
    }
    /**
     * Linear Proving
     * @param key to search
     * @Time_Complexity_Best_case O(1).
     * @Time_Complexity_Worst_case O(n).
     * @return the index of the table, where is the entry with the specified key, or null
     */
    int searchLinearProving(K key) {
        for(int i = 0;i < table.length; i++){
            int code = hash(key,i);

            if(table[code] == null)
                return NOT_FOUND;

            if(table[code] != REMOVED_CELL)
                if(table[code].key().equals(key))
                    return code;
        }
        return NOT_FOUND;
    }


    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * returns its value; otherwise, returns null.
     * @Time_Complexity_Best_case O(1).
     * @Time_Complexity_Worst_case O(n).
     * @param key whose associated value is to be returned
     * @return value of entry in the dictionary whose key is the specified key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V get(K key) {
        int index = searchLinearProving(key);

        if(index == NOT_FOUND)
            return null;

        return table[index].value();
    }


    /**
     * Replaces an entry value and returns the old value.
     * @param newEntry New entry in the position.
     * @param code Position in the array.
     * @return previous value associated with key.
     */
    private V replacesKey( Entry<K,V> newEntry,int code){
        V returnValue = table[code].value();
        table[code] = newEntry;
        return returnValue;
    }

    /**
     * Does the put second cycle.
     * Searches for an entry with K key, if it finds one, replaces it's value by the specified value and returns the old value
     * otherwise puts the new entry in the removed position found earlier,
     * @param key with which the specified value is to be associated.
     * @param newEntry NewEntry being put.
     * @param removedSavedPos Position of the removed position found earlier.
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key.
     */
    private V putSecondCycle(K key, Entry<K,V> newEntry, int removedSavedPos){
        int index = searchLinearProving(key);
        if(index == NOT_FOUND){
            table[removedSavedPos] = newEntry;
            currentSize++;
            return null;
        }
        else
            return replacesKey(newEntry,index);
    }

    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * replaces its value by the specified value and returns the old value;
     * otherwise, inserts the entry (key, value) and returns null.
     * @Time_Complexity_Best_case O(1).
     * @Time_Complexity_Worst_case O(n).
     * @param key   with which the specified value is to be associated
     * @param value to be associated with the specified key
     * @return previous value associated with key,
     * or null if the dictionary does not have an entry with that key
     */
    @Override
    public V put(K key, V value) {
        if (isFull())
            rehash();

        Entry<K,V> newEntry =  new Entry<>(key,value);
        int removedSavedPos = -1;
        int counter = 0;
        while(removedSavedPos == -1){
            int code = hash(key,counter);

            if(table[code] == null) {
                table[code] = newEntry;
                currentSize ++;
                return null;
            }
            if(table[code].key().equals(key) )
                return replacesKey(newEntry,code);

            if(table[code] == REMOVED_CELL)
                removedSavedPos = code;

            counter++;
        }
        return putSecondCycle(key,newEntry,removedSavedPos);
    }

    @SuppressWarnings("unchecked")
    private void rehash(){
        Entry<K,V>[] oldTable = table;
        int arraySize = HashTable.nextPrime((int) (currentSize*2 / IDEAL_LOAD_FACTOR));
        // Compiler gives a warning.
        table = (Entry<K,V>[]) new Entry[arraySize];
        for ( int i = 0; i < arraySize; i++ )
            table[i] = null;
        maxSize = (int)(arraySize * MAX_LOAD_FACTOR);
        currentSize = 0;
        for(Entry<K,V> entry : oldTable){
            if(entry != null && entry != REMOVED_CELL)
                put(entry.key(),entry.value());
        }
    }


    /**
     * If there is an entry in the dictionary whose key is the specified key,
     * removes it from the dictionary and returns its value;
     * otherwise, returns null.
     * @Time_Complexity_Best_case O(1).
     * @Time_Complexity_Worst_case O(n)
     * @param key whose entry is to be removed from the map
     * @return previous value associated with key,
     * or null if the dictionary does not an entry with that key
     */
    @SuppressWarnings("unchecked")
    @Override
    public V remove(K key) {
        int index = searchLinearProving(key);

        if(index == NOT_FOUND)
            return null;

        V value = table[index].value();
        table[index] = (Entry<K, V>) REMOVED_CELL;
        currentSize--;

        return value;
    }

    /**
     * Returns an iterator of the entries in the dictionary.
     *
     * @return iterator of the entries in the dictionary
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new FilterIterator<>(new ArrayIterator<>(table,currentSize), m -> m!=null && m!= REMOVED_CELL);
    }

}
