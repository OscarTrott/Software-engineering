/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antgame;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author oscar
 */
public class World implements WorldInterface{

    Cell[][] layout;
    AntBrain_State[] redBrain;
    AntBrain_State blackBrain;
    int sizeX;
    int sizeY;
    Game controller;
    
    public World(Game g)
    {
        controller = g;
    }
    
    @Override
    public void createWorld() {
        layout = new Cell[sizeX][];
        for (int i = 0; i < sizeX; i++)
        {
            layout[i] = new Cell[sizeY];
        }
    }

    public void randomWorld()
    {
        
    }
    
    @Override
    public void setDimensions(int x, int y) {
        sizeX = x;
        sizeY = y;
    }

    @Override
    public void placeAnts() 
    {
        int id = 0;
        for (int y = 0; y < layout.length; y++)
        {
            for (int x = 0; x < layout[0].length; x++)
            {
                if (layout[x][y].hasAnthill(true))
                {
                    Ant a = new Ant(id++, x, y, true);
                    try {
                        layout[x][y].setAnt(a);
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                    controller.addAnt(a);
                }
                else if (layout[x][y].hasAnthill(false))
                {
                    Ant a = new Ant(id++, x, y, false);
                    try {
                        layout[x][y].setAnt(a);
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                    controller.addAnt(a);
                }
            }
        }
    }

    public int[] getFacedCell(Ant a)
    {
        int direction = a.getDirection();
        int x = a.getX();
        int y = a.getY();
        int nextX = x;
        int nextY = y;
        
        switch (direction)
        {
            case 0:
                nextX++;
            case 1:
                if (x%2!=0)
                {
                    nextY++;
                }
                else
                {
                    nextY++;
                    nextX++;
                }
            case 2:
                if (x%2!=0)
                {
                    nextY++;
                    nextX--;
                }
                else
                {
                    nextY++;
                }
            case 3:
                nextX--;
            case 4:
                if (x%2!=0)
                {
                    nextX--;
                    nextY--;
                }
                else
                {
                    nextY--;
                }
            case 5:
                if (x%2!=0)
                {
                    nextY--;
                }
                else
                {
                    nextY--;
                    nextX++;
                }
        }
        
        int[] result = new int[2];
        result[0] = nextX;
        result[1] = nextY;
        return result;
        
    }
    
    @Override
    public Cell_Interface getCell(int x, int y) {
        return layout[x][y];
    }
    
}
