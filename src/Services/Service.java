/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Services;


/**
 * Interface of all services with full access.
 */
public interface Service extends ReadOnlyService {

    /**
     * Constants.
     */
    final int INITIAL_SERVICE_RATING = 4;
    final int INITIAL_RATINGS = 1;  //The service starts with a rating(1);
    final int THIS_EVALUATION = 1 ;

    /**
     * Checks if a service is equal to others.
     * The criterion is if they have the same name.
     * @param serv service being compared with this service
     * @return true if is equal , false otherwise.
     */
    boolean equals(Object serv);



    /**
     * Adds a review to the service, and updates the overall rating of the service.
     * @param stars Number of stars given in the evaluation.
     * @param description Review given in the evaluation.
     */
    void addReview(int stars, String description);


    /**
     * Returns true if the service has the tag being checked.
     * @return true if the service has the tag being checked,false otherwise.
     */
    boolean hasTag(String tag);


    /**
     * Calculates the Manhattan Distance between this service and other service.
     * @param service Service that is being calculated the distance between him and this service.
     * @return the Manhattan Distance between this service and other service.
     */
    long getManhattanDistance(Service service);



}
