/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antgame;

import java.io.File;

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
        //for (int i = 0; i < 10; i++)
          //  System.out.println(g.pseudoRandom(16384));
        g.setPlayerNum(2);
        g.addPlayer("1", new File("C:\\sample.ant"));
        g.addPlayer("2",new File("C:\\sample.ant"));
        g.randomiseWorld();
        g.startTournament();
        System.out.println("Winner: "+g.determineWinner().getName());
    }
}
