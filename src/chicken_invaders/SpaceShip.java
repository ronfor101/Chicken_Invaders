package chicken_invaders;

import java.awt.*;
import javax.swing.*;

public class SpaceShip extends Thread
{
    GameManager gamePanel;
    int x = 0, y = 0;
    int size = 70;
    boolean isAlive;
    public int shipLevel;
    Image shipImage;
    
    public SpaceShip(GameManager gamePanel)
    {
        shipLevel = 1;
        isAlive = true;
        this.gamePanel = gamePanel;
        shipImage = (new ImageIcon("SpaceShip.png")).getImage();
        start();
    }
    
    public void drawShip(Graphics g)
    {
        g.drawImage(shipImage, x, y, size,size,null);
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
