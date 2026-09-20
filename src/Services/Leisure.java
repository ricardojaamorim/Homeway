/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Services;

/**
 * Interface of all Leisure services.
 */
public interface Leisure extends Service { //has to be public because AppStudentsClass needs access.

    /**
     * Returns the discount of the service.
     * @return  the discount of the service.
     */
    int getDiscount();

    /**
     * Returns the final price counting with the discount.
     * @return the final price counting with the discount.
     */
    float getFinalPrice();
}
