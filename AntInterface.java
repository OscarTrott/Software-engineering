/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antgame;

/**
 *
 * @author Owner
 */
public interface AntInterface {
  public void move(); //Return boolean? for if the ant cannot move forward, i.e there is a rock or ant in front
  public void turnLeft(boolean left); //Should take in an int to determine how far the ant should rotate and in which direction OR what angle the ant should face after the turn
  public void mark(int markNum);
  public void unMark(); //Should have an int value passed which tells the ant which mark number should be used
  public void pickUpFood();
  public void dropFood();
  public void flip();
  public void setState(int state);
  public void setResting(int period);
  public int getState();
  
}
