package antgame;

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
                    char[] cArray = new char[1];
                    cArray[0] = c;
                    Integer i = new Integer(0);
                    i = Integer.decode(new String(cArray));
                    parsedWorld[cX][y].setFood(i);
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
    
    public boolean isWellFormed()
    {        
        if (sizeX != 150 || sizeY != 150) return false;
        
        for (int x = 0; x < sizeX; x++)
        {
            if (!parsedWorld[x][0].isRocky() || !parsedWorld[x][149].isRocky()) 
                return false;
        }
        for (int y = 0; y < sizeY; y++)
        {
            if (!parsedWorld[0][y].isRocky() || !parsedWorld[149][y].isRocky()) 
                return false;
        }
        
        int rocks = 0;
        int food = 0;
        int redAnthill = 0;
        int blackAnthill = 0;
        
        for (int x = 1; x < sizeX-1; x++)
        {
            for (int y = 1; y < sizeY-1; y++)
            {
                if (parsedWorld[x][y].hasAnthill(true))
                {
                    redAnthill++;
                    if (!checkAntHill(x,y,true)) 
                        return false;
                } else if (parsedWorld[x][y].hasAnthill(false)) {
                    blackAnthill++;
                    if (!checkAntHill(x,y,false)) 
                        return false;
                } else if (parsedWorld[x][y].isRocky()) {
                    rocks++;
                    if (!checkRock(x,y)) 
                        return false;
                } else if (parsedWorld[x][y].foodRemaining()>0) {
                    food++;
                    if (!checkFood(x,y)) 
                        return false;
                }
            }
        }
        
        return (rocks==14&&food==11&&redAnthill==1&&blackAnthill==1);
    }
    
    boolean checkAntHill(int x, int y, boolean isRed)
    {
        int offset = 0;
        //Check the cells and nearby cells for if there are any rocks or other anthills
        for (int height = -1; height < 14; height++)
        {
            int maxWidth = height<7?7+height:19-height;
            for (int width = 0; width < maxWidth+2; width++)
            {
                if (y+height<0||y+height>sizeY-1||x+width+offset<0||x+width+offset>sizeX-1||parsedWorld[x+width+offset][y+height].isRocky()||parsedWorld[x+width+offset][y+height].hasAnthill(isRed)) //Need not check for anthills or food as they do not yet exist
                {
                    return false;
                }
            }
            if (height<6)
                offset = (height+y)%2==0?offset-1:offset;
            else
                offset = (height+y)%2!=0?offset+1:offset;
        }
        
        for (int height = 0; height < 13; height++)
        {
            int maxWidth = height<7?7+height:19-height;
            for (int width = 0; width < maxWidth; width++)
            {
                if (!parsedWorld[x+width+offset][y+height].hasAnthill(isRed)) return false;
            }
            if (height<6)
                offset = (height+y)%2==0?offset-1:offset;
            else
                offset = (height+y)%2!=0?offset+1:offset;
        }
        return true;
    }
    
    boolean checkRock(int x, int y)
    {
        int offset = 0;
        for (int height = -1; height < 2; height++)
        {
            int maxWidth = height!=0?2:3;
            if (y%2!=0)
                for (int width = 0; width < maxWidth; width++)
                {
                    if (y+height<0||y+height>sizeY-1||x+width+offset<0||x+width+offset>sizeX-1||parsedWorld[x+width+offset][y+height].isRocky()||parsedWorld[x+width+offset][y+height].hasAnthill(true)||parsedWorld[x+width+offset][y+height].hasAnthill(false))
                    {
                        if (height!=0&&width+offset!=0) return false;
                    }

                }
            else
                for (int width = -1; width < maxWidth-1; width++)
                {
                    if (y+height<0||y+height>sizeY-1||x+width+offset<0||x+width+offset>sizeX-1||parsedWorld[x+width+offset][y+height].isRocky()||parsedWorld[x+width+offset][y+height].hasAnthill(true)||parsedWorld[x+width+offset][y+height].hasAnthill(false))
                    {
                        if (height!=0&&width+offset!=0) return false;
                    }
                }
            if (height ==-1) offset = (height+y)%2==0?offset-1:offset;
            else offset = (height+y)%2!=0?offset+1:offset;
        }
            
        //Places the rock
        if (!parsedWorld[x][y].isRocky())
        {
            return false;
        }
        return true;
    }
    
    boolean checkFood(int x, int y)
    {
        int offset = 0;
        //Check if all cells are free
        for (int height = -1; height < 6; height++)
        {
            if (y%2==0)
                for (int width = -1; width < 6; width++)
                {

                    if (y+height<0||y+height>sizeY-1||x+width+offset<0||x+width+offset>sizeX-1||parsedWorld[x+width+offset][y+height].isRocky()||parsedWorld[x+width+offset][y+height].hasAnthill(true)||parsedWorld[x+width+offset][y+height].hasAnthill(false))
                    {
                        return false;
                    }

                }
            else
                for (int width = 0; width < 7; width++)
                {

                    if (y+height<0||y+height>sizeY-1||x+width+offset<0||x+width+offset>sizeX-1||parsedWorld[x+width+offset][y+height].isRocky()||parsedWorld[x+width+offset][y+height].hasAnthill(true)||parsedWorld[x+width+offset][y+height].hasAnthill(false))
                    {
                        return false;
                    }

                }
            offset = (height+y)%2==0?offset-1:offset;
        }
        offset = 0;
        //Places the ant blob
        
        for (int height = 0; height < 5; height++)
        {

            for (int width = 0; width < 5; width++)
            {
                if (parsedWorld[x+width+offset][y+height].foodRemaining()!=5)
                {
                    return false;
                }

            }
            offset = (height+y)%2==0?offset-1:offset;
        }
        return true;
    }
}
