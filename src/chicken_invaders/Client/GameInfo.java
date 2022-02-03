package chicken_invaders.Client;

import chicken_invaders.GameManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class GameInfo extends JPanel
{
    GameManager game;
    Image enemyScreen;
    int width;
    int height;
    
    public GameInfo(GameManager game)
    {
        this.game = game;
        width = 512;
        height = 350;
        
        setBackground(Color.BLACK);
        enemyScreen = (new ImageIcon("placeholder.png")).getImage();
    }
    
    public void paintComponent(Graphics g)	
    {
	super.paintComponent(g);
        g.drawImage(enemyScreen,0,height,width,height,null);
        
        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 25));
        g.drawString("Health: ?????", (height / 2) - 50, height / 5);
        g.drawString("FirePower: " + game.ship.shipLevel, (height / 2) - 50, (height / 5) * 2);
        g.drawString("Score: " + game.gameScore, (height / 2) - 50, (height / 5) * 3);
        
        repaint();
    }
}
