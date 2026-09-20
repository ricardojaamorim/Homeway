/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */


package Errors;

/**
 * Exception that happens when the service is already full.
 */
public class ServiceAlreadyFull extends Exception {
    public ServiceAlreadyFull( String message) {
        super(message);
    }
}
