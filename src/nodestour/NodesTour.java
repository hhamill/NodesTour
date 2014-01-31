
package nodestour;
import java.util.Random;
/**
 *
 * @author harrison hamill
 */
public class NodesTour {
    /**
     * Set these variables to change the program.
     * xBound and yBound are the respective x and y size of our board.
     * requiredClosedLoop determines if the tour must end on the same square
     * it started. Note: This option set to true will take longer, especially
     * on a larger board.
     */
    static int xBound = 8;
    static int yBound = 8;
    static boolean requiredClosedLoop=false;
    
    public static void main(String[] args) {
       Tour tour = TourLogic.RunTour();
       if (tour!=null) {
        
        System.out.println("Starting from: " + tour.startX + "," + tour.startY + ". Total attempts: " + tour.attempts);
        System.out.println(TourLogic.PrintBoard(tour.head));
       }
       
    }
    
}
