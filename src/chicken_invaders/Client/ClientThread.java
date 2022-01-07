package chicken_invaders.Client;

import chicken_invaders.GameData;


public class ClientThread extends Thread
{
    ClientGamePanel clientPanel;
    
    public ClientThread(ClientGamePanel clientPanel)
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
                    if (obj instanceof Integer)
                    {
                        clientPanel.index = (int)obj;
                        System.out.println(clientPanel.index);
                    }
                    if (obj instanceof GameData)
                    {
                        clientPanel.tempData = (GameData)obj;
                        //System.out.println(clientPanel.tempData.toString());
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
