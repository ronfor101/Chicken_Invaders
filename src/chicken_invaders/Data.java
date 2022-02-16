package chicken_invaders;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import javax.swing.ImageIcon;


public class Data implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public int player;
    public int score;
    
    public Data(GameManager gm)
    {
        this.player = gm.player;
        this.score = gm.gameScore;
    }
    
    public String toString()
    {
        return (player + " : " + score);
    }
}
