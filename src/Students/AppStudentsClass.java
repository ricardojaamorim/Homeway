
/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Students;


import Errors.StudentDoesntExist;
import Errors.StudentAlreadyExists;
import Errors.ServiceAlreadyFull;
import Errors.ServiceDoesntExist;
import Errors.NoStudentsFromCountry;
import Errors.InvalidLocation;
import Errors.MovingToSameLocation;
import Errors.MovingToSameHouse;
import Errors.InvalidThriftyMove;
import Errors.StudentIsThrifty;
import Errors.NoPlacesVisited;

import Services.ServiceWithEntryControl;
import Services.Lodging;
import Services.Service;
import Services.Leisure;
import Services.Eating;
import Services.StudentCantGoThere;
import Services.ValidGoServiceWithEntryControl;
import Services.ReadOnlyService;

import dataStructures.Map;
import dataStructures.SortedMap;
import dataStructures.SepChainHashTable;
import dataStructures.RBSortedMap;
import dataStructures.Iterator;
import dataStructures.ListHashMap;
import dataStructures.DoublyLinkedListHashMap;

import java.io.IOException;
import java.io.ObjectInputStream;

/**
 * Class that makes the student related operations.
 */
public class AppStudentsClass implements AppStudents {

    private static final long serialVersionUID = 1L;

    /**
     * Map with all students in the area.
     * @key Name of the student.
     * @value Student.
     */
    private final Map<String,Student> students;


    /**
     * Sorted Map of all the students in the area in alphabetic order.
     * @key Name of the student.
     * @value Student.
     */
    private transient SortedMap<String,Student> studentsByName;

    /**
     * Map that contains a list of every student of a certain nationality(country of origin).
     * @key Name of the country.
     * @value List of students of that country in insertion order.
     */
    private final Map<String,ListHashMap<Student>> studentsByCountry;


    /**
     * Constructor of the class AppStudentsClass.
     */
   public AppStudentsClass(){
       students = new SepChainHashTable<>(Students_MAP_INITIAL_CAPACITY);
       studentsByName = new RBSortedMap<>();
       studentsByCountry = new SepChainHashTable<>(COUNTRIES_MAP_INITIAL_CAPACITY);
   }





    /**
     * Manual Deserialization.
     * Only studentsByName is transient, because deserializing the maps would implicate rehashing which costs
     * more time than just doing the java deserialization.
     * @param ois Object that does the deserialization.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        studentsByName = new RBSortedMap<>();

        Iterator<Map.Entry<String,Student>> it = students.iterator();
        while(it.hasNext()){
            Map.Entry<String,Student> entry = it.next();
            studentsByName.put(entry.key(),entry.value());
        }
    }



    @Override
    public Student getStudent(String name)throws StudentDoesntExist {
        Student student = students.get(name.toUpperCase());
        if(student == null)
            throw new StudentDoesntExist();

        return student;
    }




    /**
     * Puts the student in every student structures.
     * @param home Student's new home.
     * @param student Student.
     * @param name Student's name.
     * @param country Student's country.
     */
    private void addStudentToStructures(ServiceWithEntryControl home, Student student,
                                        String name,String country){
        students.put(name.toUpperCase(), student);

        studentsByName.put(name.toUpperCase(), student);

        String countryInUpperCase = country.toUpperCase();

        if(studentsByCountry.get(countryInUpperCase) == null)
            studentsByCountry.put(countryInUpperCase,
                    new DoublyLinkedListHashMap<>(STUDENTS_PER_COUNTRY_LIST_INITIAL_CAPACITY));
        studentsByCountry.get(countryInUpperCase).add(student);

        home.addOccupant(student);
    }

    @Override
    public void addStudent(String type, String name, String country, Service home)
            throws ServiceDoesntExist, ServiceAlreadyFull, StudentAlreadyExists {

        if ( !(home instanceof Lodging) )
            throw new ServiceDoesntExist();

        ((ServiceWithEntryControl) home).isFull();

        Student student = students.get(name.toUpperCase());
        if (student != null)
            throw new StudentAlreadyExists(student.getName());

        switch (StudentsTypes.valueOf(type.toUpperCase())) {
            case BOOKISH -> student = new BookishClass(type,name,country,home);

            case OUTGOING ->{ student = new OutgoingClass(type, name, country, home);
                            ( (StudentsThatKeepVisited)student).addVisitedPlace(home);
            }
            case THRIFTY -> student = new ThriftyClass(type,name,country,home);
        }
        addStudentToStructures( (ServiceWithEntryControl)home, student, name, country);
    }




