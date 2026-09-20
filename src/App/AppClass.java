

/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package App;

import Errors.*;

import Services.Service;
import Services.AppServices;
import Services.ReadOnlyService;
import Services.AppServicesClass;
import Services.ServicesTypes;

import Students.AppStudents;
import Students.AppStudentsClass;
import Students.ReadOnlyStudent;
import Students.StudentsTypes;
import Students.Student;

import dataStructures.Iterator;
import dataStructures.TwoWayIterator;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.FileInputStream;
import java.io.File;


/**
 * Class that makes the requests of the main (also contains the logic behind the areas).
 */
public class AppClass implements App{


    private static final long serialVersionUID = 1L;

    /**
     * Object that has the logic behind all methods that involve services.
     */
    private AppServices appServices;

    /**
     * Object that has the logic behind all methods that involve students.
     */
    private AppStudents appStudents;

    /**
     * Name of the area.
     */
    private String areaName;

    /**
     * Coords of the top left corner of the bounding rectangle(1º cell is the latitude and the 2º is the longitude).
     */
    private long[] topLeft;

    /**
     * Coords of the bottom right corner of the bounding rectangle(1º cell is the latitude and the 2º is the longitude).
     */
    private long[] bottomRight;


    /**
     * Constructor of the class AppClass.
     */
    public AppClass(){
        initClass();
    }



    /**
     * Initializes the class.
     */
    private void initClass(){
        appServices = new AppServicesClass();
        appStudents = new AppStudentsClass();
        topLeft = new long[nTypesOfCoords];
        bottomRight = new long[nTypesOfCoords];
    }




    /**
     * Returns the name of the file where the areas are stored.
     * @return the name of the file where the areas are stored.
     */
    private String getAreaFileName(String name){
        String fileName = name.toLowerCase();
        return fileName.replace(" ", "_") + AREA_FILE_TYPE;
    }


    @Override
    public boolean boundsAreDefined() {
        return areaName != null;
    }



