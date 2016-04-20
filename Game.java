/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antgame;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author oscar
 */
public class Game implements GameInterface{

    Ant[] ants;
    Visualiser gui;
    int currentRound;
    World world;
    int seed;
    AntBrain_State[] redBrain;
    AntBrain_State[] blackBrain;
    
    /**
     * Start a new game with default parameters for a contest world
     */
    public Game()
    {
        ants = new Ant[0];
        gui = new Visualiser(150, 150);
        seed = 42;
        world = new World(this);
        world.setDimensions(150, 150);
    }
    
    /**
     * Start a game with the given parameters, no longer a valid contest world unless x and y are both 150
     * @param x the number of elements wide of the simulation
     * @param y the number of elements high of the simulation
     */
    public Game(int x, int y)
    {
        ants = new Ant[0];
        gui = new Visualiser(x, y);
        seed = 42;
        world = new World(this);
        world.setDimensions(x, y);
    }
    
    @Override
    public void startGame() throws Exception
    {
        currentRound = 0;
        
        while (++currentRound!=300000) step(); //Step for each round
        JOptionPane.showMessageDialog(new JFrame(), "Results");
    }

    public void addAnt(Ant a)
    {
        Ant[] t = new Ant[ants.length+1];
        for (int i = 0; i < ants.length; i++) t[i] = ants[i];
        t[ants.length] = a;
        ants = t;
    }
    
    private void step() throws Exception
    {
        for (int i = 0; i < ants.length; i++)
        {
            if (ants[i].isAlive())
            {
                if (ants[i].isResting()) ants[i].rest();
                else if (ants[i].isRed) stepAnt(ants[i], redBrain);
                else stepAnt(ants[i], blackBrain);
            }
        }
    }
    
    private void stepAnt(Ant a, AntBrain_State[] brain) throws Exception
    {
        AntBrain_State instruction = brain[a.getState()];
        
        if (instruction.getClass()==State_Flip.class)
        {
            State_Flip flip = (State_Flip)instruction;
            a.setState(Math.random()*flip.get_P()==0?flip.get_St1():flip.get_St2());
        }
        else if (instruction.getClass()==State_Drop.class)
        {
            dropFood(a);
            a.setState(instruction.get_St1());
        }
        else if (instruction.getClass()==State_Mark.class)
        {
            State_Mark mark = (State_Mark)instruction;
            mark(a, mark.getMarkNum());
            a.setState(instruction.get_St1());
        }
        else if (instruction.getClass()==State_Move.class)
        {
            int[] coord = world.getFacedCell(a);
            int x = coord[0];
            int y = coord[1];
            
            if (!(world.getCell(x, y).isRocky()||world.getCell(x, y).getAnt()!=null))
            {
                world.getCell(a.getX(), a.getY()).setAnt(null);
                a.setX(x);
                a.setY(y);
                world.getCell(a.getX(), a.getY()).setAnt(a);
                a.setResting(14); //Makes the ant rest for 14 steps
            }
        }
        else if (instruction.getClass()==State_PickUp.class)
        {
            State_PickUp pickup = (State_PickUp)instruction;
            if (world.getCell(a.getX(), a.getY()).foodRemaining()>0&&a.hasFood) 
            {
                world.getCell(a.getX(), a.getY()).reduceFood();
                a.pickUpFood();
            }
            a.setState(instruction.get_St1());
        }
        else if (instruction.getClass()==State_Sense.class)
        {
            State_Sense sense = (State_Sense)instruction;
            //Sense Ahead 2 3 Foe
            Cell sensedCell = null;
            if (sense.senseDir.equals("Here")) sensedCell = (Cell)world.getCell(a.getX(), a.getY());
            else if (sense.senseDir.equals("Ahead")) 
            {
                int[] loc = world.getFacedCell(a);
                sensedCell = (Cell)world.getCell(loc[0], loc[1]);
            }
            else if (sense.senseDir.equals("LeftAhead"))
            {
                a.turnLeft(true);
                int[] loc = world.getFacedCell(a);
                sensedCell = (Cell)world.getCell(loc[0], loc[1]);
                a.turnLeft(false);
            }
            else
            {
                a.turnLeft(false);
                int[] loc = world.getFacedCell(a);
                sensedCell = (Cell)world.getCell(loc[0], loc[1]);
                a.turnLeft(true);
            }
            
            if (sense.cond.equals("Friend"))
            {
                if (sensedCell.getAnt().isRed()==a.isRed())a.setState(sense.get_St1());
                else a.setState(sense.get_St2());
            }
            else if (sense.cond.equals("Foe"))
            {
                if (sensedCell.getAnt().isRed()!=a.isRed())a.setState(sense.get_St1());
                else a.setState(sense.get_St2());
            }
            else if (sense.cond.equals("FriendWithFood"))
            {
                if (sensedCell.getAnt().isRed()==a.isRed()&&sensedCell.getAnt().hasFood()) a.setState(sense.get_St1());
                else a.setState(sense.get_St2());
            }
            else if (sense.cond.equals("FoeWithFood"))
            {
                if (sensedCell.getAnt().isRed()!=a.isRed()&&sensedCell.getAnt().hasFood()) a.setState(sense.get_St1());
                else a.setState(sense.get_St2());
            }
            else if (sense.cond.equals("Food"))
            {
                if (sensedCell.foodRemaining()>0) a.setState(sense.get_St1());
                else a.setState(sense.get_St2());
            }
            else if (sense.cond.equals("Rock"))
            {
                if (sensedCell.isRocky()) a.setState(sense.get_St1());
                else a.setState(sense.get_St2());
            }
            else if (sense.cond.equals("FoeMarker"))
            {
                boolean marker = false;
                for (int i = 0; i < 6; i++) marker = marker||sensedCell.hasMark(a.isRed(), i);
                if (marker) a.setState(sense.get_St1());
                else a.setState(sense.get_St2());
            }
            else if (sense.cond.equals("Marker"))
            {
                if (sensedCell.hasMark(a.isRed(), sense.get_MarkNum())) a.setState(sense.get_St1());
                else a.setState(sense.get_St2());
            }
            else if (sense.cond.equals("Home"))
            {
                if (sensedCell.hasAnthill(a.isRed())) a.setState(sense.get_St1());
                else a.setState(sense.get_St2());
            }
            else
            {
                if (sensedCell.hasAnthill(!a.isRed())) a.setState(sense.get_St1());
                else a.setState(sense.get_St2());
            }
        }
        else if (instruction.getClass()==State_Turn.class)
        {
            State_Turn turn = (State_Turn)instruction;
            a.turnLeft(turn.get_TurnLeft());
            a.setState(turn.get_St1());
        }
        else
        {
            State_Unmark unmark = (State_Unmark)instruction;
            world.getCell(a.getX(), a.getY()).unmark(a.isRed(),unmark.get_MarkNum());
        }
    }
    
    private void mark(Ant a, int markNum)
    {
        world.getCell(a.getX(), a.getY()).markCell(markNum, a.isRed());
    }
    
    private void dropFood(Ant a)
    {
        a.dropFood();
        world.getCell(a.getX(), a.getY()).increaseFood();
    }
    
    @Override
    public void loadWorld() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void determineWinner() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public void setSeed(int seed_)
    {
        seed = seed_;
    }
    
}
