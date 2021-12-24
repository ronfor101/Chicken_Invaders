/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chicken_invaders.Server;

/**
 *
 * @author ronfo
 */
public class InitServer extends java.util.Observable
{
    public ServerGameManager game = new ServerGameManager(this);
    
    public static void main(String[] args) 
    {
        int port = 25565;
        new InitServer().handleClients(port);
    }
    
    public void handleClients(int port) 
    {
        //game.start();
        java.net.ServerSocket serverSocket = null;
        try 
        {
            System.out.println("The Server is ready...");
            serverSocket = new java.net.ServerSocket(port);
            for (int i = 0; i < 2 ;i++) 
            {
                java.net.Socket socket = serverSocket.accept();
                update("A new client: " + i);
                ServerThread clientThread = new ServerThread(this, i, socket);
                addObserver(clientThread);
                clientThread.start(); 
            }
        }
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
        }
        finally 
        {
            if (serverSocket != null)
                try 
                { 
                    serverSocket.close(); 
                }
                catch (java.io.IOException x) {}
        }
    }
    
    public void update(Object obj)
    {
        System.out.println("sent:" + obj.toString());
        setChanged();
        notifyObservers(obj);
    }
    
    public void remove(int index, ServerThread clientThread) 
    {
        deleteObserver(clientThread);
        //update("Client " + index + " has left.");
    }
}
