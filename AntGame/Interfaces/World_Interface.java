/**
 *
 * @author Owner
 */
public interface WorldInterface {
  public void createWorld();
  public void setDimensions(); //Should take in two int parameters defining x and y, for a tournament world this should be 150, 150
  public void placeAnts();
  getCell(); //Should also take in two parameters x and y defining the cell to be returned
}
