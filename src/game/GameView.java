package game;

import city.cs.engine.UserView;
import city.cs.engine.World;
import javax.swing.*;
import java.awt.*;


/**
 * GameView for the world.
 */
public class GameView extends UserView {
    private Image background;
    private Sonic sonic;
    private GameLevel level;


    /**
     * Creates the GameView.
     */
    public GameView(World w, Sonic s, GameLevel l, int width, int height) {
        super(w, width, height);
        this.sonic = s;
        this.level = l;
        background = new ImageIcon("data/level1platform.png").getImage();
    }

    /**
     * Updates the view with a new Sonic and GameLevel.
     */
    public void updateView(Sonic s, GameLevel l) {
        this.sonic = s;
        this.level = l;
    }

    Image threeHearts = new ImageIcon("data/3hearts.png").getImage();
    Image twoHearts = new ImageIcon("data/2hearts.png").getImage();
    Image oneHeart = new ImageIcon("data/1heart.png").getImage();

    /**
     * Sets the background variable to the Level1 background.
     */
    public void Level1Image(){
        background = new ImageIcon("data/level1platform.png").getImage();
    }

    /**
     * Sets the background variable to the Level2 background.
     */
    public void Level2Image(){
        background = new ImageIcon("data/level2platform.png").getImage();
    }

    /**
     * Sets the background variable to the Level3 background.
     */
    public void Level3Image(){
        background = new ImageIcon("data/level3platform.png").getImage();
    }

    /**
     * Changes the background.
     */
    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(background, 0, 0, 800, 450, this);
    }

    /**
     * Adds a ring counter, lives counter and a megaring counter (only in Level3) which
     * constantly updates.
     */
    @Override
    protected void paintForeground(Graphics2D g){
        if (level instanceof Level3){
            g.setColor(Color.white);
            g.setFont(new Font("Helvetica", Font.PLAIN, 10));
            g.drawString("Mega Rings Collected: " + this.sonic.megaringCount + "/1", 10, 70);
        }
        g.setColor(Color.white);
        g.setFont(new Font("Helvetica", Font.PLAIN, 10));
        g.drawString("Rings Collected: " + this.sonic.ringCount + "/5", 10, 50);
        if (sonic.lives == 3){
            g.drawImage(threeHearts, 10, 10, 84, 22, this);
        }
        if (sonic.lives == 2){
            g.drawImage(twoHearts, 10, 10, 84, 22, this);
        }
        if (sonic.lives == 1){
            g.drawImage(oneHeart, 10, 10, 84, 22, this);
        }
    }
}
