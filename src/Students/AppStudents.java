
/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */


package Students;

import Errors.*;
import Services.ReadOnlyService;
import Services.Service;
import dataStructures.Iterator;

import java.io.Serializable;

/**
 * Interface of the student related operations.
 */
public interface AppStudents extends Serializable {

    /**
     * Constants.
     */
    final String ALL_COUNTRIES = "ALL";

    final int Students_MAP_INITIAL_CAPACITY = 1000;

    final int COUNTRIES_MAP_INITIAL_CAPACITY = 30; //30 different nationalities should be realistic.

    final int STUDENTS_PER_COUNTRY_LIST_INITIAL_CAPACITY = 60;

    /**
     * Returns the student with that name.
     * @param name Name of the student
     * @throws StudentDoesntExist Exception that happens when there's no student with that name in the area.
     * @return the student with that name.
     */
    Student getStudent(String name)throws StudentDoesntExist;

    /**
     * Adds a new student to the area.
     * @param type Type of the student.
     * @param name Name of the student.
     * @param country Name of the country of the student.
     * @param home Service representing the student's home.
     * @throws ServiceDoesntExist Exception that happens when there's no service with that name.
     * @throws ServiceAlreadyFull Exception that happens when the lodging service is already full.
     * @throws StudentAlreadyExists Exception that happens when a student with that name already exists.
     */
    void addStudent(String type, String name, String country,
                    Service home)throws ServiceDoesntExist,
            ServiceAlreadyFull, StudentAlreadyExists;



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
     * Changes the student current location and returns true if the student is thrifty and is moving their location to
     * an eating service more expensive than the cheapest they have visited so far.
     * @param studentName Name of the student.
     * @param destination Service where the student is going.
     * @throws StudentDoesntExist Exception that happens when there's no student with that name in the area.
     * @throws InvalidLocation Exception that happens when the location of the service is invalid.
     * @throws MovingToSameLocation Exception that happens when the student is moving to a location where he is already at.
     * @throws ServiceAlreadyFull Exception that happens when the service is already full.
     * @return true if the student is thrifty and is moving their location to an eating service more expensive
     * than the cheapest they have visited so far, false otherwise.
     */
    boolean changeStudentLocation(String studentName, Service destination)throws StudentDoesntExist,
            InvalidLocation,MovingToSameLocation,ServiceAlreadyFull;


    /**
     * Changes the student home.
     * Thrifty students can't move to a lodging service more expensive or with the same price as his house.
     * @param name Name of the student.
     * @param lodging Lodging service that's the new student home.
     * @return The student involved in the process, in order to get his name and his house name,as they are registered in the system.
     * @throws StudentDoesntExist Exception that happens when there's no student with that name in the area.
     * @throws MovingToSameHouse Exception that happens when the student is trying to move to a lodging that is the same as his home.
     * @throws ServiceAlreadyFull Exception that happens when the service is already full.
     * @throws InvalidThriftyMove Exception that happens when a thrifty student is trying to move to a lodging that is more expensive or the same price as his house.
     */
    ReadOnlyStudent changeStudentHome(String name, Service lodging)throws StudentDoesntExist,
            MovingToSameHouse, ServiceAlreadyFull, InvalidThriftyMove;


    /**
     * Returns the student.
     * Has to be a method of his own because the getStudent can't throw the StudentDoesntExist exception
     * because it's used in the addStudent method(where a student with that name is expected not to exist).
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
}
