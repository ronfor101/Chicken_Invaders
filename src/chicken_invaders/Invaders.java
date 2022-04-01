package chicken_invaders;

import java.awt.*;
import java.util.Random;
import javax.swing.*;

public class Invaders extends Thread
{
    GameManager gamePanel;
    boolean direction;
    int leftLimit, rightLimit;
    int x, y;
    int size;
    int health;
    int wave;
    boolean isAlive;
    Random rnd;
    Image redInvImage; // 1 hp
    Image blueInvImage; // 2 hp
    Image greenInvImage; // 3 hp
    Image yellowInvImage; // 4 hp
    Image purpleInvImage; // 5 hp
    Image blackInvImage; // 6 hp
    Image shipInvUmage; // more then 6 hp
    
    public Invaders(GameManager gamePanel, int x, int y, int wave)
    {
        direction = true;
        health = wave;
        size = 70;
        this.x = x;
        this.y = y;
        this.gamePanel = gamePanel;
        isAlive = true;
        rnd = new Random();
        redInvImage = (new ImageIcon("RedChicken.png")).getImage();
        blueInvImage = (new ImageIcon("BlueChicken.png")).getImage();
        greenInvImage = (new ImageIcon("GreenChicken.png")).getImage();
        yellowInvImage = (new ImageIcon("YellowChicken.png")).getImage();
        purpleInvImage = (new ImageIcon("PurpleChicken.png")).getImage();
        blackInvImage = (new ImageIcon("BlackChicken.png")).getImage();
        shipInvUmage = (new ImageIcon("ShipChicken.gif")).getImage();
        
        leftLimit = this.x - 80;
        rightLimit = this.x + 80;
        
        start();
    }
    
    public void moveChicken()
    {
        if (direction) 
        {
            x -= 2;
        }
        else
        {
            x += 2;
        }
        
        if (this.x <= leftLimit) 
        {
            direction = false;
        }
        else if (this.x >= rightLimit)
        {
            direction = true;
        }
    }

    public void spawnUpgrade()
    {
        if (rnd.nextInt(16) == 1) 
        {
            gamePanel.upgradeDrops.add(new UpgradeDrop(gamePanel, x, y));
        }
    }
    
    public void spawnEgg()
    {
        gamePanel.invadersProjectiles.add(new InvaderProjectile(gamePanel, x, y));
    }
    
    public void drawInvader(Graphics g)
    {
        if (health == 1) {g.drawImage(redInvImage, x, y, size, size, null);}
        else if(health == 2){g.drawImage(blueInvImage, x, y, size, size, null);}
        else if(health == 3){g.drawImage(greenInvImage, x, y, size, size, null);}
        else if(health == 4){g.drawImage(yellowInvImage, x, y, size, size, null);}
        else if(health == 5){g.drawImage(purpleInvImage, x, y, size, size, null);}
        else if(health == 6){g.drawImage(blackInvImage, x, y, size, size, null);}
        else if(health > 6){g.drawImage(shipInvUmage, x, y, size, size, null);}
    }
    
    public void run()
    {
        while(isAlive)
        {
            try
            {
                gamePanel.repaint();
                Thread.sleep(10);
            }
            catch (InterruptedException e) {}
        }
    }
}
