/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antgame;

/**
 *
 * @author oscar
 */
public class Ant implements AntInterface{

    int currentState;
    final int id;
    int resting;
    int direction;
    boolean hasFood;
    int locationX;
    int locationY;
    final boolean isRed;
    
    public Ant(int id_, int x, int y, boolean isRed_)
    {
        isRed = isRed_;
        locationX = x;
        locationY = y;
        id = id_;
        currentState = 0;
    }
    
    @Override
    public void move() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void turn() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void mark(int markNum) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void unMark() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pickUpFood() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dropFood() {
        hasFood = false;
    }

    @Override
    public void flip() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setState(int state) {
        currentState = state;
    }

    @Override
    public void setResting(int period) {
        resting = period;
    }
    
    @Override
    public int getState()
    {
        return currentState;
    }
    
}
