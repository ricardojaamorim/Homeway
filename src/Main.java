
/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

import App.App;
import App.AppClass;
import Errors.*;
import Services.ReadOnlyService;
import Students.ReadOnlyStudent;
import dataStructures.Iterator;
import dataStructures.TwoWayIterator;

import java.io.IOException;
import java.util.Scanner;


public class Main {

    /**
     * Outputs.
     */
    private static final String UNKNOWN_COMMAND_MSG ="Unknown command. Type help to see available commands.";
    private static final String BYE_MSG ="Bye!";
    private static final String BOUNDS_CREATED_MSG ="%s created.\n";
    private static final String AREA_ALREADY_EXISTS_MSG ="Bounds already exists. Please load it!";
    private static final String INVALID_BOUNDS_MSG ="Invalid bounds.";
    private static final String SYSTEM_BOUNDS_NOT_DEFINED_MSG ="System bounds not defined.";
    private static final String SAVE_AREA_MSG ="%s saved.\n";
    private static final String BOUNDS_DOESNT_EXIST_MSG ="Bounds %s does not exists.\n";
    private static final String LOAD_AREA_MSG ="%s loaded.\n";
    private static final String SERVICE_ADDED_MSG ="%s %s added.\n";
    private static final String INVALID_SERVICE_TYPE_MSG ="Invalid service type!";
    private static final String INVALID_LOCATION_MSG ="Invalid location!";
    private static final String INVALID_EATING_PRICE_MSG ="Invalid menu price!";
    private static final String INVALID_LODGING_PRICE_MSG ="Invalid room price!";
    private static final String INVALID_LEISURE_PRICE_MSG ="Invalid ticket price!";
    private static final String INVALID_DISCOUNT_PRICE_MSG ="Invalid discount price!";
    private static final String INVALID_CAPACITY_MSG ="Invalid capacity!";
    private static final String SERVICE_ALREADY_EXISTS_MSG ="%s already exists!\n";
    private static final String SERVICES_LIST_MSG ="%s: %s (%d, %d).\n";
    private static final String NO_SERVICES_YET_MSG ="No services yet!";
    private static final String STUDENT_ADDED_MSG ="%s added.\n";
    private static final String INVALID_STUDENT_TYPE_MSG ="Invalid student type!";
    private static final String LODGING_DOESNT_EXIST_MSG ="lodging %s does not exist!\n";
    private static final String LODGING_IS_FULL_MSG ="lodging %s is full!\n";
    private static final String STUDENT_ALREADY_EXISTS_MSG ="%s already exists!\n";
    private static final String STUDENT_LEFT_MSG ="%s has left.\n";
    private static final String DOESNT_EXIST_MSG ="%s does not exist!\n";
    private static final String STUDENTS_LIST_MSG ="%s: %s at %s.\n";
    private static final String NO_STUDENTS_YET_MSG ="No students yet!";
    private static final String NO_STUDENTS_FROM_COUNTRY_MSG ="No students from %s!\n";
    private static final String STUDENT_CHANGE_LOCATION_MSG ="%s is now at %s.\n";
    private static final String DISTRACTED_STUDENT_CHANGE_LOCATION_MSG ="%s is now at %s. %s is distracted!\n";
    private static final String UnKNOWN_LOCATION_MSG ="Unknown %s!\n";
    private static final String INVALID_GO_LOCATION_MSG ="%s is not a valid service!\n";
    private static final String ALREADY_THERE_MSG ="Already there!";
    private static final String EATING_IS_FULL_MSG ="eating %s is full!\n";
    private static final String CHANGE_STUDENT_HOME_MSG ="lodging %s is now %s's home. %s is at home.\n";
    private static final String MOVING_TO_SAME_HOME_MSG ="That is %s's home!\n";
    private static final String INVALID_THRIFTY_MOVE_MSG ="Move is not acceptable for %s!\n";
    private static final String USERS_COMMAND_MSG ="%s: %s\n";
    private static final String INVALID_ORDER_TYPE_MSG ="This order does not exists!";
    private static final String SERVICE_DOESNT_CONTROL_ENTRY_MSG ="%s does not control student entry and exit!\n";
    private static final String EMPTY_SERVICE_MSG ="No students on %s!\n";
    private static final String LOCATE_STUDENT_MSG ="%s is at %s %s (%d, %d).\n";
    private static final String STUDENT_IS_THRIFTY_MSG ="%s is thrifty!\n";
    private static final String STUDENT_DIDNT_VISIT_LOCATIONS_MSG ="%s has not visited any locations!\n";
    private static final String EVALUATION_REGISTERED_MSG ="Your evaluation has been registered!";
    private static final String INVALID_EVALUATION_MSG ="Invalid evaluation!";
    private static final String NO_SERVICES_IN_SYSTEM_MSG ="No services in the system.";
    private static final String RANKING_HEADLINE_MSG ="Services sorted in descending order";
    private static final String RANKING_MSG ="%s: %d\n";
    private static final String NO_SERVICES_WHIT_THIS_TAG_MSG ="There are no services with this tag!";
    private static final String LIST_TAG_SERVICES_MSG ="%s %s\n";
    private static final String RANKED_SERVICES_HEADER_MSG ="%s services closer with %d average\n";
    private static final String INVALID_STARS_MSG ="Invalid stars!";
    private static final String NO_TYPE_SERVICES_MSG ="No %s services!\n";
    private static final String NO_TYPE_SERVICES_WITH_AVERAGE_MSG ="No %s services with average!\n";


