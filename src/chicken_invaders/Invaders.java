package chicken_invaders;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class Invaders extends Thread
{
    GameManager gamePanel;
    int x, y;
    int size;
    int life;
    boolean isAlive;
    Random rnd;
    Image invaderImage;
    
    public Invaders(GameManager gamePanel, int x, int y)
    {
        life = 3;
        size = 70;
        this.x = x;
        this.y = y;
        this.gamePanel = gamePanel;
        isAlive = true;
        rnd = new Random();
        invaderImage = (new ImageIcon("RedChicken.png")).getImage();
        start();
    }
    
    public void spawnUpgrade()
    {
        if (rnd.nextInt(10) == 1) 
        {
            gamePanel.upgradeDrops.add(new UpgradeDrop(gamePanel, x, y));
        }
    }
    
    public void drawInvader(Graphics g)
    {
        g.drawImage(invaderImage, x, y, size, size, null);
    }
    
    public void run()
    {
        while(true)
        {
            try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException e) {}
            
            if (rnd.nextInt(5000) == 555 && isAlive) 
            {
                gamePanel.invadersProjectiles.add(new InvaderProjectile(gamePanel, x, y));
            }
            
            gamePanel.repaint();
        }
    }
}
