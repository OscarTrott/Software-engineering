 

/**
 *
 * @author Owner
 */
public class State_Unmark extends State_Super{
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