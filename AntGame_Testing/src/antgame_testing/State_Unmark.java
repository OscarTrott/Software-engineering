/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antgame_testing;

/**
 *
 * @author Owner
 */
public class State_Unmark extends AntBrain_State{
    int markNum;
    public State_Unmark(String instruction_, int st1_, int markNum_) {
        super(instruction_, st1_);
        markNum = markNum_;
    }
    
    public int get_MarkNum()
    {
        return markNum;
    }
}
