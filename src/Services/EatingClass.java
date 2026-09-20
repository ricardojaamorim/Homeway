/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Services;

/**
 * Class of all Eating services.
 */
class EatingClass extends ServiceWithEntryControlClass implements  //package private.
        Eating, ValidGoServiceWithEntryControl {

    private static final long serialVersionUID = 1L;

    /**
     * Constructor of EatingClass.
     * @param type Type of the service.
     * @param latitude Latitude of the service.
     * @param longitude Longitude of the service.
     * @param price Price of the service.
     * @param value Capacity of the service.
     * @param name Name of the service.
     */
    public EatingClass(String type, String name, long latitude, long longitude,
                       int price, int value) {
        super(type,name, latitude, longitude, price,value);
    }


}
