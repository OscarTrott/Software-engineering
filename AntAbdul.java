import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abdullah Rowaished
 */
public class AntAbdul implements AntInterface {

    private AntState STATE = AntState.INITIAL; //all ants start at an INITIAL state
    private String[] commands; //list of commands as parsed from the text file.

    /**
     * constructor for the ant, takes a brain and converts it into an array of texts or commands
     * @param brain.ant in the directory
     */ 
    public AntAbdul(File brain) {
        String content = null;
        //BELOW: the input File is converted into one String, then is parsed into the field 'commands'
        try {
            content = new Scanner(brain).useDelimiter("//Z").next();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AntAbdul.class.getName()).log(Level.SEVERE, null, ex);
        }
        commands = lexBrain(content);
    }
    /**
     * checks if a whole command String is a number or not
     * @param command parsed from the brain.ant
     * @return is it a number or something else?
     */
    private boolean isNumber(String command) {
        char[] array = command.toCharArray();
        
        for(char element : array) {
            if(!Character.isDigit(element)) {
                return false;
            }
        }
        return true;
    }

    /**
     * converts the input file of the brain into an array of commands.
     *
     * @param brain file from constructor
     * @return array of String commands
     */
    private String[] lexBrain(String brain) {
        String[] spliteratedBrain = brain.split(" ");
        return spliteratedBrain;
    }

    /**
     * takes a command from the converted file then, with the current state of
     * the ant, produces the next state; i.e. next state logic (NSL)
     *
     * @param String command as an element from the String[] array commands
     * @return next AntState value (FSM property)
     */
    private AntState parseBrain(String command) {
        AntState NEXT_STATE = null;
        if (command.equals("Move")) {
            
        } else if (command.equals("Turn")) {

        } else if (command.equals("Mark")) {

        } else if (command.equals("Unmark")) {

        } else if (command.equals("Pickup")) {

        } else if (command.equals("Drop")) {

        } else if (command.equals("Flip")) {

        } else if (isNumber(command)) {

        }

        return NEXT_STATE;
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
 * @author Oscar Trott
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

/**
 * @author Abdullah Rowaished
 * WARNING: this could be revised; need to look at requirements document!
 */
enum AntState {
    INITIAL, SEARCHING, RETURNING/* RETURNING might go against the will of the user e.g. user might want the ant to collect as few particles of food as possibe; as part of a strategum, or vice-versa, this state could only fulfill one implementation */, HUNTING/* might be redundant since user cant explicitly command ants to hunt ants of the other team */, DEAD
}
