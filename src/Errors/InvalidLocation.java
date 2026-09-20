
/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Errors;

/**
 * Exception that happens when the location of the service is invalid.
 */
public class InvalidLocation extends Exception {
    public InvalidLocation(String message ) {
        super(message);
    }
}
