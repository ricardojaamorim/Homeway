/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */


package Services;

import java.io.Serializable;

/**
 * Interface of the service reviews.
 */
interface Review extends Serializable { //package private.

    /**
     * Constants.
     */
      final String EMPTY_SPACE = " ";

    /**
     * Does a Knuth-Morris-Pratt(KMP) search, to look if the review has the tag.
     * @param pattern tag.
     * @return true if it does, false otherwise.
     */
    boolean kmpSearch(char[] pattern);



    /**
     * Returns the rating (number of stars) of the review.
     * @return the rating (number of stars) of the review.
     */
    int getStars();


    /**
     * Returns the description of the review.
     * @return the description of the review.
     */
    String getDescription();

}
