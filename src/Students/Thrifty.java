/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */


package Students;

import Errors.InvalidThriftyMove;
import Services.Service;

/**
 * Interface of all thrifty students.
 */
public interface Thrifty extends Student {

    /**
     * Checks if the price of the eating service is more expensive than the cheapest one visited so far by the student,
     * and updates the cheapest price seen, if is cheaper.
     * @param price Price of the eating service
     * @return true if the price of the eating service is more expensive than the cheapest one visited so far by the student,
     * false otherwise.
     */
    boolean isMoreExpensiveThanCheapestEating(int price);



    /**
     * Returns the cheapest price of an eating service visited so far.
     * @return the cheapest price of an eating service visited so far.
     */
    int getCheapestEatingPrice();

    /**
     * Throws an exception is the student is trying to make an invalid home change.
     * @param lodging lodging that the student is trying to move to.
     * @throws InvalidThriftyMove Exception that happens when a thrifty student is trying to move to a lodging
     * that is more expensive or the same price as his house.
     */
    void checkMoveValidity(Service lodging) throws InvalidThriftyMove;
}
