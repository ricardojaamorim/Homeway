/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */


package Services;

/**
 * Enum that contains all types of services.
 * the number indicates their index in the cheaperServiceByType array.
 */
public enum ServicesTypes {
    EATING(0),
    LODGING(1),
    LEISURE(2);

    /**
     * index of the type in the cheaperServiceByType array.
     */
    private final int index;

    /**
     * Constructor of the ServicesTypes enum.
     * @param index index of the type in the cheaperServiceByType array.
     */
     ServicesTypes(int index){
         this.index = index;
     }


    /**
     * Returns the index of the type in the cheaperServiceByType array.
     * @return the index of the type in the cheaperServiceByType array.
     */
     public int getIndex(){
         return index;
     }


    /**
     * Checks if the type of the service is invalid.
     * @param type type of the service.
     * @return true if the type is invalid, false otherwise.
     */
    public static boolean invalidServiceType(String type) {
        for(ServicesTypes serviceType : ServicesTypes.values())
            if(serviceType.name().equalsIgnoreCase(type) )
                return false;
        return true;
    }

}
