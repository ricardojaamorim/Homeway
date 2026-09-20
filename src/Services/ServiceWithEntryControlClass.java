/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Services;

import Errors.ServiceAlreadyFull;
import Students.ReadOnlyStudent;
import Students.Student;
import dataStructures.TwoWayIterator;


import dataStructures.ListHashMap;
import dataStructures.DoublyLinkedListHashMap;

/**
 * Class of the services that control the students entry.
 */
class ServiceWithEntryControlClass extends ServiceClass implements ServiceWithEntryControl { //package private.

    private static final long serialVersionUID = 1L;

    /**
     * Capacity of the service.
     */
    private final int capacity;

    /**
     * Map of every student in the service.
     * @key student name.
     * @value Student.
     */
    private final ListHashMap<Student> occupants;

    /**
     * Constructor of ServiceWithEntryControlClass.
     * @param type Type of the service.
     * @param latitude Latitude of the service.
     * @param longitude Longitude of the service.
     * @param price Price of the service.
     * @param value Capacity of the service.
     * @param name Name of the service.
     */
    public ServiceWithEntryControlClass(String type, String name, long latitude, long longitude,
                                        int price, int value) {
        super(type, name, latitude, longitude, price);
        capacity = value;
        occupants = new DoublyLinkedListHashMap<>(OCCUPANTS_MAP_INITIAL_CAPACITY);
    }


    @Override
    public void addOccupant(Student student){
        occupants.add(student);
    }


    @Override
    public void removeOccupant(Student student){
            occupants.remove(student);
    }



    @Override
    public void isFull()throws ServiceAlreadyFull{
        if(getOccupantsCount() >= capacity)
            throw new ServiceAlreadyFull(super.getName());
    }



    @Override
    public int getCapacity() {
        return capacity;
    }


    @Override
    public boolean hasNoOccupants(){
        return getOccupantsCount() == 0;
    }


    @Override
    public int getOccupantsCount(){return occupants.size();}



    @Override
    public TwoWayIterator<? extends ReadOnlyStudent> getOccupantsIterator(){
        return occupants.twoWayiterator();
    }

}
