import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abdullah Rowaished
 */
public class AntAbdul implements AntInterface
{
  private AntState STATE = AntState.INITIAL; //all ants start at an INITIAL state
  private String[] commands;
  
  public AntAbdul(File brain) {
      String content = null;
      //BELOW: the input File is converted into one String, then is parsed into the field 'commands'
      try {
          content = new Scanner(brain).useDelimiter("//Z").next();
      } catch (FileNotFoundException ex) {
          Logger.getLogger(AntAbdul.class.getName()).log(Level.SEVERE, null, ex);
      }
      commands = parseBrain(content);
  }
  /**
   * converts the input file of the brain into an array of commands.
   * @param brain file from constructor
   * @return array of commands
   */
  private String[] parseBrain(String brain) {
      String[] spliteratedBrain = brain.split(" ");
      return spliteratedBrain;
  }
  
  public void move() {
    //TODO
  }
  
  public void turn() {
    //TODO
  }
  
  public void mark(int markNum) {
    //TODO
  }
  
  public void unMark() {
    //TODO
  }
  
  public void pickUpFood() {
    //TODO
  }
  
  public void dropFood() {
    //TODO
  }
  
  public void flip() {
    //TODO
  }
  
  public void setState() {
    //TODO
  }
  
  public void setResting() {
    //TODO
  }
  
}
/**
 *
 * @author Owner
 */
interface AntInterface {
  public void move(); //Return boolean? for if the ant cannot move forward, i.e there is a rock or ant in front
  public void turn(); //Should take in an int to determine how far the ant should rotate and in which direction OR what angle the ant should face after the turn
  public void mark(int markNum);
  public void unMark(); //Should have an int value passed which tells the ant which mark number should be used
  public void pickUpFood();
  public void dropFood();
  public void flip();
  public void setState();
  public void setResting();
}

enum AntState {
  INITIAL, SEARCHING, RETURNING, HUNTING, DEAD
}
