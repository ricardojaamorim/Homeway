/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Services;


import dataStructures.Predicate;

/**
 * Class used to see if the class has the tag desired.
 */
 class containsTag implements Predicate<Service> { //package private.

    private static final String EMPTY_SPACE = " ";

    /**
     * Tag desired.
     */
     private final String tag;

    /**
     * Constructor of the class containsTag.
     * @param tag Tag being checked.
     */
    public containsTag(String tag){
        this.tag = EMPTY_SPACE + tag.toUpperCase() + EMPTY_SPACE;
    }


    @Override
    public boolean check(Service elem) {
        if (elem==null)
            return false;

        return elem.hasTag(tag);
    }
}
