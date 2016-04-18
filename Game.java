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

    Visualiser gui;
    int currentRound;
    World world;
    int seed;
    
    public static void main(String[] args)
    {
        if (args.length == 0) new Game();
        else new Game(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
    }
    
    public Game()
    {
        gui = new Visualiser(150, 150);
        seed = 42;
        world = new World();
        world.setDimensions(150, 150);
    }
    
    public Game(int x, int y)
    {
        gui = new Visualiser(x, y);
        seed = 42;
        world = new World();
        world.setDimensions(x, y);
    }
    
    @Override
    public void startGame() {
        currentRound = 0;
        while (++currentRound!=300000)
        {
            step();
        }
        JOptionPane.showMessageDialog(new JFrame(), "Results");
    }

    private void step()
    {
        
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
