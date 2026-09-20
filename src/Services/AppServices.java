
/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Services;

import Errors.*;
import Students.ReadOnlyStudent;
import Students.Student;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;

import java.io.Serializable;

public interface AppServices extends Serializable {

    /**
     * Constants.
     */

    final int MINIMUML_SERVICE_PRICE = 0;

    final int MINIMUM_CAPACITY = 0;

    final int MINIMUM_DISCOUNT = 0;

    final int MAXIMUM_DISCOUNT = 100;

    final int ARRAYS_FIRST_INDEX = 0;     //first index of an array.

    final int NUMBER_OF_RATINGS = 5;

    final int MINIMUM_STAR_NUMBER = 1;
    final int MAXIMUM_STAR_NUMBER = 5;


    final int SERVICES_ORDERED_BY_RATING_LENGTH = NUMBER_OF_RATINGS + 1;
                     //used + because the array starts at position 0
                    // which is not used.

    final int SERVICES_MAP_INITIAL_CAPACITY = 80; //I think more than 80 services in an area is unrealistic.


    final int SERVICES_WITH_CERTAIN_RATING_LIST_INITIAL_CAPACITY = 30;

    final int SERVICES_WITH_CERTAIN_RATING_AND_TYPE_LIST_INITIAL_CAPACITY = 15;

    /**
     * Returns the service with that name.
     * @param name Name of the student
     * @throws ServiceDoesntExist Exception that happens when there's no service with that name in the area.
     * @return the student with that name.
     */
    Service getService(String name)throws ServiceDoesntExist;



    /**
     * Adds a new service to the area.
     * @param type Type of the service.
     * @param latitude Latitude of the service.
     * @param longitude Longitude of the service.
     * @param price Price of the service.
     * @param value Value of the service.
     * @param name Name of the service.
     * @throws InvalidEatingPrice Exception that happens when the price given to an eating service is invalid.
     * @throws InvalidLodgingPrice Exception that happens when the price given to a lodging service is invalid.
     * @throws InvalidLeisurePrice Exception that happens when the price given to a leisure service is invalid.
     * @throws InvalidDiscountPrice Exception that happens when the discount given to the service is invalid.
     * @throws InvalidCapacity Exception that happens when the capacity given to the service is invalid.
     * @throws ServiceAlreadyExists Exception that happens when there's already a service with that name in the area.
     */
    void addService(String type, long latitude,long longitude,int price,
                    int value, String name)throws InvalidEatingPrice, InvalidLodgingPrice, InvalidLeisurePrice,
            InvalidDiscountPrice, InvalidCapacity,ServiceAlreadyExists;



    /**
     * Returns the iterator of the services.
     * @return the iterator of the services.
     */
    Iterator<? extends ReadOnlyService> getServicesIterator();



    /**
     * Returns the iterator of the occupants of the service.
     * @param serviceName Name of the service.
     * @param orderSymbol Symbol of the order which the students are going to be iterated.
     * @return the iterator of the occupants of the service.
     * @throws InvalidOrderType Exception that happens when the order symbol is invalid.
     * @throws ServiceDoesntExist Exception that happens when there's no service with that name in the area.
     * @throws ServiceWithNoEntryControl Exception that happens when the service given doesn't control the entries of the students.
     */
    TwoWayIterator<? extends ReadOnlyStudent> getStudentsInServiceIterator(String serviceName, String orderSymbol)
            throws InvalidOrderType,ServiceDoesntExist,ServiceWithNoEntryControl;



    /**
     * Throws an exception if the number of stars given is invalid.
     * @param stars Number of stars given.
     * @throws InvalidRating Exception that happens when the number of stars given is invalid (isn't between 1 and 5).
     */
    void checkIfRatingIsInvalid(int stars)throws InvalidRating;



    /**
     * Adds an evaluation to a service.
     * @param stars Rating(number of stars) given in the evaluation.
     * @param serviceName Name of the service.
     * @param description Description of the evaluation.
     * @throws InvalidRating Exception that happens when the number of stars given is invalid (isn't between 1 and 5).
     * @throws ServiceDoesntExist Exception that happens when there's no service with that name in the area.
     */
    void evaluateService(int stars, String serviceName, String description)throws InvalidRating,
            ServiceDoesntExist;



    /**
     * Returns an iterator of iterators of the lists of every rating.
     * Meaning that the iterator will iterate an iterator that will iterate the services with 5 star, then will iterate an iterator of 4-star services
     * etc.
     * @return an iterator of iterators of the lists of every rating.
     * @throws NoServicesYet Exception that happens when there are no services in the area yet.
     */
    Iterator<? extends ReadOnlyService> getRankingIterator()throws NoServicesYet;



    /**
     * Returns a filtered iterator that only iterates services with the desired tag.
     * @param tag Tag being checked.
     * @return a filtered iterator that only iterates services with the desired tag.
     */
    Iterator<? extends ReadOnlyService> getServicesWithTagIterator(String tag);


    /**
     * Throws an exception if the service type is invalid.
     * @param type Service type.
     * @throws InvalidServiceType Exception that happens when the type of the service is invalid.
     */
    void checkIfServiceTypeIsInvalid(String type)throws InvalidServiceType;


    /**
     * Returns an iterator of the services of a certain rating and type closer to the student.
     * Only the closest service is stored, unless there's a tie.
     * @param type Type of the service.
     * @param stars Number of stars of the service(rating).
     * @param student Student.
     * @return an iterator of the services of a certain rating and type closer to the student.
     * @throws InvalidServiceType Exception that happens when the type of the service is invalid.
     * @throws NoTypeServices Exception that happens when there's no services with that type in the area.
     * @throws NoTypeServicesWithThatRating Exception that happens when there's no service with that type with that rating.
     */
     Iterator<? extends ReadOnlyService> getRankedIterator(String type, int stars, Student student)
            throws InvalidServiceType, NoTypeServices,NoTypeServicesWithThatRating;



    /**
     * Returns the most relevant service of a certain type, for a specific student.
     * For thrifty students returns the less expensive service of that type(in case of a tie returns the first service inserted in the system).
     * For the other students types, returns the service of that type that has better rating
     * (in case of a tie returns the service with more time in this average).
     * @param student Student.
     * @param type Type of the student
     * @return the most relevant service of a certain type, for a specific student.
     * @throws StudentDoesntExist Exception that happens when there's no student with that name in the area.
     * @throws NoTypeServices Exception that happens when there's no services with that type in the area.
     */
    ReadOnlyService findMostRelevantService(Student student, String type)throws NoTypeServices;
}
