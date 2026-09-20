
/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package App;

import Errors.*;
import Services.ReadOnlyService;
import Students.ReadOnlyStudent;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;

import java.io.IOException;
import java.io.Serializable;

public interface App extends Serializable {

    /**
     * Constants.
     */
    final int nTypesOfCoords = 2;


    final String AREA_FILE_TYPE =".ser";
    final String ASCENDING_ORDER_SYMBOL ="<";
    final String DESCENDING_ORDER_SYMBOL =">";


    /**
     * Returns true if the bounds are defined.
     * @return true if the bounds are defined,false otherwise.
     */
     boolean boundsAreDefined();


    /**
     * Saves the current area in a file.
     * @throws IOException
     */
    void saveArea()throws IOException;


    /**
     * Loads the requested area.
     * @param areaName Name of the area.
     * @return This class with the area loaded.
     * @throws BoundsDoesntExist Exception that happens when trying to load a area that does not exist.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    App loadArea(String areaName) throws IOException, ClassNotFoundException,BoundsDoesntExist;



    /**
     * Returns the name of the current area in the system.
     * @return the name of the current area in the system.
     */
    String getAreaName();



    /**
     * Creates a new area for the system.
     * @param name Name of the area.
     * @param topLeft Coords of the top left of the bounding rectangle (1º cell is the latitude and the 2º is the longitude).
     * @param bottomRight Coords of the bottom right of the bounding rectangle (1º cell is the latitude and the 2º is the longitude).
     * @throws AreaAlreadyExists Exception that happens when the user tries to create an area with a name already used.
     * @throws InvalidArea  Exception that happens if the user use invalid coords to create an area.
     */
    void addBounds(String name,long[] topLeft, long[] bottomRight)
        throws AreaAlreadyExists, InvalidArea;


    /**
     * Adds a new service to the area.
     * @param type Type of the service.
     * @param latitude Latitude of the service.
     * @param longitude Longitude of the service.
     * @param price Price of the service.
     * @param value Value of the service.
     * @param name Name of the service.
     * @throws InvalidServiceType Exception that happens when the type of the service is invalid.
     * @throws InvalidLocation Exception that happens when the location of the service is invalid.
     * @throws InvalidEatingPrice Exception that happens when the price given to an eating service is invalid.
     * @throws InvalidLodgingPrice Exception that happens when the price given to a lodging service is invalid.
     * @throws InvalidLeisurePrice Exception that happens when the price given to a leisure service is invalid.
     * @throws InvalidDiscountPrice Exception that happens when the discount given to the service is invalid.
     * @throws InvalidCapacity Exception that happens when the capacity given to the service is invalid.
     * @throws ServiceAlreadyExists Exception that happens when there's already a service with that name in the area.
     */
    void addService(String type, long latitude,long longitude,int price,
                   int value, String name)throws InvalidServiceType, InvalidLocation,
            InvalidEatingPrice, InvalidLodgingPrice, InvalidLeisurePrice,
            InvalidDiscountPrice, InvalidCapacity,ServiceAlreadyExists;



    /**
     * Returns the iterator of the services.
     * @return the iterator of the services.
     */
    Iterator<? extends ReadOnlyService> getServicesIterator();


    /**
     * Adds a new student to the area.
     * @param type Type of the student.
     * @param name Name of the student.
     * @param country Name of the country of the student.
     * @param homeName Name of the service representing the student's home.
     * @throws InvalidStudentType Exception that happens when the type of the student is invalid.
     * @throws ServiceDoesntExist Exception that happens when there's no service with that name.
     * @throws ServiceAlreadyFull Exception that happens when the lodging service is already full.
     * @throws StudentAlreadyExists Exception that happens when a student with that name already exists.
     */
    void addStudent(String type, String name, String country,
                    String homeName)throws InvalidStudentType,ServiceDoesntExist,
            ServiceAlreadyFull,StudentAlreadyExists;

    /**
     * Returns an iterator of the students of a given country in the area
     * or an iterator of all students in the area.
     * @param country Country of the student of all if there is no preference.
     * @throws NoStudentsFromCountry Exception that happens when there's no students from that country.
     * @return an iterator of the students of a given country in the area
     * or an iterator of all students in the area.
     */
    Iterator<? extends ReadOnlyStudent> getStudentsIterator(String country)
            throws NoStudentsFromCountry;


    /**
     * Removes a student from the area.
     * @param name Name of the student
     * @return the removed student's name.
     * @throws StudentDoesntExist Exception that happens when there's no student with that name in the area.
     */
    String removeStudent(String name)throws StudentDoesntExist;


    /**
     * Returns the student with that name.
     * @param name Name of the student
     * @throws StudentDoesntExist Exception that happens when there's no student with that name in the area.
     * @return the student with that name .
     */
    ReadOnlyStudent getStudent(String name)throws StudentDoesntExist;

