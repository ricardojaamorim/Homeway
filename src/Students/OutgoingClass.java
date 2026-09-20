/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Students;



import Services.Service;


/**
 * Class of all outgoing students.
 */
class OutgoingClass extends StudentsThatKeepVisitedClass implements  //package private.
        Outgoing,KeepsVisitedAllPlaces {

    private static final long serialVersionUID = 1L;




    /**
     * Constructor of the OutgoingClass.
     * @param type Type of the student.
     * @param name Name of the student.
     * @param country Name of the country of the student.
     * @param home Service representing the student's home.
     */
    public OutgoingClass(String type, String name, String country, Service home) {
        super(type,name,country,home);
    }

}
