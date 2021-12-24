package chicken_invaders.Server;

import chicken_invaders.SpaceShip;


public class ServerGameManager extends Thread
{
    InitServer initSer;
    SpaceShip ship1;
    SpaceShip ship2;
    int[] ship1Cords;
    int[] ship2Cords;
    
    public ServerGameManager(InitServer initSer)
    {
        this.initSer = initSer;
        ship1Cords = new int[3];
        ship2Cords = new int[3];
    }
    
    public void play()
    {
        
    }
    
    public void run()
    {
        
    }
}