    /**
     * Changes the student current location and returns true if the student is thrifty and is moving their location to
     * an eating service more expensive than the cheapest they have visited so far.
     * @param studentName Name of the student.
     * @param destinationName Name of the service where the student is going.
     * @throws ServiceDoesntExist Exception that happens when there's no service with that name in the area.
     * @throws StudentDoesntExist Exception that happens when there's no student with that name in the area.
     * @throws InvalidLocation Exception that happens when the location of the service is invalid.
     * @throws MovingToSameLocation Exception that happens when the student is moving to a location where he is already at.
     * @throws ServiceAlreadyFull Exception that happens when the service is already full.
     * @return true if the student is thrifty and is moving their location to an eating service more expensive
     * than the cheapest they have visited so far, false otherwise.
     */
     boolean changeStudentLocation(String studentName, String destinationName)throws ServiceDoesntExist,
            StudentDoesntExist,InvalidLocation,MovingToSameLocation,ServiceAlreadyFull;

    /**
     * Changes the student home.
     * Thrifty students can't move to a lodging service more expensive or with the same price as his house.
     * @param name Name of the student.
     * @param lodgingName Name of the lodging service that's the new student home.
     * @return The student involved in the process, in order to get his name and his house name,as they are registered in the system.
     * @throws ServiceDoesntExist Exception that happens when there's no service with that name in the area.
     * @throws StudentDoesntExist Exception that happens when there's no student with that name in the area.
     * @throws MovingToSameHouse Exception that happens when the student is trying to move to a lodging that is the same as his home.
     * @throws ServiceAlreadyFull Exception that happens when the service is already full.
     * @throws InvalidThriftyMove Exception that happens when a thrifty student is trying to move to a lodging that is more expensive or the same price as his house.
     */
     ReadOnlyStudent changeStudentHome(String name, String lodgingName)throws ServiceDoesntExist,StudentDoesntExist,
             MovingToSameHouse,ServiceAlreadyFull,InvalidThriftyMove;

    /**
     * Returns the service with that name.
     * @param name Name of the student
     * @throws ServiceDoesntExist Exception that happens when there's no service with that name in the area.
     * @return the student with that name.
     */
    ReadOnlyService getService(String name)throws ServiceDoesntExist ;


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
     * Returns true if the user requested the iteration in ascending order.
     * @param orderSymbol Symbol of the order requested by the user.
     * @return true if the user requested the iteration in ascending order.
     */
     boolean isAscendingOrder(String orderSymbol);



    /**
     * Returns the student.
     * @param studentName Name of the student.
     * @return the student.
     * @throws StudentDoesntExist Exception that happens when there's no student with that name in the area.
     */
    ReadOnlyStudent whereCommand(String studentName)throws StudentDoesntExist;


    /**
     * Returns the iterator of the places visited by the student.
     * @param studentName Name of the student.
     * @return the iterator of the places visited by the student.
     * @throws StudentDoesntExist Exception that happens when there's no student with that name in the area.
     * @throws StudentIsThrifty Exception that happens if the student name given in the visited command is a name of a thrifty student.
     * @throws NoPlacesVisited Exception that happens when the student didn't visit any service.
     */
     Iterator<? extends ReadOnlyService>getVisitedIterator(String studentName)throws StudentDoesntExist,
                                                                             StudentIsThrifty,NoPlacesVisited;



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
     * Meaning that the iterator will iterate an iterator that will iterate the services with 1 star, then will iterate an iterator of 2-star services
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
     * Returns an iterator of the services of a certain rating and type closer to the student.
     * Only the closest service is given, unless there's a tie.
     * @param type Type of the service.
     * @param stars Number of stars of the service(rating).
     * @param studentName Name of the student.
     * @return an iterator of the services of a certain rating and type closer to the student.
     * @throws InvalidRating Exception that happens when the number of stars given is invalid (isn't between 1 and 5).
     * @throws StudentDoesntExist Exception that happens when there's no student with that name in the area.
     * @throws InvalidServiceType Exception that happens when the type of the service is invalid.
     * @throws NoTypeServices Exception that happens when there's no services with that type in the area.
     * @throws NoTypeServicesWithThatRating Exception that happens when there's no service with that type with that rating.
     */
     Iterator<? extends ReadOnlyService> getRankedIterator(String type, int stars, String studentName)
             throws InvalidRating, StudentDoesntExist, InvalidServiceType,
             NoTypeServices,NoTypeServicesWithThatRating;


    /**
     * Returns the most relevant service of a certain type, for a specific student.
     * For thrifty students returns the less expensive service of that type(in case of a tie returns the first service inserted in the system).
     * For the other students types, returns the service of that type that has better rating
     * (in case of a tie returns the service with more time in this average).
     * @param studentName Student name.
     * @param type Type of the student
     * @return the most relevant service of a certain type, for a specific student.
     * @throws InvalidServiceType Exception that happens when the type of the service is invalid.
     * @throws StudentDoesntExist Exception that happens when there's no student with that name in the area.
     * @throws NoTypeServices Exception that happens when there's no services with that type in the area.
     */
   ReadOnlyService findMostRelevantService(String studentName, String type)throws InvalidServiceType,
           StudentDoesntExist,NoTypeServices;
}




