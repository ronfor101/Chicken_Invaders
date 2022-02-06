package chicken_invaders;

import java.awt.image.BufferedImage;
import java.io.Serializable;
import javax.swing.ImageIcon;


public class Data implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public int player;
    public ImageIcon screen = new ImageIcon();
    int score;
    //State 1 is update image
    //State 2 is send score
    int state;
    
    public Data(GameManager gm, int givenState)
    {
        this.state = givenState;
        this.player = gm.player;
        this.score = gm.gameScore;
        
        if (gm.gameScreen != null) 
        {
            this.screen = new ImageIcon(gm.gameScreen);
        }
    }
    
    public String toString()
    {
        return (state + " : " + player + " : " + score);
    }
}
