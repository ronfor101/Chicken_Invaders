package chicken_invaders;

import java.awt.image.BufferedImage;
import java.io.Serializable;


public class Data implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    public int player;
    public BufferedImage screen;
    int score;
    //State 1 is update image
    //State 2 is send score
    int state;
    
    public Data(GameManager gm, int givenState)
    {
        this.state = givenState;
        this.player = gm.player;
        this.screen = gm.gameScreen;
        this.score = gm.gameScore;
    }
    
    public String toString()
    {
        return (state + " : " + player + " : " + score);
    }
}
