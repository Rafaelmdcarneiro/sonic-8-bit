package game;

import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import java.util.Random;
import java.util.Timer;

/**
 * The GameLevel class which adds bodies to the world.
 */
public abstract class GameLevel extends World {

    private Sonic sonic;
    private Goalpost goalpost;
    private Megaring megaring;
    private MegaringSpring megaringSpring;

    public GameLevel() {
    }

    /**
     * Populates the world with some bodies.
     */
    public void populate(Game game) {
        sonic = new Sonic(this);
        goalpost = new Goalpost(this);
        goalpost.setPosition(new Vec2(0, -30f));

        // Setting megaring and megaring spring positions
        megaring = new Megaring(this);
        megaring.setPosition(new Vec2(0, -30));
        megaringSpring = new MegaringSpring(this);
        megaringSpring.setPosition(new Vec2(0, -30));
    }

    /**
     * Ring scatter effect.
     */
    public void scatterRings(){
        for (int i = 0; i < getSonic().getRingCount(); i++){
            Ring ring  = new Ring(this);
            ring.setPosition(new Vec2(getSonic().getPosition()));
            ring.move(new Vec2(0, 7));
            ring.applyImpulse(new Vec2(new Random().nextInt(20), new Random().nextInt(20)));
        }
    }

    /**
     * Returns the current Sonic body in the world.
     */
    public Sonic getSonic() {
        return sonic;
    }

    /**
     * Sets the Sonic in the current GameLevel to a Sonic object provided.
     */
    public void setSonic(Sonic s) {
        sonic = s;
    }

    /**
     * Return the goalpost in the current level.
     */
    public Goalpost getGoalpost()
    {
        return goalpost;
    }

    /**
     * Set the goalpost in the world to a Goalpost object provided.
     */
    public void setGoalpost(Goalpost g) {
        goalpost = g;
    }

    /**
     * Hide the goalpost.
     */
    public void hideGoalpost(){
        goalpost.setPosition(new Vec2(0,  -30f));
    }

    /**
     * Returns the megaring in the current level.
     */
    public Megaring getMegaring() {
        return megaring;
    }

    /**
     * Sets the megaring in the world to a Megaring object provided.
     */
    public void setMegaring(Megaring mr) {
        megaring = mr;
    }

    /**
     * Returns the megaring spring in the world.
     */
    public MegaringSpring getMegaringSpring() {
        return megaringSpring;
    }

    /**
     * Sets the megaring spring in the world to a MegaringSpring object provided.
     */
    public void setMegaringSpring(MegaringSpring mrs){
        megaringSpring = mrs;
    }

    /**
     * Return the current level for when the game is saved.
     */
    public abstract String getLevel();

    /**
     * Adds a collision listener.
     */
    public abstract void collisionListener(Sonic sonic, Game game);

}
