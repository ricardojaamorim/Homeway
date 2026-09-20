/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */


package Services;

import Errors.ServiceAlreadyFull;
import Students.ReadOnlyStudent;
import Students.Student;
import dataStructures.TwoWayIterator;


/**
 * Interface of the services that control the students entry.
 */
public interface ServiceWithEntryControl extends Service{ //has to be public because AppStudentsClass needs access.

    /**
     * Constants.
     */

    final int OCCUPANTS_MAP_INITIAL_CAPACITY = 30;

    /**
     * Throws an exception if the service is already full.
     * @throws ServiceAlreadyFull Exception that happens when the service is already full.
     */
    void isFull() throws ServiceAlreadyFull;

    /**
     * Adds an occupant to the service.
     * @param student student that is now in the service,
     */
    void addOccupant(Student student);

    /**
     * Removes an occupant from the service.
     * @param student Student that is leaving the service,
     */
    void removeOccupant(Student student);

    /**
     * Returns true if there's no students in the service.
     * @return true if there's no students in the service, false otherwise.
     */
    boolean hasNoOccupants();


    /**
     * Returns the capacity of the service.
     * @return the capacity of the service.
     */
    int getCapacity();

    /**
     * Returns the number of students in the service.
     * @return the number of students in the service.
     */
    int getOccupantsCount();


    /**
     * Returns a two-way iterator of the students in the service.
     * @return a two-way iterator of the students in the service.
     */
    TwoWayIterator<? extends ReadOnlyStudent> getOccupantsIterator();

}
