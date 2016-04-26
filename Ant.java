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
public class Ant implements AntInterface {

    int currentState;
    final int id;
    int resting;
    int direction;
    boolean hasFood;
    int locationX;
    int locationY;
    final boolean isRed;
    boolean alive;
    World world;
    static int nextId = 0;

    public Ant(int id_, int x, int y, boolean isRed_, World world_) {
        alive = true;
        isRed = isRed_;
        locationX = x;
        locationY = y;
        id = id_;
        currentState = 0;
        direction = 0;
        world = world_;
    }

    public boolean isResting()
    {
        return resting > 0;
    }
    public void rest()
    {
        resting--;
    }
    public boolean isRed() {
        return isRed;
    }

    public boolean isAlive() {
        return alive;
    }
    
    public boolean hasFood()
    {
        return hasFood;
    }

    public int getX() {
        return locationX;
    }

    public int getY() {
        return locationY;
    }

    public void kill()
    {
        alive = false;
    }
    
    @Override
    public void move() {
        
    }

    @Override
    public void turnLeft(boolean left) {
        direction = left?(direction+5)%6:(direction+1)%6;
    }

    @Override
    public void mark(int markNum) {
        
    }

    @Override
    public void unMark() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void pickUpFood() {
        hasFood = true;
    }

    public int getDirection() {
        return direction;
    }

    public void setX(int x)
    {
        locationX = x;
    }
    
    public void setY(int y)
    {
        locationY = y;
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
    public int getState() {
        return currentState;
    }

}
