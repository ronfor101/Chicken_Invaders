package chicken_invaders.Client;

import chicken_invaders.GameManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class GameInfo extends JPanel
{
    GameManager game;
    int width;
    int height;
    public Image enemyScreen;
    Image background;
    Image heart;
    Image emptyHeart;
    Image upgrade;
    int iconSize;
    
    public GameInfo(GameManager game)
    {
        background = (new ImageIcon("GameInfoBackground.jpg")).getImage();
        heart = (new ImageIcon("Heart.png")).getImage();
        emptyHeart = (new ImageIcon("EmptyHeart.png")).getImage();
        upgrade = (new ImageIcon("Upgrade.png")).getImage();
        iconSize = 50;
        
        this.game = game;
        width = 512;
        height = 350;
        
        setBackground(Color.BLACK);
        enemyScreen = (new ImageIcon("placeholder.png")).getImage();
    }
    
    public void paintComponent(Graphics g)	
    {
	super.paintComponent(g);
        g.drawImage(background,0,0,getWidth(),getHeight(),null);
        
        for (int i = 0; i < game.lives; i++) 
        {
            g.drawImage(heart,((height / 2) - 50) + (i * 50), (height / 5), iconSize, iconSize, null);
        }
        
        for (int i = 0; i < (3 - game.lives); i++) 
        {
            g.drawImage(emptyHeart,((height / 2) - 50) + ((game.lives + i) * 50), (height / 5), iconSize, iconSize, null);
        }
        
        for (int i = 0; i < game.ship.shipLevel; i++) 
        {
            g.drawImage(upgrade,(height / 2) + (i * 55), (height / 5) + 85, iconSize, iconSize, null);
        }
        
        //g.drawImage(enemyScreen,0,height,width,height,null);
        
        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 25));
        g.drawString("Health: ", 30, (height / 5) + 30);
        g.drawString("Ship Level: ", 30, (height / 5) + 120);
        
        repaint();
    }
}