    @Override
    public Iterator<? extends ReadOnlyStudent> getStudentsIterator(String country)throws NoStudentsFromCountry {

        if(country.equalsIgnoreCase(ALL_COUNTRIES))
            return studentsByName.values();
        else{
            ListHashMap<Student> countryList = studentsByCountry.get(country.toUpperCase());
            if(countryList == null)
                throw new NoStudentsFromCountry();

            return countryList.iterator();
        }
    }



    @Override
    public String removeStudent(String name) throws StudentDoesntExist {
       String nameInUpperCase = name.toUpperCase();
       Student student = students.remove(nameInUpperCase);

        if(student == null)
            throw new StudentDoesntExist();

        studentsByName.remove(nameInUpperCase);

        ListHashMap<Student> countryList = studentsByCountry.get(student.getCountry().toUpperCase());
        countryList.remove(student);
        if(countryList.isEmpty())
            studentsByCountry.remove(student.getCountry().toUpperCase());

       ( (ServiceWithEntryControl)student.getHome() ).removeOccupant(student);

       if( !(student.getLocation().equals(student.getHome()) ) &&
        student.getLocation() instanceof ServiceWithEntryControl service)
           service.removeOccupant(student);

       return student.getName();
    }


    /**
     * Updates the list of occupants of the destination and original location of the student.
     * @param student Student.
     * @param destination Service where the student is going.
     */
    private void updateServicesOccupants(Student student, Service destination ){
        if(student.getLocation() instanceof ServiceWithEntryControl service
                && !(student.getLocation() instanceof Lodging) )
            service.removeOccupant(student);

        if(destination instanceof ServiceWithEntryControl service)
            service.addOccupant(student);
    }


    @Override
    public boolean changeStudentLocation(String studentName, Service destination) throws StudentDoesntExist,
            InvalidLocation, MovingToSameLocation, ServiceAlreadyFull {

        Student student = getStudent(studentName);

        if(destination instanceof StudentCantGoThere)
            throw new InvalidLocation(destination.getName());
        if(student.getLocation().equals(destination))
            throw new MovingToSameLocation();
        if(destination instanceof ValidGoServiceWithEntryControl)
            ((ServiceWithEntryControl)destination ).isFull();

        updateServicesOccupants(student,destination);

        student.setLocation(destination);

        if(student instanceof StudentsThatKeepVisited person){
            if(person instanceof KeepsVisitedOnlyLeisure
                    && destination instanceof Leisure
                    || person instanceof KeepsVisitedAllPlaces)
                person.addVisitedPlace(destination);
        }

        return student instanceof Thrifty thrifty && destination instanceof Eating
        && thrifty.isMoreExpensiveThanCheapestEating(destination.getPrice()) ;
    }






    @Override
    public ReadOnlyStudent changeStudentHome(String name, Service lodging) throws StudentDoesntExist,
            MovingToSameHouse, ServiceAlreadyFull, InvalidThriftyMove {

        Student student = getStudent(name);

        if(lodging.equals(student.getHome() ))
            throw new MovingToSameHouse(student.getName());

      ( (ServiceWithEntryControl) lodging).isFull();

        if(student instanceof Thrifty thrifty)
            thrifty.checkMoveValidity(lodging);

        ( (ServiceWithEntryControl)student.getHome() ).removeOccupant(student);
        ( (ServiceWithEntryControl)lodging).addOccupant(student);

        student.setLocation(lodging);
        student.changeHome(lodging);

        if(student instanceof KeepsVisitedAllPlaces)
            ( (StudentsThatKeepVisited) student).addVisitedPlace(lodging);

        return student;
    }



    @Override
    public ReadOnlyStudent whereCommand(String studentName) throws StudentDoesntExist {
       return getStudent(studentName);
    }



    @Override
    public Iterator<? extends ReadOnlyService> getVisitedIterator(String studentName) throws StudentDoesntExist,
                                                                 StudentIsThrifty, NoPlacesVisited {

        Student student = getStudent(studentName);

        if(student instanceof Thrifty)  // the error happens if the student is thrifty, that's why
            throw new StudentIsThrifty(student.getName());  // it's not !(student instance of StudentsThatKeepVisited).

        return ( (StudentsThatKeepVisited)student ).getVisitedIterator();
    }


}
