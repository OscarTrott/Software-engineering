/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

/**
 *
 * @author Owner
 */
public class State_PickUp extends State_Super{
    int st2;
    public State_PickUp(String instruction_, int st1_, int st2_) {
        super(instruction_, st1_);
        st2 = st2_;
    }
    
    public int get_St2()
    {
        return st2;
    }
}
