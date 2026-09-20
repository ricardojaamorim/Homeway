/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Errors;

/**
 * Exception that happens when the student is moving to a location where he is already at.
 */
public class MovingToSameLocation extends Exception {
    public MovingToSameLocation() {
        super();
    }
}
