/**
 * @author Vicente Santos 71471 vr.santos@campus.fct.unl.pt
 * @author Ricardo Amorim 71365 rja.amorim@campus.fct.unl.pt
 */

package Services;


/**
 * Class of the service reviews.
 */
class ReviewClass implements Review { //package private.

    private static final long serialVersionUID = 1L;

    /**
     * Rating (number of stars) of the review.
     */
    private final int stars;


    /**
     * Description of the review.
     */
    private final String description;


    /**
     * Constructor of the ReviewClass.
     * @param stars Rating (number of stars) of the review.
     * @param description Description of the review.
     */
    public ReviewClass(int stars, String description){
        this.stars = stars;
        this.description = EMPTY_SPACE + description.toUpperCase() + EMPTY_SPACE;
    }

    /**
     * Creates the lps array used in KmpSearch.
     * @param pattern Tag.
     * @return the lps array.
     */
    private static int[] LPS(char[] pattern) {
        int m = pattern.length;
        int[] lps = new int[m];

        int i = 1;
        int j = 0;
        while(i < m){
          if(pattern[i] == pattern[j]) {
              j++;
              lps[i] = j;
              i++;
          }
          else{
              if(j != 0)
                  j = lps[j - 1];
              else
                  i++;
          }
        }
        return lps;
    }


    @Override
    public boolean kmpSearch(char[] pattern) {
        char[] text = description.toCharArray();
        int n = text.length;
        int m = pattern.length;
        int[] lps = LPS(pattern);
        int i = 0, j = 0;
        while (i < n) {
            if (pattern[j] == text[i]) {
                i++;
                j++;
            }
            if (j == m) //found
                return true;
            if (i < n && pattern[j] != text[i]) {
                if (j != 0)
                    j = lps[j - 1]; //reuse suffix of P[0..j-1]
                else
                    i++;
            }
        }
        return false;
    }




    @Override
    public int getStars() {
        return stars;
    }


    @Override
    public String getDescription() {
        return description;
    }


}