    /**
     * Returns true if the bounds of the system are defined.
     * If the bounds are not defined prints a message.
     * @param app Class that contains all the logic behind the operations.
     * @return true if the bounds of the system are defined,false otherwise.
     */
    private static boolean boundsAreDefined(App app){
        if(!app.boundsAreDefined() ) {
            System.out.println(SYSTEM_BOUNDS_NOT_DEFINED_MSG);
            return false;
        }
        return true;

    }



    /**
     * Defines the geographic bounding rectangle of the system.
     * @param in Scanner used to get the user input.
     * @param app Class that contains all the logic behind the operations.
     */
    private static void processBounds(Scanner in,App app){
       long[] topLeft = {in.nextLong(),in.nextLong()}; //1º cell is the latitude and the 2º is the longitude.
       long[] bottomRight = {in.nextLong(),in.nextLong()};
       String areaName = in.nextLine().trim();
       try{
           app.addBounds(areaName,topLeft,bottomRight);
           System.out.printf(BOUNDS_CREATED_MSG,areaName);
       }
       catch(AreaAlreadyExists e){
           System.out.println(AREA_ALREADY_EXISTS_MSG);
       }
        catch(InvalidArea e){
            System.out.println(INVALID_BOUNDS_MSG);
        }
    }




    /**
     * Saves the current geographic bounding rectangle to a text file.
     * @param app Class that contains all the logic behind the operations.
     */
    private static void processSave(App app){
        if(boundsAreDefined(app) )
            try {
                app.saveArea();
                System.out.printf(SAVE_AREA_MSG, app.getAreaName());
            } catch (IOException e) {
            }
    }



    /**
     * Load a geographic bounding rectangle of the system.
     * @param in Scanner used to get the user input.
     * @param app Class that contains all the logic behind the operations.
     * @return the class with the loaded area
     */
    private static App processLoad(Scanner in, App app){
        String areaName = in.nextLine().trim();
        try {
                App area = app.loadArea(areaName);
                System.out.printf(LOAD_AREA_MSG, area.getAreaName());
                return area;
        }
        catch(BoundsDoesntExist e){
            System.out.printf(BOUNDS_DOESNT_EXIST_MSG, areaName);
        }
        catch(IOException e){
        }
        catch (ClassNotFoundException e){
        }
        return app;
    }


