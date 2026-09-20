
/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Services;

import App.App;

import Errors.InvalidEatingPrice;
import Errors.InvalidLodgingPrice;
import Errors.InvalidDiscountPrice;
import Errors.InvalidLeisurePrice;
import Errors.InvalidCapacity;
import Errors.ServiceAlreadyExists;
import Errors.InvalidOrderType;
import Errors.ServiceDoesntExist;
import Errors.ServiceWithNoEntryControl;
import Errors.InvalidRating;
import Errors.NoServicesYet;
import Errors.NoTypeServices;
import Errors.NoTypeServicesWithThatRating;
import Errors.InvalidServiceType;

import Students.ReadOnlyStudent;
import Students.Student;
import Students.Thrifty;

import dataStructures.List;
import dataStructures.Map;
import dataStructures.SinglyLinkedList;
import dataStructures.ListInArray;
import dataStructures.ClosedHashTable;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;
import dataStructures.IteratorsIterator;
import dataStructures.FilterIterator;
import dataStructures.ListHashMap;
import dataStructures.DoublyLinkedListHashMap;

import java.io.IOException;
import java.io.ObjectInputStream;


/**
 * Class that makes the student related operations.
 */
public class AppServicesClass implements AppServices {


    private static final long serialVersionUID = 1L;



    /**
     * List that contains every service in insertion order.
     */
   private final List<Service> servicesList;

    /**
     * Map that contains every service.
     * @key Service name.
     * @Value Service.
     */
   private transient Map<String,Service> services;


    /**
     * Array that contain a list of every service of the same rating.
     * Each cell contains a list of services with the rating equal to the corresponding index
     */
   private final List<ListHashMap<Service>> servicesOrderedByRating;

    /**
     * List that contains the cheaper service of each type.
     * Indexes indication in the enum ServicesTypes.
     */
    private final List<Service> cheaperServiceByType;


    /**
     * Array that contains an array of every service type that contains a list of the services of that rating and type.
     */
    private final List<List<ListHashMap<Service>>> servicesOrderedByRatingAndType;


    /**
     * Constructor of the class AppServicesClass.
     */
    public AppServicesClass() {
        servicesList = new SinglyLinkedList<>();
        servicesOrderedByRating = new ListInArray<>(SERVICES_ORDERED_BY_RATING_LENGTH);
        cheaperServiceByType = new ListInArray<>(ServicesTypes.values().length);
        services = new ClosedHashTable<>(SERVICES_MAP_INITIAL_CAPACITY);
        servicesOrderedByRatingAndType = new ListInArray<>(SERVICES_ORDERED_BY_RATING_LENGTH);
        initServicesOrderedByRating();
        initCheaperServiceByType();
        initServicesOrderedByRatingAndType();
    }


    /**
     * Manual Deserialization.
     * Only the services map is transient and rebuilt here because the other data structures need insertion order.
     * the cheaperServiceByType array is serialized by java because he is a 3 cell array.
     * @param ois Object that does the deserialization.
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        services = new ClosedHashTable<>(SERVICES_MAP_INITIAL_CAPACITY);

        Iterator<Service> it = servicesList.iterator();
        while(it.hasNext()){
            Service service = it.next();
            services.put(service.getName().toUpperCase(),service);
        }
    }


    /**
     * Initializes the lists in the servicesOrderedByRating array.
     */
    private void initServicesOrderedByRating() {
        for (int i = 0; i < SERVICES_ORDERED_BY_RATING_LENGTH; i++) {
            servicesOrderedByRating.add(i,
                    new DoublyLinkedListHashMap<>(SERVICES_WITH_CERTAIN_RATING_LIST_INITIAL_CAPACITY));
        }
    }



    /**
     * Initializes the cheaperServiceByType array.
     */
    private void initCheaperServiceByType() {
        Service eating = new EatingClass(null, null, 0, 0, Integer.MAX_VALUE, 0); //every other variable is irrelevant.
        // created in order to full every cell, so we can put the cheapest service since the beginning in the right index.
        for (int i = 0; i < ServicesTypes.values().length; i++)
            cheaperServiceByType.addLast(eating);
    }

