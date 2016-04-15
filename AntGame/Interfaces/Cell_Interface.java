/**
 *
 * @author Owner
 */
public interface CellInterface {

    public int foodRemaining();
    public void markCell(); //Should have int value of marker being marked
    public void increaseFood();
    public void getType(); //Return what?
    public createCell(); //Constructs cell with parameters, called from constructor
}
