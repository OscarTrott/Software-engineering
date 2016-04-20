package antgame;

/**
 *
 * @author Owner
 */
public interface Cell_Interface {

    public int foodRemaining();
    public void markCell(int mark, boolean isRed); //Should have int value of marker being marked
    public void increaseFood();
    public void getType(); //Return what?
    public void createCell(); //Constructs cell with parameters, called from constructor
    public boolean isRocky();
    public Ant getAnt();
    public void setAnt(Ant a) throws Exception;
    public void reduceFood();
    public boolean hasMark(boolean isRed, int mark);
    public boolean hasAnthill(boolean isRed);
    public void unmark(boolean isRed, int mark);
}
