package nodestour;
import java.util.Random;
/**
 *This class extends NodesTour main class, and acts like a module, holding
 * static methods for use in the body of the application.
 * @author harrison hamill
 */
public class TourLogic extends NodesTour {
    /**
     * This is the main method of our tour, which builds the board, and runs
     * the simulation to find the path. 
     * @return Tour object, which contains the Head of the board linked list
     * as well as some information data like where the successful tour started
     * (x,y) and how many attempts it took to find the successful path.
     */
    public static Tour RunTour() {
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
       //Our loop is broken, so lets return our tour data.
       Tour tour = new Tour();
       tour.head=head;
       tour.startX=startX;
       tour.startY=startY;
       tour.attempts=attempts;
       return tour;
    }
    
    /**
     * This function builds our linked list based on the dimensions requested, 
     * and sets the "move" link properties, and returns the head of the list.
     * @return Node Head (head of our board list)
     */
    public static Node InitializeBoard() {
        Node head = null;
        Node workingNode=null;
        for(int y=1; y<xBound+1;y++) {
            for (int x=1; x<yBound+1; x++) {
                Node newNode = new Node(x, y, null);
                if (head==null) {
                    head = newNode;
                }
                if (workingNode==null) {
                    workingNode=newNode;
                } else {
                    workingNode.next=newNode;
                    workingNode=newNode;
                }
            }
            
        }
        workingNode=head;
        while(workingNode!=null) {
           CheckNodeFriends(workingNode, head);
           workingNode = workingNode.next;
        }
        return head;
    }
    /**
     * Clear the per try properties of each node.
     * @param Node head (head of the list).
     * @return Node head (same list but changed.)
     */
    public static Node ClearBoard(Node head) {
        Node workingNode=head;
        while (workingNode!=null) {
            workingNode.visited=false;
            workingNode.setMoveNumber(0);
            workingNode.setFinalMove(0);
            workingNode=workingNode.next;
        }
        return head;
    }
    /**
     * For use in building our linked board. Sets the 8 potential moves (if they're in bounds) for the passed in node.
     * @param Node node (The node to set the move links for).
     * @param Node head (we need the head of the list or we can't move around to check other nodes.)
     */
    public static void CheckNodeFriends(Node node, Node head) {
        if (node==head) head=head.next;
       
        while (head!=null) {
         
                if (node.x + 1 <= xBound && node.y+2<= yBound && head.x == node.x + 1  && head.y == node.y + 2) {
                    node.setSse(head);
                }
                if (node.x + 1 <= xBound && node.y-2 > 0 && head.x == node.x + 1 && head.y == node.y - 2) {
                    node.setNne(head);
                }
                
                if (node.x - 1 >0 && node.y+2<= yBound && head.x == node.x - 1 && head.y == node.y + 2) {
                    node.setSsw(head);
                }
                if (node.x - 1 >0 && node.y-2>0 && head.x == node.x - 1 && head.y == node.y - 2) {
                    node.setNnw(head);
                }
                
                if (node.x + 2 <= xBound && node.y+1<= yBound && head.x == node.x + 2 && head.y == node.y + 1) {
                    node.setSee(head);
                }
                if (node.x + 2 <= xBound && node.y-1>0 && head.x == node.x + 2 && head.y == node.y - 1) {
                    node.setNee(head);
                }
                if (node.x - 2 > 0 && node.y - 1 > 0 && head.x == node.x - 2 && head.y == node.y - 1) {
                    node.setNww(head);
                }
                if (node.x - 2 > 0 && node.y+1 <= yBound &&head.x == node.x - 2 && head.y == node.y + 1) {
                    node.setSww(head);
                }
                head = head.next;
            }
    }
    /**
     * Writes a complete printout of each node in the board, including their linked Moves to the console. Diagnostic use only.
     * @param Node head (front of the list).
     */
    public static void printStatus(Node head) {
        
       while(head!=null) {
           System.out.println("Examining node " + head.x + " " + head.y + " which has " + (head.visited ? "been" : "not been") + " visited " + (head.getMoveNumber() > 0 ? Integer.toString(head.getMoveNumber()) : ""));
           System.out.println("\t" 
           + (head.getNnw()!=null ? "nnw: " + head.getNnw().x + head.getNnw().y : "nnw blank.") + " "
           + (head.getNne()!=null ? "nne: " + head.getNne().x + head.getNne().y : "nne blank.") + " "
           + (head.getNee()!=null ? "nee: " + head.getNee().x + head.getNee().y : "nee blank.") + " "
           + (head.getSee()!=null ? "see: " + head.getSee().x + head.getSee().y : "see blank.") + " "
           + (head.getSse()!=null ? "sse: " + head.getSse().x + head.getSse().y : "sse blank.") + " "
           + (head.getSsw()!=null ? "ssw: " + head.getSsw().x + head.getSsw().y : "ssw blank.") + " "
           + (head.getSww()!=null ? "sww: " + head.getSww().x + head.getSww().y : "sww blank.") + " "
           + (head.getNww()!=null ? "nww: " + head.getNww().x + head.getNww().y : "nww blank.") + " "
           );
           
           head = head.next;
       } 
    }
    /**
     * 
     * @param Node node - the current node that you wish to move from.
     * @return Node moved node. Returns the node which will be the next move.
     */
    