    /**
     * Initializes the arrays and the lists inside that arrays in the servicesOrderedByRatingAndType array.
     */
    private void initServicesOrderedByRatingAndType(){
        for (int i = 0; i < SERVICES_ORDERED_BY_RATING_LENGTH; i++) {
            servicesOrderedByRatingAndType.add(i, new ListInArray<>(ServicesTypes.values().length));
            for(int j = 0; j < ServicesTypes.values().length; j++){
                servicesOrderedByRatingAndType.get(i).add(j,
                        new DoublyLinkedListHashMap<>(SERVICES_WITH_CERTAIN_RATING_AND_TYPE_LIST_INITIAL_CAPACITY));
            }
        }
    }



    /**
     * Checks if the price given is not valid.
     * @param price Price of the service.
     * @return true if the price is invalid , false if it's valid.
     */
    private boolean priceIsInvalid(int price) {
        return price <= MINIMUML_SERVICE_PRICE;
    }




    /**
     * Checks if the capacity of the service is not valid.
     * @param value Capacity of the service.
     * @return true ii the capacity is invalid, false otherwise.
     */
    private boolean invalidCapacity(int value) {
        return value <= MINIMUM_CAPACITY;
    }




    /**
     * Checks if the discount is invalid.
     * @param value Value of the discount of the service.
     * @return true if the discount is invalid, false otherwise.
     */
    private boolean invalidDiscount(int value) {
        return value < MINIMUM_DISCOUNT || value > MAXIMUM_DISCOUNT;
    }




    /**
     * Throws an exception if the price given is not valid.
     * @param type  Type of the service.
     * @param value Capacity of the service if it's an eating or lodging service or the discount of a leisure service.
     * @throws InvalidEatingPrice   Exception that happens when the price given to an eating service is invalid.
     * @throws InvalidLodgingPrice  Exception that happens when the price given to a lodging service is invalid.
     * @throws InvalidLeisurePrice  Exception that happens when the price given to a leisure service is invalid.
     * @throws InvalidDiscountPrice Exception that happens when the discount given to the service is invalid.
     * @throws InvalidCapacity      Exception that happens when the capacity given to the service is invalid.
     */
    private void checkPriceValidity(String type, int price, int value) throws InvalidEatingPrice,
            InvalidLodgingPrice, InvalidLeisurePrice, InvalidDiscountPrice, InvalidCapacity {

        switch (ServicesTypes.valueOf(type)) {
            case EATING -> {
                if (priceIsInvalid(price))
                    throw new InvalidEatingPrice();
                if (invalidCapacity(value))
                    throw new InvalidCapacity();
            }
            case LODGING -> {
                if (priceIsInvalid(price))
                    throw new InvalidLodgingPrice();
                if (invalidCapacity(value))
                    throw new InvalidCapacity();
            }
            case LEISURE -> {
                if (priceIsInvalid(price))
                    throw new InvalidLeisurePrice();
                if (invalidDiscount(value))
                    throw new InvalidDiscountPrice();
            }
        }
    }





    @Override
    public Service getService(String name)throws ServiceDoesntExist {
        Service service = services.get(name.toUpperCase());
        if(service == null)
            throw new ServiceDoesntExist();

        return service;
    }




    /**
     * get the index of the service type in an array composed by services types.
     * @param typeInUpperCase Service type in upper case.
     * if the method calls this method with the type already in uppercase, this method doesn't have to
     * do .toUpperCase operation all the time ,which grants a lesser time complexity.
     * @return the index of the service type in the cheaperServiceByType array.
     */
    private int getServiceTypeIndex(String typeInUpperCase){
        return ServicesTypes.valueOf(typeInUpperCase).
                getIndex();
    }



