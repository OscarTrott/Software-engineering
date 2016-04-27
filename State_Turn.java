/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

/**
 *
 * @author Owner
 */
public class State_Turn extends State_Super{
    boolean turnLeft;
    public State_Turn(String instruction_, int st1_, boolean turnLeft_) {
        super(instruction_, st1_);
        turnLeft = turnLeft_;
    }
    
    public boolean get_TurnLeft()
    {
        return turnLeft;
    }
}
