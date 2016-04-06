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
public abstract class AntBrain_State {
    String instruction;
    int st1;
    
    public AntBrain_State(String instruction_, int st1_)
    {
        instruction = instruction_;
        st1 = st1_;
    }
    
    public String get_Instruction()
    {
        return instruction;
    }
    public int get_St1()
    {
        return st1;
    }
}
