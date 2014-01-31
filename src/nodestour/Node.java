package nodestour;

/**
 * Node class - linked list node extended to be aware of the 8 surrounding nodes
 * which are in reach of it using a knights 2+1 move.
 * @author harrison hamill
 */
public class Node {
    public Node next=null;
    public int x=0;
    public int y=0;
    public boolean visited=false;
    private int moveNumber=0;
    private int finalMove=0;
    private Node nnw=null;
    private Node nne=null;
    private Node nee=null;
    private Node see=null;
    private Node sse=null;
    private Node ssw=null;
    private Node sww=null;
    private Node nww=null;
    /**
     * getMoves() returns an array of the 8 possible moves (links); shorthand so 
     * we can for loop through them rather than naming them each time.
     * @return Node[]
     */
    public Node[] getMoves() {
        return new Node[]{nnw, nne, nee, see, sse, ssw, sww, nww};
    }
    /*
    Get and set methods.
    */
    public int getMoveNumber() {
        return moveNumber;
    }
    public void setMoveNumber(int moveNumber) {
        this.moveNumber=moveNumber;
    }
    public int getFinalMove() {
        return finalMove;
    }
    public void setFinalMove(int finalMove) {
        this.finalMove=finalMove;
    }
    public Node getNnw() {
        return nnw;
    }
    public void setNnw(Node nnw) {
        this.nnw=nnw;
    }
    public Node getNne() {
        return nne;
    }
    public void setNne(Node nne) {
        this.nne=nne;
    }
    public Node getNee() {
        return  nee;
    }
    public void setNee(Node nee) {
        this.nee=nee;
    }
    public Node getSee() {
        return see;
    }
    public void setSee(Node see) {
        this.see=see;
    }
    public Node getSse() {
        return sse;
    }
    public void setSse(Node sse) {
        this.sse=sse;
    }
    public Node getSsw() {
        return ssw;
    }
    public void setSsw(Node ssw) {
        this.ssw=ssw;
    }
    public Node getSww() {
        return sww;
    }
    public void setSww(Node sww) {
        this.sww=sww;
    }
    public Node getNww() {
        return nww;
    }
    public void setNww(Node nww) {
        this.nww=nww;
    }
     /*
        end of get and set methods.
    */
    
   /**
    * This checks the active node to see how many of its linked moves are
    * not null and haven't been visited. Used to determine which move to take
    * next while running.
    * @return int count of available moves.
    */
    public int getAvailableMoves() {
        int i=0;
        for (Node node : getMoves()) {
            if (node!=null && (!node.visited || node.getMoveNumber()==1)) {
                i++;
            }
        }
        return i;
    }
    /**
     * Constructor for the Node class. Only used by board initialization.
     * @param x The X coordinate of the new node.
     * @param y The y coordinate of the new nod.
     * @param next the Next node in the list if known, (or null).
     */
    public Node(int x, int y, Node next) {
        this.x=x; 
        this.y=y; 
        this.next=next;
    }
}
