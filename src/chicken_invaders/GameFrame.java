package chicken_invaders;

import chicken_invaders.Client.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

public class GameFrame extends JFrame 
{
    GameMenu gameMenu;
    GameManager gamePanel;
    JPanel container;
    GameInfo gameInfo;
    public GameFrame()
    {
        gameMenu = new GameMenu(this);
        gameMenu.setBounds(0, 0, 1024, 700);
        
        //Container for all of the panels
        container = new JPanel();
        container.setBackground(Color.red);
        container.setLayout(null);
        container.setSize(1024, 700);
        container.add(gameMenu);
        
        setTitle("Chicken Invaders By Ron");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setSize(1024, 700);
        setResizable(false);
        setVisible(true);	
        setFocusable(false);
        
        add(container);
        
        //f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //f.setUndecorated(true);
    }
    
    public static void mouseStartingPosition()
    {
        try 
        {
            Robot r = new Robot();
            r.mouseMove(480, 500);
        } 
        catch (Exception e) {}
    }
    
    public void switchPanel(int players)
    {
        container.removeAll();
        
        if (players == 1) 
        {
            gamePanel = new GameManager(this, false);
            gamePanel.setBounds(0, 0, 1024, 700);
            container.add(gamePanel);
            
            gamePanel.hideMouseCursor();
            gamePanel.mouseStartingPosition();
        }
        else if(players == 2)
        {
            setSize(1536, 700);
            gamePanel = new GameManager(this, true);
            gamePanel.setBounds(0, 0, 1024, 700);
            container.add(gamePanel);
            
            gameInfo = new GameInfo(gamePanel);
            gameInfo.setBounds(1024, 0, 512, 700);
            add(gameInfo);
        }
        
        container.revalidate();
    }
    
    public static void main(String[] args)
    {
        GameFrame game = new GameFrame();
    }
}
