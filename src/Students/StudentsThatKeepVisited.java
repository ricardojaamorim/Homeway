/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */


package Students;

import Errors.NoPlacesVisited;
import Services.ReadOnlyService;
import Services.Service;
import dataStructures.Iterator;

/**
 * Interface of all students that keep places visited.
 */
interface StudentsThatKeepVisited extends Student{  //package private.


    /**
     * Constants.
     */

    final int PLACES_VISITED_MAP_INITIAL_CAPACITY = 60;

    /**
     * Adds a service that was visited by the student if he did not visit the service before.
     * @param place Service that was visited by the student.
     */
    void addVisitedPlace(Service place);


    /**
     * Returns the iterator of the places visited by the student.
     * @return the iterator of the places visited by the student.
     */
    Iterator<? extends ReadOnlyService> getVisitedIterator()throws NoPlacesVisited;
}
