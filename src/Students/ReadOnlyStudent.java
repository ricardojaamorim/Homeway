/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Students;

import Services.Service;

import java.io.Serializable;

/**
 * Interface of all students with limited access (can not change anything).
 */
public interface ReadOnlyStudent extends Serializable {

    /**
     * Returns the students type.
     * Usage is in the output.
     * @return the students type.
     */
    String getType();

    /**
     * Returns the students name.
     * @return the students name.
     */
    String getName();

    /**
     * Returns the service where the student is right now.
     * @return the service where the student is right now.
     */
    Service getLocation();

    /**
     * Returns the students home.
     * @return the students home.
     */
    Service getHome();

    /**
     * Returns the students country.
     * @return the students country.
     */
    String getCountry();


}
