package chicken_invaders;

import java.awt.*;
import javax.swing.*;


public class UpgradeDrop extends Thread
{
    GameManager gamePanel;
    int x = 0, y = 0;
    int size = 25;
    boolean isAlive;
    Image upgradeImage;
    
    public UpgradeDrop(GameManager gamePanel, int x, int y)
    {
        this.x = x + 23;
        this.y = y + 35;
        this.gamePanel = gamePanel;
        isAlive = true;
        upgradeImage = (new ImageIcon("Upgrade.gif")).getImage();
        start();
    }
    
    public void drawUpgrade(Graphics g)
    {
        g.drawImage(upgradeImage, x, y, size,size,null);
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
            
            y = y + 1;
            if (y > 700) 
            {
                isAlive = false;
            }
            gamePanel.repaint();
        }
    }
}
