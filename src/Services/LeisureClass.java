/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Services;



/**
 * Class of all Leisure services.
 */
class LeisureClass extends ServiceClass implements Leisure,hasDiscount { //package private.

    private static final long serialVersionUID = 1L;

    /**
     * Discount of the service.
     */
    private final int discount;

    /**
     * Constructor of LeisureClass.
     * @param type Type of the service.
     * @param latitude Latitude of the service.
     * @param longitude Longitude of the service.
     * @param price Price of the service.
     * @param value Discount of the service.
     * @param name Name of the service.
     */
    public LeisureClass(String type, String name, long latitude, long longitude,
                        int price, int value) {
        super(type,name, latitude, longitude, price);
        discount = value;
    }


    @Override
    public int getDiscount() {
        return discount;
    }


    @Override
    public float getFinalPrice() {
        return getPrice() - getPrice() * ( (float) discount / 100);
    }


}