    public void saveArea()throws IOException {
        String fileName = getAreaFileName(areaName);
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName));
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }




    @Override
    public App loadArea(String areaName) throws IOException, ClassNotFoundException,BoundsDoesntExist {
        if(!areaIsStored(areaName))
            throw new BoundsDoesntExist();

        String fileName = getAreaFileName(areaName);
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
        App app = (App)ois.readObject();
        ois.close();
        return app;
    }




    @Override
    public String getAreaName(){
        return this.areaName;
    }



    /**
     * Returns true if a area with this name is already stored.
     * @param areaName Area name;
     * @return true if an area with this name is already stored, false otherwise.
     */
    private boolean areaIsStored(String areaName) {
        File file = new File(getAreaFileName(areaName));
        return file.exists();
    }



    /**
     * Checks if there's already an area with this name.
     * @param name Name of the area to be created.
     * @return true if there's an area with this name, false otherwise.
     */
    private boolean areaAlreadyExists(String name){
        return areaIsStored(name) || name.equals(areaName);
    }



    /**
     * Check's if the area given is valid.
     * @param topLeft Coords of the top left of the bounding rectangle (1º cell is the latitude and the 2º is the longitude)
     * @param bottomRight Coords of the bottom right of the bounding rectangle
     *   (1º cell is the latitude and the 2º is the longitude).
     * @return true if the area is invalid, false if is valid.
     */
    private boolean AreaIsInvalid( long[] topLeft, long[] bottomRight){
        return topLeft[0] <= bottomRight[0] || topLeft[1] >= bottomRight[1];
    }




    @Override
    public void addBounds(String name, long[] topLeft, long[] bottomRight) throws AreaAlreadyExists, InvalidArea {
       if(areaAlreadyExists(name) )
           throw new AreaAlreadyExists();
       if(AreaIsInvalid(topLeft,bottomRight))
           throw new InvalidArea();


       if(boundsAreDefined()) {
           try {
               saveArea();
           }catch (IOException e) {
           }
           initClass();
       }

        areaName = name;
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }




    @Override
    public ReadOnlyService getService(String name)throws ServiceDoesntExist {
        return appServices.getService(name);
    }




    @Override
    public ReadOnlyStudent getStudent(String name)throws StudentDoesntExist {
        return appStudents.getStudent(name);
    }




    /**
     * Checks if the service location is outside the bounding rectangle defined for the system
     * @param latitude latitude of the service.
     * @param longitude longitude of the service.
     * @return true if the service location is outside the bounding rectangle defined for the system, false otherwise.
     */
    private boolean InvalidLocation(long latitude, long longitude){
        return latitude > topLeft[0] || latitude < bottomRight[0]
                || longitude < topLeft[1] || longitude > bottomRight[1];
    }




    @Override
    public void addService(String type, long latitude, long longitude, int price,
                           int value, String name) throws InvalidServiceType, InvalidLocation,
                           InvalidEatingPrice, InvalidLodgingPrice, InvalidLeisurePrice,
                           InvalidDiscountPrice, InvalidCapacity,ServiceAlreadyExists{

         if(ServicesTypes.invalidServiceType(type)) //has to come before invalidLocation exception.
            throw new InvalidServiceType();
         if(InvalidLocation(latitude,longitude))
             throw new InvalidLocation(null); //doesn't use the message

         appServices.addService(type,latitude,longitude,price,value,name);
    }




    @Override
    public Iterator<? extends ReadOnlyService> getServicesIterator(){
        return appServices.getServicesIterator();
    }




    @Override
    public void addStudent(String type, String name, String country, String homeName)
            throws InvalidStudentType, ServiceDoesntExist, ServiceAlreadyFull, StudentAlreadyExists {

        if (StudentsTypes.invalidStudentType(type)) //has to be the first exception.
            throw new InvalidStudentType();
        Service lodging = appServices.getService(homeName);
        appStudents.addStudent(type,name,country,lodging);
    }




    @Override
    public Iterator<? extends ReadOnlyStudent> getStudentsIterator(String country)
            throws NoStudentsFromCountry{

        return appStudents.getStudentsIterator(country);
    }




    @Override
    public String removeStudent(String name) throws StudentDoesntExist {
        return appStudents.removeStudent(name);
    }



    @Override
    public boolean changeStudentLocation(String studentName, String destinationName) throws ServiceDoesntExist,
            StudentDoesntExist, InvalidLocation, MovingToSameLocation, ServiceAlreadyFull {

        Service destination = appServices.getService(destinationName);

        return appStudents.changeStudentLocation(studentName,destination);
    }




    @Override
    public ReadOnlyStudent changeStudentHome(String name, String lodgingName) throws ServiceDoesntExist,
            StudentDoesntExist, MovingToSameHouse, ServiceAlreadyFull, InvalidThriftyMove {

        Service lodging = appServices.getService(lodgingName);

        return appStudents.changeStudentHome(name,lodging);
    }








    @Override
    public TwoWayIterator<? extends ReadOnlyStudent> getStudentsInServiceIterator(String serviceName,
                       String orderSymbol) throws InvalidOrderType, ServiceDoesntExist,
            ServiceWithNoEntryControl{

        return appServices.getStudentsInServiceIterator(serviceName,orderSymbol);
    }




    @Override
    public boolean isAscendingOrder(String orderSymbol) {
        return orderSymbol.equals(ASCENDING_ORDER_SYMBOL);
    }




    @Override
    public ReadOnlyStudent whereCommand(String studentName) throws StudentDoesntExist {
        return appStudents.whereCommand(studentName);
    }



    @Override
    public Iterator<? extends ReadOnlyService> getVisitedIterator(String studentName)
            throws StudentDoesntExist, StudentIsThrifty, NoPlacesVisited {
        return appStudents.getVisitedIterator(studentName);
    }



    @Override
    public void evaluateService(int stars, String serviceName, String description)
            throws InvalidRating, ServiceDoesntExist {
        appServices.evaluateService(stars,serviceName,description);
    }



    @Override
    public Iterator<? extends ReadOnlyService> getRankingIterator()throws NoServicesYet{
        return appServices.getRankingIterator();
    }




    @Override
    public Iterator<? extends ReadOnlyService> getServicesWithTagIterator(String tag) {
        return appServices.getServicesWithTagIterator(tag);
    }


    @Override
    public Iterator<? extends ReadOnlyService> getRankedIterator(String type, int stars, String studentName)
            throws InvalidRating, StudentDoesntExist, InvalidServiceType, NoTypeServices, NoTypeServicesWithThatRating {

        appServices.checkIfRatingIsInvalid(stars);  // this error has to comes first.

        Student student = appStudents.getStudent(studentName);

        return appServices.getRankedIterator(type,stars,student);
    }



    @Override
    public ReadOnlyService findMostRelevantService(String studentName, String type) throws InvalidServiceType,
            StudentDoesntExist, NoTypeServices {

        appServices.checkIfServiceTypeIsInvalid(type.toUpperCase()); // this error has to comes first.

        Student student = appStudents.getStudent(studentName);

        return appServices.findMostRelevantService(student,type);
    }


}
