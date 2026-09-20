/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Services;


import dataStructures.Iterator;

import java.io.Serializable;

/**
 * Interface of all services with limited access (can not change anything).
 */
public interface ReadOnlyService extends Serializable {


    /**
     * Returns the type of the service.
     * @return the type of the service.
     */
    String getType();


    /**
     * Returns the name of the service.
     * @return the name of the service.
     */
    String getName();


    /**
     * Returns the latitude of the service.
     * @return the latitude of the service.
     */
    long getLatitude();


    /**
     * Returns the longitude of the service.
     * @return the longitude of the service.
     */
    long getLongitude();


    /**
     * Returns the price of the service.
     * @return the price of the service.
     */
    int getPrice();


    /**
     * Returns the rounded average rating of the service.
     * @return the rounded average rating of the service.
     */
    int getAverageRating();


    /**
     * Returns the number of reviews the service got.
     * @return the number of reviews the service got.
     */
    int getEvaluationCount();


}
