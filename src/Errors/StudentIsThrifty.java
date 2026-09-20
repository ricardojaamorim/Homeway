/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Errors;

/**
 * Exception that happens if the student name given in the visited command is a name of a thrifty student.
 */
public class StudentIsThrifty extends Exception {
    public StudentIsThrifty(String message) {
        super(message);
    }
}
