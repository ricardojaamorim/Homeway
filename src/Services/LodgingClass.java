/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Services;

/**
 * Class of all Lodging services.
 */
class LodgingClass extends ServiceWithEntryControlClass implements Lodging,StudentCantGoThere { //package private.

    private static final long serialVersionUID = 1L;


    /**
     * Constructor of LodgingClass.
     * @param type Type of the service.
     * @param latitude Latitude of the service.
     * @param longitude Longitude of the service.
     * @param price Price of the service.
     * @param value Capacity of the service.
     * @param name Name of the service.
     */
    public LodgingClass(String type, String name, long latitude, long longitude,
    int price, int value) {
        super(type, name, latitude, longitude, price, value);

    }

}