    /**
     * Adds a new service of a given type to the current geographic area.
     * @param in Scanner used to get the user input.
     * @param app Class that contains all the logic behind the operations.
     */
    private static void processService(Scanner in,App app){
       String type = in.next().toLowerCase();
       long latitude = in.nextLong();
       long longitude  = in.nextLong();
       int price = in.nextInt();
       int value = in.nextInt();
       String name = in.nextLine().trim();
        if(boundsAreDefined(app) )
            try {
                app.addService(type,latitude,longitude,price,value,name);
                System.out.printf(SERVICE_ADDED_MSG, type, name);
            } catch (InvalidServiceType e) {
                System.out.println(INVALID_SERVICE_TYPE_MSG);
            } catch (InvalidLocation e) {
                System.out.println(INVALID_LOCATION_MSG);
            } catch (InvalidEatingPrice e) {
                System.out.println(INVALID_EATING_PRICE_MSG);
            } catch (InvalidLodgingPrice e) {
                System.out.println(INVALID_LODGING_PRICE_MSG);
            } catch (InvalidLeisurePrice e) {
                System.out.println(INVALID_LEISURE_PRICE_MSG);
            } catch (InvalidDiscountPrice e) {
                System.out.println(INVALID_DISCOUNT_PRICE_MSG);
            } catch (InvalidCapacity e) {
                System.out.println(INVALID_CAPACITY_MSG);
            } catch (ServiceAlreadyExists e) {
                System.out.printf(SERVICE_ALREADY_EXISTS_MSG, e.getMessage());
            }
    }


    /**
     * Displays the list of services in the system.
     * @param app Class that contains all the logic behind the operations.
     */
    private static void processServices(App app) {
        if(boundsAreDefined(app) ) {
            Iterator<? extends ReadOnlyService> it = app.getServicesIterator();
            if (!it.hasNext())
                System.out.println(NO_SERVICES_YET_MSG);
            else {
                while (it.hasNext()) {
                    ReadOnlyService service = it.next();
                    System.out.printf(SERVICES_LIST_MSG, service.getName(), service.getType(),
                            service.getLatitude(), service.getLongitude());
                }
            }
        }
    }


    /**
     * Adds a student to the system.
     * @param in Scanner used to get the user input.
     * @param app Class that contains all the logic behind the operations.
     */
    private static void processStudent(Scanner in,App app){
        String type = in.nextLine().trim().toLowerCase();
        String name = in.nextLine().trim();
        String country = in.nextLine().trim();
        String homeName = in.nextLine().trim();
        if(boundsAreDefined(app) )
            try {
                app.addStudent(type, name, country, homeName);
                System.out.printf(STUDENT_ADDED_MSG, name);
            } catch (InvalidStudentType e) {
                System.out.println(INVALID_STUDENT_TYPE_MSG);
            } catch (ServiceDoesntExist e) {
                System.out.printf(LODGING_DOESNT_EXIST_MSG, homeName);
            } catch (ServiceAlreadyFull e) {
                System.out.printf(LODGING_IS_FULL_MSG, homeName);
            } catch (StudentAlreadyExists e) {
                System.out.printf(STUDENT_ALREADY_EXISTS_MSG, e.getMessage());
            }
    }


    /**
     * Lists all the students or those of a given country.
     * @param in Scanner used to get the user input.
     * @param app Class that contains all the logic behind the operations.
     */
    private static void processStudents(Scanner in,App app){
      String country = in.nextLine().trim();
      if(boundsAreDefined(app) ) {
          try {
              Iterator<? extends ReadOnlyStudent> it = app.getStudentsIterator(country);

              if (!it.hasNext())
                  System.out.println(NO_STUDENTS_YET_MSG);
              else
                  while (it.hasNext()) {
                      ReadOnlyStudent student = it.next();
                      System.out.printf(STUDENTS_LIST_MSG, student.getName(),
                              student.getType(), student.getLocation().getName());
                  }
          }
          catch(NoStudentsFromCountry e) {
               System.out.printf(NO_STUDENTS_FROM_COUNTRY_MSG, country);
          }
      }
    }


    /**
     * Removes a student from the system.
     * @param in Scanner used to get the user input.
     * @param app Class that contains all the logic behind the operations.
     */
    private static void processLeave(Scanner in,App app){
       String name = in.nextLine().trim();
       if(boundsAreDefined(app) )
           try {
               String studentName = app.removeStudent(name);
               System.out.printf(STUDENT_LEFT_MSG,studentName);
           } catch (StudentDoesntExist e) {
               System.out.printf(DOESNT_EXIST_MSG, name);
           }
    }


