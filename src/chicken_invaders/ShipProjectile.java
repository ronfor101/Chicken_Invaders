package chicken_invaders;

import java.awt.*;
import javax.swing.*;


public class ShipProjectile extends Thread
{
    JPanel gamePanel;
    int x = 0, y = 0;
    int size = 25;
    boolean isAlive;
    Image projectileImage;
    
    public ShipProjectile(JPanel gamePanel, int x, int y)
    {
        this.x = x + 23;
        this.y = y - 40;
        this.gamePanel = gamePanel;
        isAlive = true;
        projectileImage = (new ImageIcon("Projectile.png")).getImage();
        start();
    }
    
    public void drawProjectile(Graphics g, int level)
    {
        g.drawImage(projectileImage, x, y, size,size + 40,null);
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
            y = y - 5;
            if (y < 0) 
            {
                isAlive = false;
            }
            gamePanel.repaint();
        }
    }
}
