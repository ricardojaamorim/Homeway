/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Errors;

/**
 * Exception that happens when the user tries to create an area with a name already used.
 */
public class AreaAlreadyExists extends Exception {
    public AreaAlreadyExists() {
        super();
    }
}
