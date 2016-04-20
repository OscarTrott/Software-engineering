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
    State_Super[] redBrain;
    State_Super[] blackBrain;
    int sizeX;
    int sizeY;
    Game controller;
    String worldName;
    static int worldNum;
    
    public World(Game g, String worldName_)
    {
        worldName = worldName_;
        controller = g;
    }
    
    public void setName(String worldName_)
    {
        worldName = worldName_;
    }
    
    @Override
    public void createWorld() {
        //Sets up the layout array
        layout = new Cell[sizeX][];
        for (int i = 0; i < sizeX; i++)
        {
            layout[i] = new Cell[sizeY];
        }
    }

    /**
     * Fills this world with a contest worlds specification randomly
     */
    public void randomWorld()
    {
        worldName = "world"+worldNum++;
        //Place a ring of rocks around the world
        for (int x = 0; x < sizeX; x++)
        {
            addRock(x, 0, false);
            addRock(x, sizeY-1, false);
        }
        for (int y = 1; y < sizeY-2; y++)
        {
            addRock(0, y, false);
            addRock(sizeX-1, y, false);
        }
        //Add the anthills
        addRandomRedAnthill();
        addRandomBlackAnthill();
        for (int foodCount = 0; foodCount < 11; foodCount++)
        {
            addRandomFood();
        }
        for (int rockCount = 0; rockCount < 14; rockCount++)
        {
            addRandomRock();
        }
    }
    
    /**
     * Adds an anthill randomly to this world object
     */
    public void addRandomRedAnthill()
    {
        boolean placed = false;
        while (!placed)
        {
            double temp = Math.random()*(sizeX-7);
            int x = Math.round(Math.round(temp));
            temp = Math.random()*sizeY;
            int y = Math.round(Math.round(temp));
            placed = addRedAnthill(x,y);
        }
    }
    
    /**
     * Attempts to add a red anthill at the coordinates specified
     * @param x position on x axis of the top left point of the anthill
     * @param y position on y axis of the top left point of the anthill
     * @return true if the anthill is able to be placed, false otherwise
     */
    public boolean addRedAnthill(int x, int y)
    {
        boolean placed = true;
            
        int offset = 0;

        for (int height = -1; height < 14; height++)
        {
            int maxWidth = height<8?7+height:20-height;
            for (int width = -1; width < maxWidth+1; width++)
            {
                if (getCell(x+width+offset,y+height).isRocky()) //Need not check for anthills or food as they do not yet exist
                {
                    placed = false;
                }
            }
            if (height<8)
                offset = (height+y)%2!=0?offset-1:offset;
            else
                offset = (height+y)%2!=0?offset+1:offset;
        }
        if (placed)
            for (int height = 0; height < 13; height++)
            {
                int maxWidth = height<8?7+height:20-height;
                for (int width = 0; width < maxWidth; width++)
                {
                    layout[x+width+offset][y+height] = new Cell(false, AntHill.RED_ANTHILL);
                }
                if (height<8)
                    offset = (height+y)%2!=0?offset-1:offset;
                else
                    offset = (height+y)%2!=0?offset+1:offset;
            }
        return placed;
    }
    
    /**
     * Add a random black anthill to the world
     */
    public void addRandomBlackAnthill()
    {
        boolean placed = false;
        while (!placed)
        {
            placed = true;
            double temp = Math.random()*(sizeX-7);
            int x = Math.round(Math.round(temp));
            temp = Math.random()*sizeY;
            int y = Math.round(Math.round(temp));
            placed = addBlackAnthill(x,y);
        }
    }
    
    /**
     * Attempts to add a black anthill at the coordinates specified
     * @param x position on x axis of the top left point of the anthill
     * @param y position on y axis of the top left point of the anthill
     * @return true if the anthill is able to be placed, false otherwise
     */
    public boolean addBlackAnthill(int x, int y)
    {
        int offset = 0;
        boolean placed = true;
        for (int height = -1; height < 14; height++)
        {
            int maxWidth = height<8?7+height:20-height;
            for (int width = -1; width < maxWidth+1; width++)
            {
                if (getCell(x+width+offset,y+height).isRocky()||getCell(x+width-height,y+height).hasAnthill(true))
                {
                    placed = false;
                }
            }
            if (height<8)
                offset = (height+y)%2!=0?offset-1:offset;
            else
                offset = (height+y)%2!=0?offset+1:offset;
        }
        if (placed)
            for (int height = 0; height < 13; height++)
            {
                int maxWidth = height<8?7+height:20-height;
                for (int width = 0; width < maxWidth; width++)
                {
                    layout[x+width+offset][y+height] = new Cell(false, AntHill.BLACK_ANTHILL);
                }
                if (height<8)
                    offset = (height+y)%2!=0?offset-1:offset;
                else
                    offset = (height+y)%2!=0?offset+1:offset;
            }
        return placed;
    }
    
    /**
     * Places a random food square in this world
     */
    public void addRandomFood()
    {
        boolean placed = false;
        while (!placed)
        {
            placed = true;
            double temp = Math.random()*(sizeX-7);
            int x = Math.round(Math.round(temp));
            temp = Math.random()*sizeY;
            int y = Math.round(Math.round(temp));
            placed = addFood(x,y);
        }
    }
    
    /**
     * Adds a 5x5 food square to the world
     * @param x location of the top left of the food
     * @param y location of the top left of the food
     * @return true if the food can be placed, false otherwise
     */
    public boolean addFood(int x, int y)
    {
        int offset = 0;
        boolean placed = true;
        //Check if all cells are free
        for (int height = -1; height < 6; height++)
        {
            for (int width = -1; width < 6; width++)
            {
                if (getCell(x+width+offset,y+height).isRocky()||getCell(x+width-offset,y+height).hasAnthill(true)||getCell(x+width-offset,y+height).hasAnthill(false))
                {
                    placed = false;
                }
                offset = (height+y)%2!=0?offset-1:offset;
            }
        }
        offset = 0;
        if (placed)
        {
            for (int height = 0; height < 5; height++)
            {

                for (int width = 0; width < 5; width++)
                {
                    if (getCell(x+width+offset,y+height).isRocky()||getCell(x+width-offset,y+height).hasAnthill(true)||getCell(x+width-offset,y+height).hasAnthill(false))
                    {
                        layout[x+width-height][y+height].setFood(5);
                    }
                    offset = (height+y)%2!=0?offset-1:offset;

                }
            }
        }
        return placed;
    }
    
    /**
     * Adds a random rock to this world
     */
    public void addRandomRock()
    {
        boolean placed = false;
        while (!placed)
        {
            double temp = Math.random()*(sizeX-7);
            int x = Math.round(Math.round(temp));
            temp = Math.random()*sizeY;
            int y = Math.round(Math.round(temp));
            addRock(x,y, true);
        }
    }
    
    /**
     * Attempts to place a rock at the specified location
     * @param x axis location of the rock
     * @param y axis location of the rock
     * @param surroundCheck true if the method should check the surrounding area for obstructions
     * @return true if the rock can be placed, false otherwise
     */
    public boolean addRock(int x, int y, boolean surroundCheck)
    {
        int offset = 0;
        boolean placed = true;
        if (surroundCheck)
            for (int height = -1; height < 2; height++)
            {
                int maxWidth = height!=0?2:3;
                for (int width = -1; width < maxWidth; width++)
                {
                    if (getCell(x+width+offset,y+height).isRocky()||getCell(x+width-offset,y+height).hasAnthill(true)||getCell(x+width-offset,y+height).hasAnthill(false))
                    {
                        placed = false;
                    }
                    offset = (height+y)%2!=0?offset-1:offset;
                }
            }
        if (!(getCell(x,y).isRocky()||getCell(x,y).hasAnthill(true)||getCell(x,y).hasAnthill(false)))
        {
            layout[x][y] = new Cell(true, AntHill.NO_ANTHILL);
            placed = true;
        }
        return placed;
    }
    
    /**
     * Sets the worlds dimensions to be x and y
     * @param x the width of the world
     * @param y the height of the world
     */
    @Override
    public void setDimensions(int x, int y) {
        sizeX = x;
        sizeY = y;
    }

    /**
     * Place an ant on every anthill cell with its colour being that of the anthill, increments the id with each ant placed
     */
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
                    Ant a = new Ant(id++, x, y, true, this);
                    try {
                        layout[x][y].setAnt(a);
                    } catch (Exception ex) {
                        System.err.println(ex.getMessage());
                    }
                    controller.addAnt(a);
                }
                else if (layout[x][y].hasAnthill(false))
                {
                    Ant a = new Ant(id++, x, y, false, this);
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

    /**
     * Returns the x and y coordinates of the cell which the ant a faces, should never be out of bounds as the world is encircled by rocky terrain
     * @param a the ant whose coordinates and direction are being used
     * @return the x and y coordinates of the faced cell
     */
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
    
    /**
     * Returns the cell at the specified coordinates
     * @param x the x value
     * @param y the y value
     * @return the cell at x, y
     */
    @Override
    public Cell_Interface getCell(int x, int y) {
        return layout[x][y];
    }
    
    public void setWorld(Cell[][] world)
    {
        layout = world;
    }
    
    public String getName()
    {
        return worldName;
    }
}
