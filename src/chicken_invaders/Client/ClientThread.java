package chicken_invaders.Client;


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
                        System.out.println(obj);
                    }
                    if (obj instanceof String)
                    {
                        
                    }
                    if (obj instanceof Boolean)
                    {
                        
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