    /**
     * Changes the location of a student to a eating service, or leisure service.
     * @param in Scanner used to get the user input.
     * @param app Class that contains all the logic behind the operations.
     */
    private static void processGo(Scanner in,App app){
       String studentName = in.nextLine().trim();
       String destinationName = in.nextLine().trim();
       if(boundsAreDefined(app) )
           try {
               boolean isDistracted = app.changeStudentLocation(studentName, destinationName);
               ReadOnlyStudent student = app.getStudent(studentName); //necessary to get the student name
               if(isDistracted)                                       //and location registered in the system.
                   System.out.printf(DISTRACTED_STUDENT_CHANGE_LOCATION_MSG, student.getName(),
                           student.getLocation().getName(),student.getName());
               else
                   System.out.printf(STUDENT_CHANGE_LOCATION_MSG, student.getName(),
                           student.getLocation().getName());
           } catch (ServiceDoesntExist e) {
               System.out.printf(UnKNOWN_LOCATION_MSG, destinationName);
           } catch (StudentDoesntExist e) {
               System.out.printf(DOESNT_EXIST_MSG, studentName);
           } catch (InvalidLocation e) {
               System.out.printf(INVALID_GO_LOCATION_MSG, e.getMessage());
           } catch (MovingToSameLocation e) {
               System.out.println(ALREADY_THERE_MSG);
           } catch (ServiceAlreadyFull e) {
               System.out.printf(EATING_IS_FULL_MSG, e.getMessage());
           }
    }


    /**
     * Changes the home of a student if that is acceptable.
     * @param in Scanner used to get the user input.
     * @param app Class that contains all the logic behind the operations.
     */
    private static void processMove(Scanner in,App app){
       String name = in.nextLine().trim();
       String lodgingName = in.nextLine().trim();
       if(boundsAreDefined(app) )
           try {
               ReadOnlyStudent student = app.changeStudentHome(name, lodgingName);
               System.out.printf(CHANGE_STUDENT_HOME_MSG, student.getHome().getName(),
                                                    student.getName(), student.getName() );
           } catch (ServiceDoesntExist e) {
               System.out.printf(LODGING_DOESNT_EXIST_MSG, lodgingName);
           } catch (StudentDoesntExist e) {
               System.out.printf(DOESNT_EXIST_MSG, name);
           } catch (MovingToSameHouse e) {
               System.out.printf(MOVING_TO_SAME_HOME_MSG, e.getMessage());
           } catch (ServiceAlreadyFull e) {
               System.out.printf(LODGING_IS_FULL_MSG, e.getMessage());
           } catch (InvalidThriftyMove e) {
               System.out.printf(INVALID_THRIFTY_MOVE_MSG, e.getMessage());
           }
    }




    /**
     * Iterates the users in the service from newest to oldest based on insertion order into service.
     * @param it Iterator that will iterate.
     */
    private static void iterateUsersInAscendingOrder(TwoWayIterator<? extends ReadOnlyStudent> it){
        it.fullForward();
        while (it.hasPrevious()) {
            ReadOnlyStudent student = it.previous();
            System.out.printf(USERS_COMMAND_MSG, student.getName(), student.getType());
        }
    }



    /**
     * Iterates the users in the service from oldest to newest based on insertion order.
     * @param it Iterator that will iterate.
     */
    private static void iterateUsersInDescendingOrder(TwoWayIterator<? extends ReadOnlyStudent> it) {
        it.rewind();
        while (it.hasNext()) {
            ReadOnlyStudent student = it.next();
            System.out.printf(USERS_COMMAND_MSG, student.getName(), student.getType());
        }
    }


