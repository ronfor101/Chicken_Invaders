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
    public GameFrame()
    {
        gameMenu = new GameMenu(this);
        setTitle("Shiken Pooper By Ron");
        //setLayout(null);
        setSize(gameMenu.width,gameMenu.height);
        setResizable(false);
        setVisible(true);	
        setFocusable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(gameMenu);
        //gameMenu.setBounds(0, 0, gameMenu.width, gameMenu.height);
        
        
        //f.setExtendedState(JFrame.MAXIMIZED_BOTH);
        //f.setUndecorated(true);
        
    }
    
    public void hideMouseCursor()
    {
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
	cursorImg, new Point(0, 0), "blank cursor");
	setCursor(blankCursor);	
    }
    
    public void showMouseCursor()
    {
        setCursor(Cursor.getDefaultCursor());
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
        getContentPane().removeAll();
        
        if (players == 1) 
        {
            gamePanel = new GameManager(this, false);
            add(gamePanel);
            hideMouseCursor();
            mouseStartingPosition();
        }
        else if(players == 2)
        {
            gamePanel = new GameManager(this, true);
            add(gamePanel);
        }
        
        revalidate();
    }
    
    public static void main(String[] args)
    {
        GameFrame game = new GameFrame();
    }
}
