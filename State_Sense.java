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
public class State_Sense extends AntBrain_State{
    String senseDir;
    int st2;
    String cond;
    int markNum;
    
    public State_Sense(String instruction_, int st1_, String senseDir_, String cond_, int st2_, int markNum_) {
        super(instruction_, st1_);
        markNum = markNum_;
        st2 = st2_;
        cond = cond_;
        senseDir = senseDir_;
    }
    
    public int get_MarkNum()
    {
        return markNum;
    }
    
    public String get_SenseDir()
    {
        return senseDir;
    }
    public int get_St2()
    {
        return st2;
    }
    public String get_Cond()
    {
        return cond;
    }
    
}
