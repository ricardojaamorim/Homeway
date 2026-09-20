/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Errors;

/**
 * Exception that happens when the service given doesn't control the entries of the students.
 */
public class ServiceWithNoEntryControl extends Exception {
    public ServiceWithNoEntryControl(String message) {
        super(message);
    }
}
