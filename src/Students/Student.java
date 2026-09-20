/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Students;

import Services.Service;

/**
 * Interface of all students with full access.
 */
public interface Student extends ReadOnlyStudent {

    /**
     * Checks if a student is equal to others.
     * The criterion is if they have the same name.
     * @param stud Student being compared with this student
     * @return true if is equal , false otherwise.
     */
    boolean equals(Object stud);

    /**
     * Changes the student home.
     * @param lodging Lodging service that's the new student home.
     */
    void changeHome(Service lodging);


    /**
     * Updates the student current location.
     * @param service Service where the student is now located.
     */
    void setLocation(Service service);
}