    /**
     * Lists all students currently in a given eating or lodging service.
     * @param in Scanner used to get the user input.
     * @param app Class that contains all the logic behind the operations.
     */
    private static void processUsers(Scanner in,App app) {
        String orderSymbol = in.next();
        String serviceName = in.nextLine().trim();
        if(boundsAreDefined(app) )
            try {
                TwoWayIterator<? extends ReadOnlyStudent> it =
                        app.getStudentsInServiceIterator(serviceName, orderSymbol);
                if (!it.hasNext())
                    System.out.printf(EMPTY_SERVICE_MSG, app.getService(serviceName).getName());
                else {
                    if (app.isAscendingOrder(orderSymbol))
                        iterateUsersInAscendingOrder(it);
                    else
                        iterateUsersInDescendingOrder(it);
                }
            } catch (InvalidOrderType e) {
                System.out.println(INVALID_ORDER_TYPE_MSG);
            } catch (ServiceDoesntExist e) {
                System.out.printf(DOESNT_EXIST_MSG, serviceName);
            } catch (ServiceWithNoEntryControl e) {
                System.out.printf(SERVICE_DOESNT_CONTROL_ENTRY_MSG, e.getMessage());
            }
    }



    /**
     * Locates a student.
     * @param in Scanner used to get the user input.
     * @param app Class that contains all the logic behind the operations.
     */
    private static void processWhere(Scanner in,App app){
       String name = in.nextLine().trim();
       if(boundsAreDefined(app) )
           try {
               ReadOnlyStudent student = app.whereCommand(name);
               ReadOnlyService service = student.getLocation();
               System.out.printf(LOCATE_STUDENT_MSG, student.getName(), service.getName(), service.getType(),
                       service.getLatitude(), service.getLongitude());
           } catch (StudentDoesntExist e) {
               System.out.printf(DOESNT_EXIST_MSG, name);
           }
    }



    /**
     * Lists the locations visited and stored by one student.
     * @param in Scanner used to get the user input.
     * @param app Class that contains all the logic behind the operations.
     */
    private static void processVisited(Scanner in,App app){
       String name = in.nextLine().trim();
       if(boundsAreDefined(app) )
           try {
               Iterator<? extends ReadOnlyService> it = app.getVisitedIterator(name);
               while (it.hasNext())
                   System.out.println(it.next().getName());
           } catch (StudentDoesntExist e) {
               System.out.printf(DOESNT_EXIST_MSG, name);
           } catch (StudentIsThrifty e) {
               System.out.printf(STUDENT_IS_THRIFTY_MSG, e.getMessage());
           } catch (NoPlacesVisited e) {
               System.out.printf(STUDENT_DIDNT_VISIT_LOCATIONS_MSG, e.getMessage());
           }
    }




    /**
     * Evaluates a service.
     * @param in Scanner used to get the user input.
     * @param app Class that contains all the logic behind the operations.
     */
    private static void processStar(Scanner in,App app){
       int stars = in.nextInt();
       String name = in.nextLine().trim();
       String description = in.nextLine().trim();
       if(boundsAreDefined(app) )
            try {
                app.evaluateService(stars, name, description);
                System.out.println(EVALUATION_REGISTERED_MSG);
            } catch (InvalidRating e) {
                System.out.println(INVALID_EVALUATION_MSG);
            } catch (ServiceDoesntExist e) {
                System.out.printf(DOESNT_EXIST_MSG, name);
            }
    }




    /**
     * Lists all services ordered by star.
     * @param app Class that contains all the logic behind the operations.
     */
    private static void processRanking(App app){
        if(boundsAreDefined(app) )
            try {
                Iterator<? extends ReadOnlyService> it = app.getRankingIterator();
                System.out.println(RANKING_HEADLINE_MSG);
                while (it.hasNext()) {
                    ReadOnlyService service = it.next();
                    System.out.printf(RANKING_MSG, service.getName(), service.getAverageRating());
                }
            } catch (NoServicesYet e) {
                System.out.println(NO_SERVICES_IN_SYSTEM_MSG);
            }
    }



