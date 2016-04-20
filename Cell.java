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
public class Cell implements Cell_Interface {

    private boolean[] redMarks;
    private boolean[] blackMarks;
    private boolean rocky;
    private int foodRemaining;
    private AntHill antHill;
    private Ant ant;
    
    public Cell(boolean rocky_, AntHill antHill_)
    {
        antHill = antHill_;
        rocky = rocky_;
        redMarks = new boolean[6];
        blackMarks = new boolean[6];
        ant = null;
    }
    
    public void setFood(int amount)
    {
        foodRemaining = amount;
    }
    @Override
    public void setAnt(Ant a) throws Exception
    {
        if (a!=null&&ant!=null) throw new Exception("Ant already exists on cell: "+ant.getX() + ", "+ant.getY());
        ant = a;
    }
    
    @Override
    public Ant getAnt()
    {
        return ant;
    }
    
    @Override
    public int foodRemaining() {
        return foodRemaining;
    }

    @Override
    public boolean isRocky()
    {
        return rocky;
    }
    
    @Override
    public void reduceFood()
    {
        foodRemaining--;
    }
    
    @Override
    public void markCell(int mark, boolean isRed) {
        if (isRed) redMarks[mark] = true;
        else blackMarks[mark] = true;
    }

    @Override
    public void increaseFood() {
        foodRemaining++;
    }

    @Override
    public void getType() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void createCell() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean hasMark(boolean isRed, int mark) {
        if (isRed) return redMarks[mark];
        else return blackMarks[mark];
    }
    
    @Override
    public boolean hasAnthill(boolean isRed)
    {
        if (isRed) return antHill==AntHill.RED_ANTHILL;
        else return antHill==AntHill.BLACK_ANTHILL;
    }
    
    @Override
    public void unmark(boolean isRed, int mark)
    {
        if (isRed) redMarks[mark] = false;
        else blackMarks[mark] = false;
    }
    
}
