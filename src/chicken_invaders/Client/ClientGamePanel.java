package chicken_invaders.Client;

import chicken_invaders.GameData;
import chicken_invaders.SpaceShip;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.*;

public class ClientGamePanel extends JPanel
{
    //final String SERVER_IP = "79.183.186.221";
    final int PORT = 25565;
    java.net.Socket socket;
    public java.io.ObjectOutputStream objectOutputStream;
    public java.io.ObjectInputStream objectInputStream;
    
    int width;
    int height;
    Image backgroundImage;
    Image ship2;
    
    int index;
    SpaceShip ship1;
    GameData tempData;
    int ship2X = -100;
    int ship2Y = -100;
    
    ClientThread clientThread;
    
    public ClientGamePanel() 
    {
        width = 1024;
        height = 700;
        ship1 = new SpaceShip(this);
        ship2 = (new ImageIcon("SpaceShip2.png")).getImage();
        backgroundImage = (new ImageIcon("Background.jpg")).getImage();
        
        addMouseMotionListener(new MouseMovement());
        mouseStartingPosition();
        hideMouseCursor();
        
        this.clientThread = new ClientThread(this);
        this.Connect();
        this.clientThread.start();
    }
    
    public void paintComponent(Graphics g)	
    {
	super.paintComponent(g);
        g.drawImage(backgroundImage,0,0,getWidth(),getHeight(),null);
        if(tempData != null && tempData.state == 0)
        {
            if (tempData.playerIndex != index) 
            {
                System.out.println(tempData.toString());
                ship2X = tempData.shipX;
                ship2Y = tempData.shipY;
            }
        }
        g.drawImage(ship2,ship2X,ship2Y,ship1.size,ship1.size,null);
        ship1.drawShip(1,g);
        
    }
    
    public void Connect()
    {
        try 
        {
            //this.socket = new java.net.Socket(java.net.InetAddress.getByName("10.30.56.93"), PORT);
            this.socket = new java.net.Socket("localhost", PORT);
            this.objectOutputStream = new java.io.ObjectOutputStream(this.socket.getOutputStream());
            this.objectInputStream = new java.io.ObjectInputStream(this.socket.getInputStream());
        }
        catch (java.io.IOException ex) 
        { 
            
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
            
        }
    }
    
    public void buildGame()
    {
        
    }
    
    public void updateGame()
    {
        
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
    
    public static void mouseStartingPosition()
    {
        try 
        {
            Robot r = new Robot();
            r.mouseMove(480, 500);
        } 
        catch (Exception e) {}
    }
    
    class MouseMovement extends MouseMotionAdapter
    {
	public void mouseMoved(MouseEvent e) 
        {
            ship1.x = e.getX();
            ship1.y = e.getY();
            if (ship1.x >= width - ship1.size) 
            {
                ship1.x = width - ship1.size - 10;
            }
            if (ship1.y >= height - 125) 
            {
                ship1.y = height - 125;
            }
            send(new GameData(index,ship1.x,ship1.y));
        }
        public void mouseDragged(MouseEvent e) 
        {
            ship1.x = e.getX();
            ship1.y = e.getY();
            if (ship1.x >= width - ship1.size) 
            {
                ship1.x = width - ship1.size - 10;
            }
            if (ship1.y >= height - 125) 
            {
                ship1.y = height - 125;
            }
            send(new GameData(index,ship1.x,ship1.y));
        }
    }
}