    /**
     * Lists the service(s) of the indicated type with the given score that are closer to
     * the student location.
     * @param in Scanner used to get the user input.
     * @param app Class that contains all the logic behind the operations.
     */
    private static void processRanked(Scanner in,App app){
       String type = in.next();
       int stars = in.nextInt();
       String studentName = in.nextLine().trim();
       if(boundsAreDefined(app) )
            try {
                Iterator<? extends ReadOnlyService> it = app.getRankedIterator(type, stars, studentName);
                System.out.printf(RANKED_SERVICES_HEADER_MSG, type, stars);
                while(it.hasNext())
                    System.out.println(it.next().getName());
            } catch (InvalidRating e) {
                System.out.println(INVALID_STARS_MSG);
            } catch (StudentDoesntExist e) {
                System.out.printf(DOESNT_EXIST_MSG, studentName);
            } catch (InvalidServiceType e) {
                System.out.println(INVALID_SERVICE_TYPE_MSG);
            } catch (NoTypeServices e) {
                System.out.printf(NO_TYPE_SERVICES_MSG, type);
            } catch (NoTypeServicesWithThatRating e) {
                System.out.printf(NO_TYPE_SERVICES_WITH_AVERAGE_MSG, type);
            }
    }


    /**
     * List all services that contains the specified tag in reviews.
     * @param in Scanner used to get the user input.
     * @param app Class that contains all the logic behind the operations.
     */
    private static void processTag(Scanner in,App app){
       String tag = in.nextLine().trim();
       if(boundsAreDefined(app) ) {
           Iterator<? extends ReadOnlyService> it = app.getServicesWithTagIterator(tag);
           if (!it.hasNext())
               System.out.println(NO_SERVICES_WHIT_THIS_TAG_MSG);
           else {
               while (it.hasNext()) {
                   ReadOnlyService service = it.next();
                   System.out.printf(LIST_TAG_SERVICES_MSG, service.getType(),
                           service.getName());
               }
           }
       }
    }


    /**
     * Finds the most relevant service of a certain type, for a specific student.
     * @param in Scanner used to get the user input.
     * @param app Class that contains all the logic behind the operations.
     */
    private static void processFind(Scanner in,App app){
       String name = in.nextLine().trim();
       String type = in.next();
       if(boundsAreDefined(app) )
           try {
               ReadOnlyService service = app.findMostRelevantService(name, type);
               System.out.println(service.getName());
           } catch (InvalidServiceType e) {
               System.out.println(INVALID_SERVICE_TYPE_MSG);
           } catch (StudentDoesntExist e) {
               System.out.printf(DOESNT_EXIST_MSG, name);
           } catch (NoTypeServices e) {
               System.out.printf(NO_TYPE_SERVICES_MSG, type);
           }
    }






    /**
     * Prints a sequence of messages that describe every command.
     */
    private static void processHelp(){
      for(Commands command : Commands.values() ){
          System.out.println(command.getDescription() );
      }
    }

    /**
     * Process the command "EXIT".
     */
    private static void processExit(App app){
        try {
            app.saveArea();
            System.out.println(BYE_MSG);
        }
        catch (IOException e) {
        }
    }


    /**
     * Runs the app.
     * @param in Scanner used to get the user input.
     * @param systemApp Class that contains all the logic behind the operations.
     */
    private static void runApp(Scanner in, App systemApp){
        boolean endApp = false;
        App app = systemApp;
        while(!endApp){
            String command = in.next().toUpperCase();
            try {
                 switch (Commands.valueOf(command)) {
                    case BOUNDS -> processBounds(in,app);
                    case SAVE -> processSave(app);
                    case LOAD -> app = processLoad(in, app);
                    case SERVICE -> processService(in,app);
                    case SERVICES -> processServices(app);
                    case STUDENT -> processStudent(in,app);
                    case STUDENTS -> processStudents(in,app);
                    case LEAVE -> processLeave(in,app);
                    case GO -> processGo(in,app);
                    case MOVE -> processMove(in,app);
                    case USERS -> processUsers(in,app);
                    case STAR -> processStar(in,app);
                    case WHERE -> processWhere(in,app);
                    case VISITED -> processVisited(in,app);
                    case RANKING -> processRanking(app);
                    case RANKED -> processRanked(in,app);
                    case TAG -> processTag(in,app);
                    case FIND -> processFind(in,app);
                    case HELP -> processHelp();
                    case EXIT -> {processExit(app);
                                  endApp = true;}
                }
            }catch(IllegalArgumentException invalidCommand){
                System.out.println(UNKNOWN_COMMAND_MSG);
            }
        }
    }


    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        App systemApp = new AppClass();
        runApp(in,systemApp);
        in.close();
    }
}


