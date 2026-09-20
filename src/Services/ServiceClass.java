/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Services;


import dataStructures.SinglyLinkedList;
import dataStructures.Iterator;
import dataStructures.List;

/**
 * Class of all services.
 */
abstract class ServiceClass implements Service {

    private static final long serialVersionUID = 1L;

    /**
     * Type of the service(needed in the output to know the type of the service).
     */
    private final String type;

    /**
     * Name of the service.
     */
    private final String name;

    /**
     * Latitude of the service.
     */
    private final long latitude;

    /**
     * Longitude of the service.
     */
    private final long longitude;

    /**
     * Price of the service.
     */
    private final int price;

    /**
     * Average rating of the service.
     */
    private float averageRating;

    /**
     * List of the service reviews.
     */
    private final List<Review> reviews;


    /**
     * Constructor of ServiceClass.
     * @param type Type of the service.
     * @param latitude Latitude of the service.
     * @param longitude Longitude of the service.
     * @param price Price of the service.
     * @param name Name of the service.
     */
    protected ServiceClass(String type, String name, long latitude, long longitude,
                           int price) {
        this.type = type;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        averageRating = INITIAL_SERVICE_RATING;
        reviews = new SinglyLinkedList<>();
    }



    @Override
    public boolean equals(Object serv){
       Service service = (Service) serv;
        if(service == null || service.getName() == null)
            return false;

        return name.equalsIgnoreCase(service.getName());
    }



    /**
     * Updates the overall rating of the service.
     * @param newRating Number of stars given in the evaluation.
     */
    private void updateRating(int newRating){
       averageRating = (averageRating * (getEvaluationCount() + INITIAL_RATINGS) + newRating)
        / (getEvaluationCount() + INITIAL_RATINGS + THIS_EVALUATION);
    }



    @Override
    public void addReview(int stars, String description){
        updateRating(stars);
        Review review = new ReviewClass(stars,description);
        reviews.addFirst(review); //review order doesn't matter and doing this way it can add with a O(1) time complexity,
    }                             //using a list (SinglyLinkedList) with less space complexity.


    /**
     * Returns true if the service doesn't have reviews.
     * Having this as a method might be usefully for future updates.
     * @return true if the service doesn't have reviews, false otherwise.
     */
    private boolean doesntHaveReviews(){
        return reviews.isEmpty();
    }



    @Override
    public boolean hasTag(String tag){
        if(doesntHaveReviews())
            return false;

        Iterator<Review> it = reviews.iterator();
        while(it.hasNext()){
            if(it.next().kmpSearch(tag.toCharArray()))
                return true;
        }
        return false;
    }



    @Override
    public long getManhattanDistance(Service service){
        return Math.abs(latitude - service.getLatitude())
                + Math.abs(longitude - service.getLongitude());
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
    public long getLatitude(){
        return latitude;
    }



    @Override
    public long getLongitude(){
        return longitude;
    }



    @Override
    public int getPrice(){
        return price;
    }



    @Override
    public int getAverageRating(){
        return Math.round(averageRating);
    }



    @Override
    public int getEvaluationCount(){
        return reviews.size();
    }



}
