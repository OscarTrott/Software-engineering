/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

/**
 *
 * @author Owner
 */
public class Player {
    private final String name;
    private int score;
    private final State_Super[] brain;
    
    public Player(String name_, State_Super[] brain_)
    {
        brain = brain_;
        name = name_;
    }
    
    public State_Super[] getBrain()
    {
        return brain;
    }
    
    public String getName()
    {
        return name;
    }
    
    public int currentScore()
    {
        return score;
    }
    
    public void win()
    {
        score+=2;
    }
    
    public void draw()
    {
        score++;
    }
}
