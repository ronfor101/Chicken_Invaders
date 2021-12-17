package chicken_invaders;

import java.awt.*;
import javax.swing.*;


public class InvaderProjectile extends Thread
{
    GameManager gamePanel;
    int x = 0, y = 0;
    int size = 25;
    boolean isAlive;
    Image invaderProjectileImage;
    
    public InvaderProjectile(GameManager gamePanel, int x, int y)
    {
        this.x = x + 23;
        this.y = y + 35;
        this.gamePanel = gamePanel;
        isAlive = true;
        invaderProjectileImage = (new ImageIcon("InvaderProjectile.png")).getImage();
        start();
    }
    
    public void drawInvaderProjectile(Graphics g)
    {
        g.drawImage(invaderProjectileImage, x, y, size,size + 5,null);
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
            
            y = y + 2;
            if (y > 700) 
            {
                isAlive = false;
            }
            gamePanel.repaint();
        }
    }
}
