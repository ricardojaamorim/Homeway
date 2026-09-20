package dataStructures;
/**
 * SepChain Hash Table Iterator
 * @author AED  Team
 * @version 1.0
 * @param <K> Generic Key
 * @param <V> Generic Value
 */
import dataStructures.exceptions.NoSuchElementException;

class SepChainHashTableIterator<K,V> implements Iterator<Map.Entry<K,V>> {

    /**
     * Array being iterated.
     */
    private final Map<K,V>[] table;

    /**
     * Current index of the array being iterated.
     */
    private int current;

    /**
     * Size of the array being iterated.
     */
    private final int size;

    /**
     * Iterator of the current MapSinglyList.
     */
    private Iterator<Map.Entry<K, V>> it;

    public SepChainHashTableIterator(Map<K,V>[] table) {
        this.table = table;
        size = table.length;
        int firstList = nextNonEmptyList(0);
        if(firstList == -1) {
            it = table[size - 1].iterator(); //if all lists are empty the iterator is the iterator of the last list,
            current = size - 1;              //facilitate knowing the iteration does not have elements.
        }
        else {
            it = table[firstList].iterator();
            current = firstList;
        }
    }

    /**
     * Searches for the next non-empty list in the array.
     * @param position Position where the search starts.
     * @return the index of the non-empty list or -1 if there's none.
     */
    private int nextNonEmptyList(int position){
        for(int i = position; i < size; i++){
            if(!table[i].isEmpty())
                return i;
        }
        return -1;
    }

    /**
     * Returns true if next would return an element
     * rather than throwing an exception.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(n).
     * @return true iff the iteration has more elements
     */
    public boolean hasNext() {
        return it.hasNext() || (nextNonEmptyList(current + 1) != -1 );
    }

    /**
     * Returns the next element in the iteration.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(n).
     * @return the next element in the iteration
     * @throws NoSuchElementException - if call is made without verifying pre-condition
     */
    public Map.Entry<K,V> next() {
        if(!hasNext())
            throw new NoSuchElementException();

        if(!it.hasNext()){
            current = nextNonEmptyList(current + 1);
            it = table[current].iterator();
        }
        return it.next();
    }

    /**
     * Restarts the iteration.
     * After rewind, if the iteration is not empty, next will return the first element.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(n).
     */
    public void rewind() {
        int firstList = nextNonEmptyList(0);
        if (firstList == -1) {
            it = table[size - 1].iterator(); // if all lists are empty the iterator is the iterator of the last list,
            current = size - 1;              //facilitate knowing the iteration does not have elements.
        } else {
            it = table[firstList].iterator();
            current = firstList;
        }
    }

}

