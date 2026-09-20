/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Errors;

/**
 * Exception that happens when there's no service with that type with that rating.
 */
public class NoTypeServicesWithThatRating extends Exception {
    public NoTypeServicesWithThatRating() {
        super();
    }
}
