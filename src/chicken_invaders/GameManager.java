package chicken_invaders;

import chicken_invaders.Client.ClientThread;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class GameManager extends JPanel
{
    //////////////////////////multiplayer//////////////////////////
    final int PORT = 25565;
    java.net.Socket socket;
    public java.io.ObjectOutputStream objectOutputStream;
    public java.io.ObjectInputStream objectInputStream;
    
    ClientThread clientThread;
    boolean mpSentScore;
    boolean mpState;
    public boolean mpStart;
    public boolean mpOppDead;
    public BufferedImage gameScreen;
    public int oppScore;
    public int player;
    ///////////////////////////////////////////////////////////////
    
    //////////////////////////single player//////////////////////////
    boolean gameActive;
    public int gameScore;
    GameManager gamePanel = this;
    public SpaceShip ship;
    Image backgroundImage;
    static GameFrame mainFrame;
    
    ArrayList<ShipProjectile> projectiles;
    ArrayList<Invaders> invaders;
    ArrayList<InvaderProjectile> invadersProjectiles;
    ArrayList<UpgradeDrop> upgradeDrops;
    
    int width;
    int height;
    
    long waveTimeGathered;
    long shipImmuTimeGathered;
    boolean shipImmun;
    boolean showWave;
    int wave;
    public int lives;
    ////////////////////////////////////////////////////////////////
    
    public GameManager(GameFrame frame, boolean mpState)
    {
        mpSentScore = false;
        mpOppDead = false;
        mpStart = true;
        this.mpState = mpState;
        //If the game is multiplayer then connect to server
        if (this.mpState) 
        {
            player = 0;
            mpStart = false;
            this.clientThread = new ClientThread(this);
            this.Connect();
            this.clientThread.start();
            send("ready");
        }
        
        //init game panel
        mainFrame = frame;
        gameActive = true;
        gameScore = 0;
        width = 1024;
        height = 700;
        backgroundImage = (new ImageIcon("Background.jpg")).getImage();
        ship = new SpaceShip(this);
        projectiles = new ArrayList<ShipProjectile>();
        invaders = new ArrayList<Invaders>();
        invadersProjectiles = new ArrayList<InvaderProjectile>();
        upgradeDrops = new ArrayList<UpgradeDrop>();
        shipImmun = true;
        shipImmuTimeGathered = System.currentTimeMillis();
        showWave = true;
        wave = 1;
        waveTimeGathered = System.currentTimeMillis();
        lives = 3;
        
        spawnInvaders();
        
        //mouse listener for movment and clicks
        addMouseMotionListener(new MouseMovement());
        addMouseListener(new MouseAdapter() 
        { 
            public void mousePressed(MouseEvent me) 
            { 
                if (gameActive) 
                {
                    if (ship.shipLevel == 1) 
                    {
                        projectiles.add(new ShipProjectile(gamePanel, ship.x, ship.y));
                    }
                    else if (ship.shipLevel == 2) 
                    {
                        projectiles.add(new ShipProjectile(gamePanel, ship.x - 10, ship.y));
                        projectiles.add(new ShipProjectile(gamePanel, ship.x + 10, ship.y));
                    }
                    else if (ship.shipLevel == 3) 
                    {
                        projectiles.add(new ShipProjectile(gamePanel, ship.x, ship.y - 10));
                        projectiles.add(new ShipProjectile(gamePanel, ship.x - 20, ship.y));
                        projectiles.add(new ShipProjectile(gamePanel, ship.x + 20, ship.y));
                    }
                    
                }
                else
                {
                    if (mpState) 
                    {
                        if (mpOppDead) 
                        {
                            ship.isAlive = true;
                            hideMouseCursor();
                            spawnInvaders();
                            mouseStartingPosition();
                            gameScore = 0;
                            gameActive = true;

                            mpSentScore = false;
                            mpOppDead = false;
                            mpStart = false;
                            showWave = true;
                            waveTimeGathered = System.currentTimeMillis();
                            send("ready");
                        }
                    }
                    else
                    {
                        ship.isAlive = true;
                        hideMouseCursor();
                        spawnInvaders();
                        mouseStartingPosition();
                        gameScore = 0;
                        gameActive = true;
                    }
                }
            }
        });
    }
    
    public void paintComponent(Graphics g)	
    {
	super.paintComponent(g);
        g.drawImage(backgroundImage,0,0,getWidth(),getHeight(),null);
        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 20));
        
        if (gameActive && !showWave) 
        {
            g.drawString("Score: " + gameScore, 5, 20);
            g.drawString("Health: " + lives, 5, 655);
            hit();
            //Draws The Ship
            if (ship.isAlive) 
            {
                ship.drawShip(g);
            }
            
            //Draws The Invaders Projectiles
            for (int i = 0; i < invadersProjectiles.size(); i++) 
            {
                InvaderProjectile temp = invadersProjectiles.get(i);
                if (temp.isAlive) 
                {
                    temp.drawInvaderProjectile(g);
                }
                else
                {
                    invadersProjectiles.remove(temp);
                }
            }
            
            //Draws The Invaders
            for (int i = 0; i < invaders.size(); i++) 
            {
                Invaders temp = invaders.get(i);
                if (temp.isAlive) 
                {
                    temp.drawInvader(g);
                }
                else
                {
                    temp.spawnUpgrade();
                    
                    invaders.remove(temp);
                }
            }
        
            //Draws The Projectiles
            for (int i = 0; i < projectiles.size(); i++) 
            {
                ShipProjectile temp = projectiles.get(i);
                if (temp.isAlive) 
                {
                    temp.drawProjectile(g, ship.shipLevel);
                }
                else
                {
                    projectiles.remove(temp);
                }
            }
            
            //Draws The Upgrades
            for (int i = 0; i < upgradeDrops.size(); i++) 
            {
                UpgradeDrop temp = upgradeDrops.get(i);
                if (temp.isAlive) 
                {
                    temp.drawUpgrade(g);
                }
                else
                {
                    upgradeDrops.remove(temp);
                }
            }
            
            if (shipImmun) 
            {
                if (cooldownOver(shipImmuTimeGathered, 2000)) 
                {
                    shipImmun = false;
                }
            }
            
            if (invaders.isEmpty()) 
            {
                wave++;
                showWave = true;
                waveTimeGathered = System.currentTimeMillis();
                
                projectiles.clear();
                invadersProjectiles.clear();
                invaders.clear();
                upgradeDrops.clear();
                if (ship.shipLevel > 1) 
                {
                    ship.shipLevel--;
                }
                spawnInvaders();
            }
        }
        else if (!gameActive)
        {
            for (int i = 0; i < invaders.size(); i++) 
            {
                invaders.get(i).isAlive = false;
            }
            
            projectiles.clear();
            invadersProjectiles.clear();
            invaders.clear();
            upgradeDrops.clear();
            
            ship.shipLevel = 1;
            lives = 3;
            
            showMouseCursor();
            
            if (mpState) 
            {
                g.setColor(Color.white);
                g.setFont(new Font("Arial", 1, 50));
                g.drawString("Your Score: " + gameScore, 310, (height / 2) - 60);
                
                if (mpOppDead) 
                {
                    g.drawString("Opponent's Score: " + oppScore, 230, (height / 2));
                    if (gameScore > oppScore) 
                    {
                        g.setFont(new Font("Arial", 1, 100));
                        g.drawString("You Won!!!", 230, 150);
                        
                    }
                    else
                    {
                        g.setFont(new Font("Arial", 1, 100));
                        g.drawString("You Lost :(", 230, 150);
                    }
                }
                else
                {
                    g.setFont(new Font("Arial", 1, 100));
                    g.drawString("Game Over!", 230, 150);
                    
                    g.setFont(new Font("Arial", 1, 50));
                    g.drawString("Opponent Still Alive", 280, (height / 2));
                }
            }
            else
            {
                g.setFont(new Font("Arial", 1, 100));
                g.drawString("Game Over!", 230, 150);
                
                g.setFont(new Font("Arial", 1, 50));
                g.drawString("Score: " + gameScore, 390, (height / 2) - 60);
            }
            
            g.setFont(new Font("Arial", 1, 25));
            g.drawString("Click Left Mouse Button To Restart", 300, 650);
            
            if (mpState && !mpSentScore) 
            {
                send(new Data(this));
                mpSentScore = true;
            }
        }
        else if (showWave) 
        {
            g.setColor(Color.white);
            g.setFont(new Font("Arial", 1, 100));
            g.drawString("Wave " + wave, 330, height / 2);
            if (!mpStart) 
            {
                shipImmuTimeGathered = System.currentTimeMillis();
                waveTimeGathered = System.currentTimeMillis();
            }
            if (cooldownOver(waveTimeGathered, 2000)) 
            {
                showWave = false;
                mouseStartingPosition();
            }
        }
    }
    
    public void hideMouseCursor()
    {
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(
	cursorImg, new Point(0, 0), "blank cursor");
	setCursor(blankCursor);	
    }
    
    public void showMouseCursor()
    {
        setCursor(Cursor.getDefaultCursor());
    }
    
    class MouseMovement extends MouseMotionAdapter
    {
	public void mouseMoved(MouseEvent e) 
        {
            ship.x = e.getX();
            ship.y = e.getY();
            if (ship.x >= width - ship.size) 
            {
                ship.x = width - ship.size - 10;
            }
            if (ship.y >= height - 125) 
            {
                ship.y = height - 125;
            }
        }
        public void mouseDragged(MouseEvent e) 
        {
            ship.x = e.getX();
            ship.y = e.getY();
            if (ship.x >= width - ship.size) 
            {
                ship.x = width - ship.size - 10;
            }
            if (ship.y >= height - 125) 
            {
                ship.y = height - 125;
            }
        }
    }
    
    public void hit()
    {
        //Space Ship *Projectiles* and Invaders Hit
        ShipProjectile currentProj;
        Invaders currentInv;
        InvaderProjectile currentInvProj;
        UpgradeDrop currentDrop;
        
        for (int i = 0; i < projectiles.size(); i++) 
        {
            currentProj = projectiles.get(i);
            for (int j = 0; j < invaders.size(); j++) 
            {
                currentInv = invaders.get(j);
                if (currentProj.x < currentInv.x + currentInv.size && currentProj.x + currentProj.size > currentInv.x && currentProj.y < currentInv.y + currentInv.size &&(currentProj.size + 40) + currentProj.y > currentInv.y) 
                {
                    currentInv.life--;
                    if (currentInv.life <= 0) 
                    {
                        currentInv.isAlive = false;
                        gameScore += 50;
                    }
                    currentProj.isAlive = false;
                    break;
                }
            }
        }
        
        //Space Ship and Invader Projectiles Hit
        for (int j = 0; j < invadersProjectiles.size(); j++) 
        {
            currentInvProj = invadersProjectiles.get(j);
            if (!shipImmun && ship.x < currentInvProj.x + currentInvProj.size && ship.x + ship.size > currentInvProj.x && ship.y < currentInvProj.y + currentInvProj.size + 5 && ship.size + ship.y > currentInvProj.y) 
            {
                shipImmun = true;
                shipImmuTimeGathered = System.currentTimeMillis();
                currentInvProj.isAlive = false;
                mouseStartingPosition();
                lives--;
                if (lives <= 0) 
                {
                    ship.isAlive = false;
                    gameActive = false;
                }
                break;
            }
        }
        
        //Space Ship And Upgrade Drop
        for (int j = 0; j < upgradeDrops.size(); j++) 
        {
            currentDrop = upgradeDrops.get(j);
            if (ship.x < currentDrop.x + currentDrop.size && ship.x + ship.size > currentDrop.x && ship.y < currentDrop.y + currentDrop.size && ship.size + ship.y > currentDrop.y) 
            {
                if (ship.shipLevel < 3) 
                {
                    ship.shipLevel++;
                }
                currentDrop.isAlive = false;
                break;
            }
        }
        
        //Space Ship and Invaders Hit
        for (int j = 0; j < invaders.size(); j++) 
        {
            currentInv = invaders.get(j);
            if (!shipImmun && ship.x < currentInv.x + currentInv.size && ship.x + ship.size > currentInv.x && ship.y < currentInv.y + currentInv.size && ship.size + ship.y > currentInv.y) 
            {
                shipImmun = true;
                shipImmuTimeGathered = System.currentTimeMillis();
                mouseStartingPosition();
                lives--;
                if (lives <= 0) 
                {
                    ship.isAlive = false;
                    gameActive = false;
                }
                break;
            }
        }
    }
    
    public void spawnInvaders()
    {
        int invX = 125;
        int invY = 25;
        for (int i = 0; i < 8; i++) 
        {
            invaders.add(new Invaders(this, invX, invY));
            invX += 100;
        }
        invX = 125;
        invY += 100;
        for (int i = 0; i < 8; i++) 
        {
            invaders.add(new Invaders(this, invX, invY));
            invX += 100;
        }
        invX = 125;
        invY += 100;
        for (int i = 0; i < 8; i++) 
        {
            invaders.add(new Invaders(this, invX, invY));
            invX += 100;
        }
    }
    
    public static void mouseStartingPosition()
    {
        try 
        {
            Robot r = new Robot();
            r.mouseMove(mainFrame.getX() + 480,mainFrame.getY() + 500);
        } 
        catch (Exception e) {}
    }
    
    public boolean cooldownOver(long timeGathered, int coolDownTime)
    {
        long time = System.currentTimeMillis();
        if (time > timeGathered + coolDownTime) 
        {
            timeGathered = time;
            return true;
        }
        return false;
    }
    
    //Multiplayer Functions
    public void Connect()
    {
        try 
        {
            //this.socket = new java.net.Socket(java.net.InetAddress.getByName("79.183.186.221"), PORT);
            this.socket = new java.net.Socket("localhost", PORT);
            this.objectOutputStream = new java.io.ObjectOutputStream(this.socket.getOutputStream());
            this.objectInputStream = new java.io.ObjectInputStream(this.socket.getInputStream());
        }
        catch (java.io.IOException ex) 
        { 
            System.out.println("Somthing went wrong, Could not connect :( ");
        }
    }
    
    public void send(Object obj) 
    {
        try 
        {
            this.objectOutputStream.writeObject(obj);
            this.objectOutputStream.flush();
        }
        catch (java.io.IOException ex) 
        {
            System.out.println("Somthing went wrong, Could not send information :( ");
        }
    }
    
    public void screenShot() {
        try {
            Point p = getLocationOnScreen();
            Dimension dim = new Dimension(1024, 700);
            Rectangle rect = new Rectangle(p, dim);
            gameScreen = (new Robot()).createScreenCapture(rect);
        }
        catch (Exception e) { }
    }
}
