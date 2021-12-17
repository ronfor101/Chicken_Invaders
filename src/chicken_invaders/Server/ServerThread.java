package chicken_invaders.Server;


public class ServerThread extends Thread implements java.util.Observer
{
    private InitServer server;
    private int index;
    private java.net.Socket socket;
    private java.io.ObjectInputStream in;
    private java.io.ObjectOutputStream out;
    
    public ServerThread(InitServer server, int index, java.net.Socket socket) 
    {
        this.server = server;
        this.index = index;
        this.socket = socket;
    } 
    
    public void run() 
    {
        try 
        {
            in = new java.io.ObjectInputStream(socket.getInputStream());
            out = new java.io.ObjectOutputStream(socket.getOutputStream());
            
            if (index < 2) 
            {
                out.writeObject(new Integer(1));
            }
            else
            {
                out.writeObject(new Integer(2));
            }
            
            out.flush();
            Object obj;
            while ((obj = in.readObject()) != null) 
            {
                if (obj instanceof String) 
                {
                    
                }
                if (obj instanceof Integer) 
                {
                    
                }
            }
        }
        catch (Exception e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
        finally 
        {
            try { in.close(); } catch (Exception e2) { }
            try { out.close(); } catch (Exception e2) { }
            try { socket.close(); } catch (Exception e2) { }
            server.remove(index, this);
        }
    }
    
    public void update(java.util.Observable o, Object arg) 
    {
        try 
        {
            out.writeObject(arg);
            out.flush();
        } 
        catch (java.io.IOException ex) 
        {
            
        }
    }
}