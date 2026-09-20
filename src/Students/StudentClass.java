/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Students;

import Services.Service;

/**
 * Class of all students.
 */
abstract class StudentClass implements Student {

    private static final long serialVersionUID = 1L;

    /**
     * Type of the student.
     * needed in the output to know the type of the student.
     */
    private final String type;

    /**
     * Name of the student.
     */
    private final String name;


    /**
     * Service where the student is currently located.
     */
    private Service location;


    /**
     * Service representing the student's home.
     */
    private Service home;

    /**
     * Name of the country of the student.
     */
    private final String country;


    /**
     * Constructor of the StudentClass.
     * @param type Type of the student.
     * @param name Name of the student.
     * @param country Name of the country of the student.
     * @param home Service representing the student's home.
     */
    protected StudentClass(String type, String name, String country, Service home){
        this.type = type;
        this.name = name;
        this.country = country;
        this.home = home;
        location = home;
    }

    @Override
    public boolean equals(Object stud){
      Student student = (Student) stud;
        if(student == null || student.getName() == null
                || this.name == null)
            return false;

        return name.equalsIgnoreCase(student.getName());
    }



    @Override
    public void setLocation(Service service){
       location = service;
    }


    @Override
    public void changeHome(Service lodging){
        home = lodging;
    }


    @Override
    public String getType(){
        return type;
    }

    @Override
    public String getName(){
        return name;
    }

    @Override
    public Service getLocation(){
        return location;
    }

    @Override
    public Service getHome(){
        return home;
    }

    @Override
    public String getCountry(){
        return country;
    }
}
