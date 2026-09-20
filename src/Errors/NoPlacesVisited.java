/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Errors;

/**
 * Exception that happens when the student didn't visit any service.
 */
public class NoPlacesVisited extends Exception {
    public NoPlacesVisited(String message) {
        super(message);
    }
}
