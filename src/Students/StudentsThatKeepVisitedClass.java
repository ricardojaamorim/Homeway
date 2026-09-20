/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */


package Students;


import Errors.NoPlacesVisited;
import Services.ReadOnlyService;
import Services.Service;

import dataStructures.List;
import dataStructures.Map;
import dataStructures.SinglyLinkedList;
import dataStructures.ClosedHashTable;
import dataStructures.Iterator;


/**
 * Class of all students that keep places visited.
 */
class StudentsThatKeepVisitedClass extends StudentClass implements StudentsThatKeepVisited {  //package private.

    private static final long serialVersionUID = 1L;

    /**
     * List of places visited by the student.
     * Outgoing students keep all the places visited.
     * Bookish students keep only the leisure places.
     */
    private final List<Service> placesVisited;

    /**
     * Map of all places visited by the student.
     * @key Place's name.
     * @value Place visited(Service).
     */
    private final Map<String,Service> placesVisitedMap;

    /**
     * Constructor of the StudentsThatKeepVisitedClass.
     * @param type Type of the student.
     * @param name Name of the student.
     * @param country Name of the country of the student.
     * @param home Service representing the student's home.
     */
    public StudentsThatKeepVisitedClass(String type, String name, String country, Service home) {
        super(type, name, country, home);
        placesVisited = new SinglyLinkedList<>();
        placesVisitedMap = new ClosedHashTable<>(PLACES_VISITED_MAP_INITIAL_CAPACITY);
    }



    @Override
    public void addVisitedPlace(Service place){
        Service service = placesVisitedMap.put(place.getName().toUpperCase(), place);
        if(service == null)
            placesVisited.addLast(place);
    }



    @Override
    public Iterator<? extends ReadOnlyService> getVisitedIterator()throws NoPlacesVisited {
        if(placesVisited.isEmpty())
            throw new NoPlacesVisited(super.getName());

        return placesVisited.iterator();
    }

}
