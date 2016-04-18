package antgame;

/**
 *
 * @author Owner
 */
public interface Cell_Interface {

    public int foodRemaining();
    public void markCell(); //Should have int value of marker being marked
    public void increaseFood();
    public void getType(); //Return what?
    public void createCell(); //Constructs cell with parameters, called from constructor
}
