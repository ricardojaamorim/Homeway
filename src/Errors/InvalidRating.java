/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Errors;

/**
 * Exception that happens when the number of stars given is invalid (isn't between 1 and 5).
 */
public class InvalidRating extends Exception {
    public InvalidRating() {
        super();
    }
}
