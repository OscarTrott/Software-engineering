/**
 * WARNING:: THIS IS OBSOLETE; YOU MAY TAKE INSPIRATIION FROM HERE IF YOU WISH, BUT I WON'T BE USING THIS FOR THE PROJECT!
 */
package Neurology;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author ar421
 */
class Brain {
    //current state
    private int state;
    //list of instuctions 
    private Instruction[] instructions;
    /**
     * This constructor method creates a new ant brain for a new ant with the help of a global counter, then stores it in a static HashMap.
     * @param inst 
     */
    public Brain(Instruction[] inst) {
        instructions = new Instruction[10000];
        int counter = 0;
        try {
            for (int i = 0; i < inst.length; i++) {
                instructions[i] = inst[i];
                BrainMap.brains.put(BrainMap.id, this);
                BrainMap.id++;
                counter++;
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("Warning: Too many instructions; only the first 10000 will be input into the ant brain!");
            BrainMap.id = BrainMap.id - counter;
            for (int i = 0; i < 10000; i++) {
                instructions[i] = inst[i];
                BrainMap.brains.put(BrainMap.id, this);
                BrainMap.id++;
            }
        } catch (Exception c) {
            System.out.println("Error: can't run the game!");
        }
    }
    
    public void step() {
        if(state < 10000) {
        
            Instruction i = instructions[state];
        
            if(i instanceof Sense) {
                System.out.println("sense");
            } else if (i instanceof Mark) {
                System.out.println("mark");
            } else if (i instanceof Unmark) {
                System.out.println("unmark");
            } else if (i instanceof PickUp) {
                System.out.println("pickup");
            } else if (i instanceof Drop) {
                System.out.println("drop");
            } else if (i instanceof Turn) {
                System.out.println("turn");
            } else if (i instanceof Move) {
                System.out.println("move");
            } else if (i instanceof Flip) {
                System.out.println("flip");
            } else {
                System.out.println("unknown");
            }
        
            state++;
        } else {
            kill();
        }
    }

    private void kill() {
        throw new UnsupportedOperationException("Not supported yet."); //kills ant
    }
}

interface Instruction {
}
class Sense implements Instruction {}
class Mark implements Instruction {}
class Unmark implements Instruction {}
class PickUp implements Instruction {}
class Drop implements Instruction {}
class Turn implements Instruction {}
class Move implements Instruction {}
class Flip implements Instruction {}


public class BrainMap {
    public static void newBrain(Instruction[] inst) {
        new Brain(inst);
    }
    public static Integer id = 0;
    public final static Map<Integer, Brain> brains = new HashMap<>();
}
