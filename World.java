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
public class World implements WorldInterface{

    Cell[][] layout;
    AntBrain_State[] redBrain;
    AntBrain_State blackBrain;
    int sizeX;
    int sizeY;
    
    @Override
    public void createWorld() {
        layout = new Cell[sizeX][];
        for (int i = 0; i < sizeX; i++)
        {
            layout[i] = new Cell[sizeY];
        }
    }

    @Override
    public void setDimensions(int x, int y) {
        sizeX = x;
        sizeY = y;
    }

    @Override
    public void placeAnts() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Cell_Interface getCell(int x, int y) {
        return layout[x][y];
    }
    
}
