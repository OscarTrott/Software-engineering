/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antgame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author oscar
 */
public class Game implements GameInterface{

    Ant[] ants;
    VisualisationInterface gui;
    int currentRound;
    World world;
    int s;
    State_Super[] redBrain;
    State_Super[] blackBrain;
    ArrayList<Player> players;
    int numOfPlayers;
    Stats stats;
    
    /**
     * Start a new game with default parameters for a contest world
     */
    public Game()
    {
        ants = new Ant[0];
        s = 12345;
        world = new World(this, "World1");
        world.setDimensions(150, 150);
        for (int i = 0; i < 4; i++) genS();
        stats = new Stats();
    }
    
    /**
     * Start a game with the given parameters, no longer a valid contest world unless x and y are both 150
     * @param x the number of elements wide of the simulation
     * @param y the number of elements high of the simulation
     */
    public Game(int x, int y)
    {
        ants = new Ant[0];
        world = new World(this, "World1");
        world.setDimensions(x, y);
        
        //Initial seed
        s = 12345;
        for (int i = 0; i < 4; i++) genS();
        stats = new Stats();
    }
    
    public void addPlayer(String name, File f)
    {
        AntBrain_Parser p = new AntBrain_Parser(f);
        players.add(new Player(name, p.getBrain1().toArray(new State_Super[p.getBrain1().size()])));
    }
    
    public void setPlayerNum(int num)
    {
        numOfPlayers = num;
    }
    
    public int getPlayerNum()
    {
        return numOfPlayers;
    }
    
    /**
     * Starts the game which is currently set up
     * @param p1 player one being used in this game
     * @param p2 player two being user in this game
     * @throws Exception Throws an exception when an illegal operation occurs, an ant moves into a cell occupied by a rock
     */
    @Override
    public void startGame(Player p1, Player p2) throws Exception
    {
        redBrain = p1.getBrain();
        blackBrain = p2.getBrain();
        currentRound = 0;
        while (++currentRound!=300000) step(); //Step for each round
        if (stats.getRedFood()==stats.getBlackFood()) 
        {
            p1.draw();
            p2.draw();
        }
        else if (stats.getRedFood()>stats.getBlackFood())
        {
            p1.win();
        }
        else
        {
            p2.win();
        }
    }

    /**
     * Add an ant to the world, used in initial world setup
     * @param a the ant to be added
     */
    public void addAnt(Ant a)
    {
        Ant[] t = new Ant[ants.length+1];
        for (int i = 0; i < ants.length; i++) t[i] = ants[i];
        t[ants.length] = a;
        ants = t;
    }
    
    /**
     * Steps through a single execution cycle for all ants
     * @throws Exception throws an exception when an illegal operation occurs
     */
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
    
    /**
     * Makes the ant a run a single execution cycle
     * @param a the ant to be stepped
     * @param brain the brain of that ants colour
     * @throws Exception throws an exception when an illegal operation occurs
     */
    private void stepAnt(Ant a, State_Super[] brain) throws Exception
    {
        int killCount = 0;
        for (int i = 0; i < 6; i++)
        {
            int[] coord = world.getFacedCell(a);
            if (world.getCell(coord[0],coord[1]).isRocky()||(world.getCell(coord[0],coord[1]).getAnt()!=null&&world.getCell(coord[0],coord[1]).getAnt().isRed!=a.isRed())) killCount++;
            a.turnLeft(true);
        }
        if (killCount>4) 
        {
            if (a.isRed()) stats.redKill();
            else stats.blackKill();
            a.kill();
            for (int i = 0; i < 3; i++) world.getCell(a.getX(), a.getY()).increaseFood();
            return;
        }
        
        State_Super instruction = brain[a.getState()];
        
        //Find which instruction is being executed and then run the apprpriate code
        if (instruction.getClass()==State_Flip.class)
        {
            //Got to a state depending on the probability
            State_Flip flip = (State_Flip)instruction;
            a.setState(pseudoRandom(flip.p)==0?flip.get_St1():flip.get_St2());
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
            
            //If the cell in front of the ant can be moved into then move the ant nito it
            if (!(world.getCell(x, y).isRocky()||world.getCell(x, y).getAnt()!=null))
            {
                world.getCell(a.getX(), a.getY()).setAnt(null);
                a.setX(x);
                a.setY(y);
                world.getCell(a.getX(), a.getY()).setAnt(a);
                a.setResting(14); //Makes the ant rest for 14 steps
            }
            a.setState(instruction.get_St1());
        }
        else if (instruction.getClass()==State_PickUp.class)
        {
            State_PickUp pickup = (State_PickUp)instruction;
            if (world.getCell(a.getX(), a.getY()).foodRemaining()>0&&a.hasFood) 
            {
                world.getCell(a.getX(), a.getY()).reduceFood();
                a.pickUpFood();
            }
            a.setState(pickup.get_St1());
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
    
    /**
     * Force an ant to drop its food onto the cell it is currently on
     * @param a the ant whose food should be dropped
     */
    private void dropFood(Ant a)
    {
        if (a.isRed()&&world.getCell(a.getX(), a.getY()).hasAnthill(a.isRed)) stats.redFood();
        else if (!a.isRed()&&world.getCell(a.getX(), a.getY()).hasAnthill(a.isRed)) stats.blackFood();
        a.dropFood();
        world.getCell(a.getX(), a.getY()).increaseFood();
    }
    
    @Override
    public void loadWorld(File w) {
        WorldParser wp = new WorldParser();
        try {
            wp.loadWorld(w);
        } catch (Exception ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        world.setWorld(wp.getWorld());
        world.setName(w.getName());
    }

    @Override
    public Player determineWinner() {
        Player winner = players.get(0);
        for (Player p : players)
        {
            if (p.currentScore()>winner.currentScore()) winner = p;
        }
        return winner;
    }
    
    @Override
    public void setSeed(int seed_)
    {
        s = seed_;
    }
    
    public void genS()
    {
        s = s*22695477+1;
    }
    
    public int pseudoRandom(int n)
    {
        return ((s/65536)%16384)%n;
    }
    
    public void randomiseWorld()
    {
        world.randomWorld();
    }
    
    public void writePlayerMatchup()
    {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter("PlayerMatchups.txt"));
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        for (int i = 0; i < players.size(); i++)
        {
            for (int j = 0; j < players.size(); j++)
            {
                if (i!=j) try {
                    bw.write(players.get(i).getName()+ " vs "+players.get(j).getName() + " on world " + world.getName());
                    bw.newLine();
                } catch (IOException ex) {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        try {
            bw.flush();
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void startTournament()
    {
        for (int p1 = 0; p1 < players.size(); p1++)
        {
            for (int p2 = 0; p2 < players.size(); p2++)
            {
                if (p1 != p2)
                {
                    try {
                        startGame(players.get(p1),players.get(p2));
                    } catch (Exception ex) {
                        Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    gui.updateWorld(world.layout);
                }
            }
        }
    }
    
    public Stats getStats()
    {
        return stats;
    }
    
    public ArrayList<Player> getPlayers()
    {
        return players;
    }
}
