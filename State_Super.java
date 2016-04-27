/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

/**
 *
 * @author Owner
 */
public abstract class State_Super {
    String instruction;
    int st1;
    
    public State_Super(String instruction_, int st1_)
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
