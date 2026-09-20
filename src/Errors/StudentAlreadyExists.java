/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Errors;

/**
 * Exception that happens when a student with that name already exists.
 */
public class StudentAlreadyExists extends Exception {
    public StudentAlreadyExists(String message) {
        super(message);
    }
}
