package antgame;

import java.io.File;


/**
 *
 * @author Owner
 */
public interface GameInterface {
  public void startGame(Player p1, Player p2) throws Exception;
  public void loadWorld(File w); 
  public Player determineWinner();
  public void setSeed(int seed);
  
}
