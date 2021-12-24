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
                        clientPanel.index = (int)obj;
                    }
                    if (obj instanceof String)
                    {
                        System.out.println("got " + (String)obj);
                        String temp = (String)obj;
                        String[] tempA = temp.split(",", 3);
                        
                        if (Integer.parseInt(tempA[2]) != clientPanel.index) 
                        {
                            clientPanel.ship2Cords[0] = Integer.parseInt(tempA[0]);
                            clientPanel.ship2Cords[1] = Integer.parseInt(tempA[1]);
                        }
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
