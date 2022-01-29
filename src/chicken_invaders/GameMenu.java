package chicken_invaders;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;


public class GameMenu extends JPanel
{
    int width;
    int height;
    GameFrame mainFrame;
    
    Image backgroundImage;
    Image startImage;
    Image multiImage;
    Image logo;
    
    public GameMenu(GameFrame frame)
    {
        mainFrame = frame;
        width = 1024;
        height = 700;
        backgroundImage = (new ImageIcon("Background.jpg")).getImage();
        startImage = (new ImageIcon("StartButton.png")).getImage();
        multiImage = (new ImageIcon("MultiButton.png")).getImage();
        logo = (new ImageIcon("Logo.png")).getImage();
        setLayout(null);
        
        JButton startButton = new JButton("Start");
        startButton.setBorderPainted(false); 
        startButton.setContentAreaFilled(false); 
        startImage = startImage.getScaledInstance(300, 100, Image.SCALE_DEFAULT);
        startButton.setIcon(new ImageIcon(startImage));
        startButton.setBounds(362, 400, 310, 100);
        add(startButton);
        startButton.addActionListener((ActionEvent e) ->
        {
            mainFrame.switchPanel(1);
        });
        
        JButton multiButton = new JButton("Muiltiplayer");
        multiButton.setBorderPainted(false); 
        multiButton.setContentAreaFilled(false); 
        multiImage = multiImage.getScaledInstance(300, 100, Image.SCALE_DEFAULT);
        multiButton.setIcon(new ImageIcon(multiImage));
        multiButton.setBounds(362, 500, 310, 100);
        add(multiButton);
        multiButton.addActionListener((ActionEvent e) ->
        {
            mainFrame.switchPanel(2);
        });
    }
    
    public void paintComponent(Graphics g)	
    {
	super.paintComponent(g);
        g.drawImage(backgroundImage,0,0,getWidth(),getHeight(),null);
        g.drawImage(logo, 20, 40, 1000, 500, null);
    }
}
