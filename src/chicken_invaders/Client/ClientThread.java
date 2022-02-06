package chicken_invaders.Client;

import chicken_invaders.Data;
import chicken_invaders.GameManager;


public class ClientThread extends Thread
{
    GameManager clientPanel;
    
    public ClientThread(GameManager clientPanel)
    {
        this.clientPanel = clientPanel;
    }
    
    public void run()
    {
        while(true)
        {
            try 
            {
                Object obj;
                while ((obj = clientPanel.objectInputStream.readObject()) != null) 
                {
                    if (obj instanceof Data)
                    {
                        clientPanel.enemyData = (Data) obj;
                        System.out.println(clientPanel.enemyData.toString());
                    }
                    if (obj instanceof Integer)
                    {
                        clientPanel.player = (Integer)obj;
                        System.out.println(clientPanel.player);
                    }
                }
            } 
            catch (java.io.IOException ex) 
            {
                
            } 
            
            catch (ClassNotFoundException ex) 
            {
                
            } 
        }
    }
}
