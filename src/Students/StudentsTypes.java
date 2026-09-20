/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */


package Students;

/**
 * Enum of every student type.
 */
public enum StudentsTypes {
    BOOKISH,THRIFTY,OUTGOING;

    /**
     * Constructor of the StudentsTypes enum.
     */
    StudentsTypes(){}


    /**
     * Checks if the type of the student is invalid.
     * @param type type of the student.
     * @return true if the type is invalid, false otherwise.
     */
    public static boolean invalidStudentType(String type) {
        for(StudentsTypes studentType : StudentsTypes.values())
            if(studentType.name().equalsIgnoreCase(type) )
                return false;
        return true;
    }

}
