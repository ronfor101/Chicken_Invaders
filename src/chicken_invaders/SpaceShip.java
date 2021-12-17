package chicken_invaders;

import java.awt.*;
import javax.swing.*;

public class SpaceShip extends Thread
{
    JPanel gamePanel;
    public int x = 0, y = 0;
    public int size = 70;
    boolean isAlive;
    public int shipLevel;
    Image shipImage1;
    Image shipImage2;
    
    public SpaceShip(JPanel gamePanel)
    {
        shipLevel = 1;
        isAlive = true;
        this.gamePanel = gamePanel;
        shipImage1 = (new ImageIcon("SpaceShip.png")).getImage();
        shipImage2 = (new ImageIcon("SpaceShip2.png")).getImage();
        start();
    }
    
    public void drawShip(int skin, Graphics g)
    {
        if (skin == 1) 
        {
            g.drawImage(shipImage1, x, y, size,size,null);
        }
        else if(skin == 2)
        {
            g.drawImage(shipImage2, x, y, size,size,null);
        }
    }
    
    public void fireProjectile()
    {
        ShipProjectile projectile = new ShipProjectile(gamePanel, x, y);
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
            
            gamePanel.repaint();
        }
    }
}
