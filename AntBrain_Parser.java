/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antgame;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javafx.scene.AccessibleAction;
/**
 *
 * @author Owner
 */
public class AntBrain_Parser {
    javafx.scene.AccessibleAction a = null;
    File b1;
    File b2;
    int length;
    ArrayList<AntBrain_State> brain1;
    ArrayList<AntBrain_State> brain2;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AntBrain_Parser a = new AntBrain_Parser();
        System.out.println(a.getBrain1());
    }
    
    private File getBrain(String message) 
    {
        File f = null;
        JOptionPane.showMessageDialog(null, message);

        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("C:\\Users\\Owner\\Desktop"));
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            f = chooser.getSelectedFile();
        }

        return f;
    }
    
    public AntBrain_Parser()
    {
        b1 = getBrain("Please choose player 1's brain");
        b2 = getBrain("Please choose player 2's brain");
        
        brain1 = new ArrayList();
        brain2 = new ArrayList();
        
        BufferedReader r1;
        BufferedReader r2;
        try
        {
            FileReader f1 = new FileReader(b1);
            r1 = new BufferedReader(f1);
        }
        catch (FileNotFoundException e)
        {
            System.err.println("File not found");
            return;
        }
        try
        {
            FileReader f2 = new FileReader(b2);            
            r2 = new BufferedReader(f2);

        }
        catch (FileNotFoundException e)
        {
            System.err.println("File not found");
            return;
        }
        
        String inputLine;
        int count = 0;
        
        //Gets all of the states from file1 and adds them to player 1's brain
        try 
        {
            while (r1.ready() && count < 10001)
            {
                inputLine = r1.readLine();
                count++;
                if (!isWellFormed(inputLine, true))
                {
                    throw new IOException("Input is not well formed on line: " + count + "  Line content: " + inputLine);
                }
            }
            if (count == 10001)throw new IOException("Input file has too many lines, exiting");
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
            return;
        }
        
        count = 0;
        
        //Gets all states from input file2 and adds those states to player 2's brain
        try 
        {
            while (r2.ready() && count < 10001)
            {
                inputLine = r2.readLine();
                count++;
                if (!isWellFormed(inputLine,false))
                {
                    throw new IOException("Input is not well formed on line: " + count);
                
                }
            }
            if (count == 10001)throw new IOException("Input file has too many lines, exiting"); //End if line number is > 10000
        }
        catch (IOException e)
        {
            System.err.println(e.getMessage());
            return;
        }
        
        
    }
    
    private boolean isWellFormed(String line, boolean isBrain1)
    {
        boolean wellFormed = false;
        String[] splitLine = line.split("\\s");
        
        if (splitLine[0].equals("Sense") && splitLine.length < 7)
        {
            if (splitLine[1].equals("Here") || splitLine[1].equals("Ahead") || splitLine[1].equals("LeftAhead") || splitLine[1].equals("RightAhead"))
            {
                if (Integer.parseInt(splitLine[2]) >= 0 )
                {
                    if (Integer.parseInt(splitLine[3]) >= 0 )
                    {
                        if (splitLine[4].matches("Friend|Foe|FriendWithFood|FoeWithFood|Food|Rock|FoeMarker|Home|FoeHome") && splitLine.length == 5)
                        {
                            if (isBrain1) brain1.add(new State_Sense(splitLine[0], Integer.parseInt(splitLine[2]), splitLine[1], splitLine[4], Integer.parseInt(splitLine[3]),0));
                            else brain1.add(new State_Sense(splitLine[0], Integer.parseInt(splitLine[2]), splitLine[1], splitLine[4], Integer.parseInt(splitLine[3]),0));
                            wellFormed = true;
                        }
                        else if (splitLine[4].matches("Marker") && splitLine.length == 6 && Integer.parseInt(splitLine[5]) >= 0 && Integer.parseInt(splitLine[5]) <= 5)
                        {
                            if (isBrain1) brain1.add(new State_Sense(splitLine[0], Integer.parseInt(splitLine[2]), splitLine[1], splitLine[4], Integer.parseInt(splitLine[3]), Integer.parseInt(splitLine[5])));
                            else brain1.add(new State_Sense(splitLine[0], Integer.parseInt(splitLine[2]), splitLine[1], splitLine[4], Integer.parseInt(splitLine[3]), Integer.parseInt(splitLine[5])));
                            wellFormed = true;
                        }
                    }
                }
            }
        }
        else if (splitLine[0].equals("Drop") && splitLine.length == 2)
        {
            if (Integer.parseInt(splitLine[1]) >= 0)
            {
                if (isBrain1) brain1.add(new State_Drop(splitLine[0],Integer.parseInt(splitLine[1])));
                else brain2.add(new State_Drop(splitLine[0],Integer.parseInt(splitLine[1])));
                wellFormed = true;
            }
        }
        else if (splitLine[0].equals("Flip") && splitLine.length == 4)
        {
            if (Integer.parseInt(splitLine[1]) >= 0 && Integer.parseInt(splitLine[2]) >= 0 && Integer.parseInt(splitLine[3])>= 0)
            {
                if (isBrain1) brain1.add(new State_Flip(splitLine[0],Integer.parseInt(splitLine[3]),Integer.parseInt(splitLine[1]),Integer.parseInt(splitLine[2])));
                else brain2.add(new State_Flip(splitLine[0],Integer.parseInt(splitLine[3]),Integer.parseInt(splitLine[1]),Integer.parseInt(splitLine[2])));
                wellFormed = true;
            }
        }
        else if (splitLine[0].equals("Mark") && splitLine.length == 3)
        {
            if (Integer.parseInt(splitLine[1]) >= 0 && Integer.parseInt(splitLine[1]) <= 5 && Integer.parseInt(splitLine[2]) >= 0)
            {
                if (isBrain1) brain1.add(new State_Mark(splitLine[0],Integer.parseInt(splitLine[2]),Integer.parseInt(splitLine[1])));
                else brain2.add(new State_Mark(splitLine[0],Integer.parseInt(splitLine[2]),Integer.parseInt(splitLine[1])));
                wellFormed = true;
            }
        }
        else if (splitLine[0].equals("Unmark") && splitLine.length == 3)
        {
            if (Integer.parseInt(splitLine[1]) >= 0 && Integer.parseInt(splitLine[1]) <= 5 && Integer.parseInt(splitLine[2]) >= 0)
            {
                if (isBrain1) brain1.add(new State_Unmark(splitLine[0],Integer.parseInt(splitLine[2]),Integer.parseInt(splitLine[1])));
                else brain2.add(new State_Unmark(splitLine[0],Integer.parseInt(splitLine[2]),Integer.parseInt(splitLine[1])));
                wellFormed = true;
            }
        }
        else if (splitLine[0].equals("Move") || splitLine[0].equals("PickUp") && splitLine.length == 3)
        {
            if (Integer.parseInt(splitLine[1]) >= 0 && Integer.parseInt(splitLine[2]) >= 0)
            {
                if (isBrain1) brain1.add(new State_Move(splitLine[0],Integer.parseInt(splitLine[1]),Integer.parseInt(splitLine[2])));
                else brain2.add(new State_Move(splitLine[0],Integer.parseInt(splitLine[1]),Integer.parseInt(splitLine[2])));
                wellFormed = true;
            }
        }
        else if (splitLine[0].equals("Turn") && splitLine.length == 3)
        {
            if (splitLine[1].matches("Left|Right") && Integer.parseInt(splitLine[2]) >= 0) 
            {
                if (isBrain1) brain1.add(new State_Turn(splitLine[0],Integer.parseInt(splitLine[2]),splitLine[1].equals("Left")));
                
                wellFormed = true;
            }
        }
        return wellFormed;
    }
    
    public ArrayList<AntBrain_State> getBrain1()
    {
        return brain1;
    }
    public ArrayList<AntBrain_State> getBrain2()
    {
        return brain1;
    }
}
