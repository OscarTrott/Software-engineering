package antgame;

/**
 *
 * @author Owner
 */
public interface WorldInterface {
  public void createWorld();
  public void setDimensions(int x, int y); //Should take in two int parameters defining x and y, for a tournament world this should be 150, 150
  public void placeAnts();
  public Cell_Interface getCell(int x, int y); //Should also take in two parameters x and y defining the cell to be returned
}