    /**
     * Checks if the service is cheaper than the cheapest service
     * @param service the service being checked.
     * @param type Type of the service.
     * @return true if is cheaper than the cheaper service so far, false otherwise.
     */
    private boolean isCheapestTypeService(Service service,String type){
        Service chepeastService = cheaperServiceByType.get(getServiceTypeIndex(type));

        if(chepeastService.getName() == null )
            return true;

        if(service instanceof hasDiscount)
            return ( (Leisure) service).getFinalPrice() <
                    ( (Leisure) chepeastService).getFinalPrice();

        return service.getPrice() < chepeastService.getPrice();
    }


    /**
     * Puts the service in every service structures.
     * @param service Service.
     * @param name Service name.
     * @param type Service type in upper case.
     */
    private void addServiceToStructures(Service service,String name,String type){
        servicesList.addLast(service);
        servicesOrderedByRating.get(service.getAverageRating() ).
                add(service);

        services.put(name.toUpperCase(),service);

        if(isCheapestTypeService(service, type)) {
            int index = getServiceTypeIndex(type);
            cheaperServiceByType.remove(index);
            cheaperServiceByType.add(index, service);
        }

        servicesOrderedByRatingAndType.get(service.getAverageRating() ).
                get(getServiceTypeIndex(type)).add(service);
    }



    @Override
    public void addService(String type, long latitude, long longitude, int price,
                           int value, String name) throws InvalidEatingPrice, InvalidLodgingPrice, InvalidLeisurePrice,
            InvalidDiscountPrice, InvalidCapacity,ServiceAlreadyExists {

        String typeInUpperCase = type.toUpperCase();
        checkPriceValidity(typeInUpperCase,price,value);
        Service service = services.get(name.toUpperCase());
        if(service != null)
            throw new ServiceAlreadyExists(service.getName());

        switch(ServicesTypes.valueOf(typeInUpperCase) ){
            case EATING-> service = new EatingClass(type, name, latitude,
                    longitude, price,value);
            case LODGING->service = new LodgingClass(type, name, latitude,
                    longitude, price,value);
            case LEISURE->service = new LeisureClass(type, name, latitude,
                    longitude, price,value);
        }
        addServiceToStructures(service,name,typeInUpperCase);
    }








@Override
public Iterator<? extends ReadOnlyService> getServicesIterator(){
    return servicesList.iterator();
}





@Override
public TwoWayIterator<? extends ReadOnlyStudent> getStudentsInServiceIterator(String serviceName,
      String orderSymbol) throws InvalidOrderType, ServiceDoesntExist,ServiceWithNoEntryControl{

    if(!(orderSymbol.equals(App.ASCENDING_ORDER_SYMBOL) ||
            orderSymbol.equals(App.DESCENDING_ORDER_SYMBOL) ))
        throw new InvalidOrderType();

    Service service = getService(serviceName);

    if(!(service instanceof ServiceWithEntryControl) )
        throw new ServiceWithNoEntryControl( service.getName());

    return ( (ServiceWithEntryControl) service).getOccupantsIterator();
}





@Override
public void checkIfRatingIsInvalid(int stars)throws InvalidRating{
    if(stars < MINIMUM_STAR_NUMBER || stars > MAXIMUM_STAR_NUMBER)
        throw new InvalidRating();
}



