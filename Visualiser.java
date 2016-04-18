/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package antgame;
import javax.swing.*;
import java.awt.*;
/**
 *
 * @author oscar
 */
public class Visualiser {
    private char[][] map;
    private JFrame window;
    private JTextArea textBox;
    public Visualiser(int x, int y)
    {
        window = new JFrame("Ant game");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textBox = new JTextArea();
        textBox.setColumns(x+1);
        textBox.setRows(y);
        textBox.setEditable(false);
        textBox.setLineWrap(true);
        window.add(textBox);
        map = new char[x][];
        for (int i = 0; i < x; i++)
        {
            map[i] = new char[y];
            for (int j = 0; j < y; j++)
            {
                map[i][j] = ',';
            }
        }
        map[10][15] = 'c';
        window.setVisible(true);
        updateDisplay();
        window.setResizable(false);
        window.setSize(1000,1000);
    }
    
    public void setCell(int x, int y, char c)
    {

    }
    
    private void updateDisplay()
    {
        for (int i = 0; i < map[0].length; i++)
        {
            for (int j = 0; j < map.length; j++)
            {
                textBox.append(String.valueOf(map[i][j]));
            }
        }
    }
    
    public static void main(String[] args)
    {
        new Visualiser(150, 150);
    }
}
