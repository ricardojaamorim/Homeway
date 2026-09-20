/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Errors;


/**
 * Exception that happens when the student is trying to move to a lodging that is the same as his home.
 */
public class MovingToSameHouse extends Exception {
    public MovingToSameHouse(String message) {
        super(message);
    }
}
