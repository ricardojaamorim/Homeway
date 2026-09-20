/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */


package Students;

import Errors.InvalidThriftyMove;
import Services.Service;


/**
 * Class of all thrifty students.
 */
class ThriftyClass extends StudentClass implements Thrifty {  //package private.

    private static final long serialVersionUID = 1L;

    /**
     * Price of the cheapest Eating service visited so far.
     */
   private int cheapestEatingPrice;

    /**
     * Constructor of the ThriftyClass.
     * @param type Type of the student.
     * @param name Name of the student.
     * @param country Name of the country of the student.
     * @param home Service representing the student's home.
     */
    public ThriftyClass(String type, String name, String country, Service home) {
        super(type,name,country,home);
        cheapestEatingPrice = Integer.MAX_VALUE;
    }

    @Override
    public boolean isMoreExpensiveThanCheapestEating(int price){
        if(price <= cheapestEatingPrice){
            cheapestEatingPrice = price;
            return false;
        }
        return true;
    }




    @Override
    public int getCheapestEatingPrice(){
        return cheapestEatingPrice;
    }

    @Override
    public void checkMoveValidity(Service lodging) throws InvalidThriftyMove {
        if(lodging.getPrice() >= super.getHome().getPrice())
            throw new InvalidThriftyMove(super.getName());
    }


}
