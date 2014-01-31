
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
       int finalMove = xBound*yBound;
       Node head = TourLogic.InitializeBoard(); //This builds the board list.
       Node leadWorker = null;
       Node trailWorker=null;
       Random random = new Random();
       int attempts=0;
       int startX=0;
       int startY=0;
       while (true) {
            /**Increment our attempt counter. This is purely
           informational.**/
           attempts++; 
           head = TourLogic.ClearBoard(head);
           /** for each try we're going to grab random new start X and Y points
            * to try.*/
           startX = random.nextInt(xBound)+1;
           startY = random.nextInt(yBound)+1;
           leadWorker=head;
           /** we need to loop through the list in linked order to find 
            * the right X,Y element to start this attempt. **/
           while(leadWorker!=null) {
               if (leadWorker.x == startX && leadWorker.y==startY) {
                   trailWorker=leadWorker;
                   break;
               } else {
                   leadWorker=leadWorker.next;
               }
           }
           if (leadWorker!=null) {
            leadWorker.setMoveNumber(1); //Starting square gets assigned move#1.
           }
           while (leadWorker!=null) {
               /** take the next move until we run out. movePiece handles 
                * prediction.**/
              leadWorker.visited=true; 
              trailWorker=leadWorker;
              leadWorker=TourLogic.movePiece(leadWorker);
           }
           /**Now we check our trailing worker (since leadWorker went to the 
            * edge of the list and is null) to see if he made it to the 
            * end of the tour. We've included logic to capture all the moves + 1 for a closed loop.*/
           if (trailWorker!=null && (
                (trailWorker.getMoveNumber() == finalMove && !requiredClosedLoop) 
                ||
                (trailWorker.getFinalMove() == finalMove + 1 && requiredClosedLoop))
         ) {
                break; //We made the tour, so we break our (true) loop.
           }
       }
       //Our loop is broken, so lets print our board to the console.
       System.out.println("Starting from: " + startX + "," + startY + ". Total attempts: " + attempts);
       System.out.println(TourLogic.PrintBoard(head));
       
    }
    
}
