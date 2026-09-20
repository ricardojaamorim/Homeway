/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */


package dataStructures;


import dataStructures.exceptions.NoSuchElementException;

/**
 * Iterator that iterates the content of other iterators.
 * @param <E> Type of the element being iterated.
 */
public class IteratorsIterator<E>  implements Iterator<E> {


    /**
     * Current index of the iterator being iterated in the list.
     */
    private int current;

    /**
     * List of the iterators.
     */
    private final List<Iterator<E>> iteratorList;

    /**
     * Current iterator being iterated.
     */
    private Iterator<E> it;

    public IteratorsIterator(List<Iterator<E>> list){
        iteratorList = list;
        it = list.getFirst();
        current = 0;
        advance();
    }

    //Time_Complexity_Best_case  - O(1).
    // Time_Complexity_Worst_case - O(1).
    @Override
    public boolean hasNext() {
        return it.hasNext() || current + 1 < iteratorList.size();
    }

    /**
     * Advances to the next iterator if needed and if is possible.
     * @Time_Complexity_Best_case  - O(1).
     * @Time_Complexity_Worst_case - O(n) n being the number of iterators in the list.
     */
    private void advance(){
        while(!it.hasNext() && current + 1 < iteratorList.size()){
                current++;
                it = iteratorList.get(current);
        }
    }

    //Time_Complexity_Best_case  - O(1).
    // Time_Complexity_Worst_case - O(n) n being the number of iterators in the list.
    @Override
    public E next() {
        if(!hasNext() )
            throw new NoSuchElementException();

       E element = it.next();
       advance();
       return element;
    }

    //Time_Complexity_Best_case  - O(1).
    // Time_Complexity_Worst_case - O(1).
    @Override
    public void rewind() {
        it = iteratorList.getFirst();
        current = 0;
    }

}
