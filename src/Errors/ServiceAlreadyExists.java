/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */


package Errors;

/**
 * Exception that happens when there's already a service with that name in the area.
 */
public class ServiceAlreadyExists extends Exception {
    public ServiceAlreadyExists(String message) {
        super(message);
    }
}
