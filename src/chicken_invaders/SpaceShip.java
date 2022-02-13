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
    Image shieldImage;
    
    public SpaceShip(GameManager gamePanel)
    {
        shipLevel = 1;
        isAlive = true;
        this.gamePanel = gamePanel;
        shieldImage = (new ImageIcon("Shield.png")).getImage();
        shipImage = (new ImageIcon("SpaceShip.png")).getImage();
        start();
    }
    
    public void drawShip(Graphics g)
    {
        g.drawImage(shipImage, x, y, size,size,null);
        if (gamePanel.shipImmun) 
        {
            g.drawImage(shieldImage, x - 30, y - 30, size + 60,size + 60,null);
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