    public static Node movePiece(Node node) {
        Node nextMove=null;
        int bestMove=9;
        int outside= Math.round((xBound + yBound)/4);
        int finalMove = xBound*yBound;
        Random rnd = new Random();
        for (Node move : node.getMoves()) {
            if (move!=null && (!move.visited || (move.visited && move.getMoveNumber()==1 && node.getMoveNumber()==finalMove))) {
               int availableMoves=move.getAvailableMoves();
               int moveOutside = Math.min(Math.abs(1-move.x) + Math.abs(1-move.y),Math.abs(xBound-move.x) + Math.abs(yBound-move.y))/2 ;
                
                if ( availableMoves<bestMove && availableMoves>0) {
                    bestMove=availableMoves;
                    nextMove=move; 
                } 
                if (availableMoves==bestMove && (rnd.nextInt(3)==1) || moveOutside<outside) {
                    outside=moveOutside;
                    nextMove=move;
                }
                
            }
        }
        if (nextMove!=null) {
            int currentMove=node.getMoveNumber();
            if (currentMove<finalMove) {
                // nextMove.setMoveNumber(0);
                nextMove.setMoveNumber(currentMove+1);
            } else {
                nextMove.setFinalMove(currentMove+1);
            }
            //System.out.println("Moving from " + node.x + "," + node.y + " to " + nextMove.x + "," + nextMove.y);
            nextMove.visited=true;
        } else {
            //sSystem.out.println("Couldn't find any more moves from " + node.x + "," + node.y);
        }
        return nextMove;
    }
    /**
     * @param Node head (front of the linked list so we can iterate through).
     * @return String of a grid printout of the board (tab and newline)
     */
    public static String PrintBoard(Node head) {
        String board="";
        int y=1;
        while (head!=null) {
            if (head.y>y) {
                y=head.y;
                board+="\n";
            }
           
            String marker="";
            if (!requiredClosedLoop) {
                if (head.getMoveNumber()==1) {
                    marker="*";
                } else if (head.getMoveNumber()==xBound*yBound) {
                    marker="#";
                }
            }
            String tile = "[" + marker + head.getMoveNumber() + (head.getFinalMove()>0?"&" + head.getFinalMove():"") + "]";
            if (tile.length()<8) {
                tile +="\t";
            }
            board += tile;
            
            //board += "[" + head.x + "," + head.y + "]" + head.getMoveNumber() + " " + (head.visited?"t":"f") + "\t";
            head=head.next;
        }
        return board + "\n\n";
    }
}
