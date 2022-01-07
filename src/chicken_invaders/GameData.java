package chicken_invaders;

import java.io.Serializable;


public class GameData implements Serializable
{
    private static final long serialVersionUID = 1L;
    public int state;
    
    //Ship information (state = 0)
    public int playerIndex;
    public int shipX;
    public int shipY;
    
    //Game information (state = 1)
    
    public GameData(int playerIndex, int shipX, int ShipY)
    {
        this.state = 0;
        this.playerIndex = playerIndex;
        this.shipX = shipX;
        this.shipY = ShipY;
    }
    
    public String toString()
    {
        return (playerIndex + " " + shipX + " " + shipY);
    }
}
