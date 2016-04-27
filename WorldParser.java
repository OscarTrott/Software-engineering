/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Owner
 */
public class WorldParser {
    File worldFile;
    Cell[][] parsedWorld;
    int sizeX;
    int sizeY;
    
    public void loadWorld(File f) throws FileNotFoundException, IOException, Exception
    {
        worldFile = f;
        BufferedReader br = new BufferedReader(new FileReader(f));
        int x = Integer.parseInt(br.readLine());
        int y = Integer.parseInt(br.readLine());
        sizeX = x;
        sizeY = y;
        parsedWorld = new Cell[x][];
        for (int i = 0; i < x; i++)
        {
            parsedWorld[i] = new Cell[y];
        }
        String line = null;
        int cY = 0;
        while ((line=br.readLine())!=null)
        {
            parseLine(line, cY++);
        }
    }
    
    private void parseLine(String s, int y) throws Exception
    {
        
        char[] splitLine = s.toCharArray();
        int cX = 0;
        for (char c : splitLine)
        {
            if (c!=' ')
            {
                if (Character.isDigit(c)) 
                {
                    parsedWorld[cX][y] = new Cell(false, AntHill.NO_ANTHILL);
                    parsedWorld[cX][y].setFood(Character.digit(c,0));
                }
                else if (c=='+')
                    parsedWorld[cX][y] = new Cell(false, AntHill.RED_ANTHILL);
                else if (c=='-')
                    parsedWorld[cX][y] = new Cell(false, AntHill.BLACK_ANTHILL);
                else if (c=='.')
                    parsedWorld[cX][y] = new Cell(false, AntHill.NO_ANTHILL);
                else if (c=='#')
                    parsedWorld[cX][y] = new Cell(true, AntHill.NO_ANTHILL);
                else
                    throw new Exception("Unexpected input at x: "+cX+" , y:" + y);
                cX++;
            }
        }
    }
    
    public Cell[][] getWorld()
    {
        return parsedWorld;
    }
    
    public int getX()
    {
        return sizeX;
    }
    public int getY()
    {
        return sizeY;
    }
}
