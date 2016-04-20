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
public class Stats {

    int redFood;
    int blackFood;
    int blackKills;
    int redKills;
    int foodRemaining;
    int remainingAnts;
    
    public Stats()
    {
        blackKills = 0;
        redKills = 0;
        foodRemaining = 0;
        remainingAnts = 254;
        redFood = 0;
        blackFood = 0;
    }
    public void redFood()
    {
        redFood++;
    }
    public void blackFood()
    {
        blackFood++;
    }
    public int getBlackFood()
    {
        return blackFood;
    }
    public int getRedFood()
    {
        return redFood;
    }
    public int getAnts()
    {
        return remainingAnts;
    }
    public int getRedKills()
    {
        return redKills;
    }
    public int getBlackKills()
    {
        return blackKills;
    }
    public void blackKill()
    {
        remainingAnts--;
        blackKills++;
    }
    public void redKill()
    {
        remainingAnts--;
        redKills++;
    }
    
}
