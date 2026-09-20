/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Errors;

/**
 * Exception that happens when a thrifty student is trying to move to a lodging that is more expensive or the same price as his house.
 */
public class InvalidThriftyMove extends Exception {
    public InvalidThriftyMove(String message) {
        super(message);
    }
}
