/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Errors;

/**
 * Exception that happens when there's no lodging service with that name.
 */
public class LodgingDoesntExist extends Exception {
    public LodgingDoesntExist() {
        super();
    }
}
