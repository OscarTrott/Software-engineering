 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 

import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author oscar
 */
public class Tester {
    public static void main(String[] args) throws Exception
    {
        new Tester();
    }
    Tester() throws Exception
    {
        Game g = new Game();
        g.setPlayerNum(2);
        g.addPlayer("1", new File("\\\\smbhome.uscs.susx.ac.uk\\ojt21\\Desktop\\ourAnt.ant"));
        g.addPlayer("2", new File("\\\\smbhome.uscs.susx.ac.uk\\ojt21\\Desktop\\ourAnt.ant"));
        System.out.println(g.loadWorld(new File("\\\\smbhome.uscs.susx.ac.uk\\ojt21\\Desktop\\world.world")));
        g.startTournament();
        System.out.println("Winner: "+g.determineWinner().getName());
        /*for (int i = 0; i < 100; i++)
        {
            System.out.println(g.pseudoRandom(16384));
        }*/
        
        
    }
}