    /**
     * Updates the structures ordered by rating when a service rating is changed.
     * @param service Service with the changed rating.
     * @param oldRating Old rating.
     * @param newRating New rating.
     */
    private void updateStructuresOrderedByRating(Service service, int oldRating, int newRating){
    servicesOrderedByRating.get(oldRating) //gets the list of the services with that rating.
            .remove(service);

    servicesOrderedByRating.get(newRating).add(service);


    ListHashMap<Service> oldRatingList = servicesOrderedByRatingAndType.get(oldRating).
            get(getServiceTypeIndex(service.getType().toUpperCase()));

    oldRatingList.remove(service);

    ListHashMap<Service> newRatingList = servicesOrderedByRatingAndType.get(newRating).
            get(getServiceTypeIndex(service.getType().toUpperCase()));

    newRatingList.add(service);
}


@Override
public void evaluateService(int stars, String serviceName, String description)
        throws InvalidRating, ServiceDoesntExist {

    checkIfRatingIsInvalid(stars);

    Service service = getService(serviceName);

    int rating = service.getAverageRating();
    service.addReview(stars,description);
    int updatedRating = service.getAverageRating();

    if(rating != updatedRating)
        updateStructuresOrderedByRating(service,rating,updatedRating);
}






@Override
public Iterator<? extends ReadOnlyService> getRankingIterator() throws NoServicesYet {

    if(servicesList.isEmpty())
        throw new NoServicesYet();

    List<Iterator<Service>> itList = new ListInArray<>(SERVICES_ORDERED_BY_RATING_LENGTH);
    for(int i = MAXIMUM_STAR_NUMBER; i > ARRAYS_FIRST_INDEX ; i--){
        itList.addLast(servicesOrderedByRating.get(i).iterator() );
    }
    return new IteratorsIterator<>(itList);
}




@Override
public Iterator<? extends ReadOnlyService> getServicesWithTagIterator(String tag) {
    return new FilterIterator<>(servicesList.iterator(),new containsTag(tag));
}



/**
 * Updates the list of the closer n rating services of a certain type to the student, if given a
 * closer service than the closest before.In case of a tie more than 1 service are stored.
 * @param list List of the closer n rating services of a certain type to the student.
 * @param studentLocation Location of the student.
 * @param service Service being compared.
 */
private void updateCloserServicesList(List<Service> list,
                                       Service studentLocation,Service service){
    if(list.isEmpty())
        list.addLast(service);
    else{
        if(service.getManhattanDistance(studentLocation) <
                list.getFirst().getManhattanDistance(studentLocation)) {
            for(int i = 0; i < list.size(); i++)
                list.removeFirst(); //the list could contain more than 1 element if the distance is tied.
            list.addFirst(service);
        }
        else if(service.getManhattanDistance(studentLocation) ==
                list.getFirst().getManhattanDistance(studentLocation))
            list.addLast(service);
    }
}



    @Override
    public void checkIfServiceTypeIsInvalid(String type)throws InvalidServiceType{
        if(ServicesTypes.invalidServiceType(type))
            throw new InvalidServiceType();
    }





    /**
     * Throws an exception if there's no services with this type in the area.
     * @param type Service type.
     * @throws NoTypeServices Exception that happens when there's no services with that type in the area
     */
    private void checkIfNoTypeServices(String type)throws NoTypeServices{
          if(cheaperServiceByType.get(getServiceTypeIndex(type.toUpperCase()))
                  .getName() == null)
              throw new NoTypeServices();
    }





@Override
public Iterator<? extends ReadOnlyService> getRankedIterator(String type, int stars, Student student)
        throws InvalidServiceType, NoTypeServices, NoTypeServicesWithThatRating {

    checkIfServiceTypeIsInvalid(type);
    checkIfNoTypeServices(type);

    ListHashMap<Service> candidatesList = servicesOrderedByRatingAndType.get(stars).
           get(getServiceTypeIndex(type.toUpperCase()));

    if(candidatesList.isEmpty())
        throw new NoTypeServicesWithThatRating();

    List<Service> returnList = new SinglyLinkedList<>();

    Iterator<Service> it = candidatesList.iterator();

    Service studentLocation = student.getLocation();
    while(it.hasNext()){
        Service service = it.next();
        updateCloserServicesList(returnList,studentLocation,service);
    }
    return returnList.iterator();
}







@Override
public ReadOnlyService findMostRelevantService(Student student, String type) throws NoTypeServices {

    checkIfNoTypeServices(type);

    if (student instanceof Thrifty)
        return cheaperServiceByType.get(getServiceTypeIndex(type.toUpperCase()) );
    else {
        for (int i = MAXIMUM_STAR_NUMBER; i > ARRAYS_FIRST_INDEX; i--) {
            ListHashMap<Service> candidatesList = servicesOrderedByRatingAndType.get(i).
                    get(getServiceTypeIndex(type.toUpperCase()));

            Iterator<Service> it = candidatesList.iterator();
            if(it.hasNext())
                return it.next();
        }
    }
    return null; //never reaches here.
}

}
