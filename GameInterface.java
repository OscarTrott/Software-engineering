package antgame;


/**
 *
 * @author Owner
 */
public interface GameInterface {
  public void startGame();
  public void loadWorld(); //What does this do?
  public void determineWinner();
  public void setSeed(int seed);
    
}
