package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Sonic character.
 */
public class Sonic extends Walker{

    public static final Shape SonicShape = new PolygonShape(
            -0.82f,2.48f,
            0.81f,2.49f,
            1.83f,0.98f,
            1.84f,-2.49f,
            -1.86f,-2.48f,
            -1.85f,0.98f
    );
    /**
     * Boolean to check if Sonic is on the floor.
     */
    public boolean onFloor = true;
    /**
     * Boolean to check if Sonic is looking right.
     */
    public boolean isLookingRight = true;
    /**
     * Boolean to check if either movement keys are pressed.
     */
    public boolean movementKeyPressed;
    /**
     * Sonic's ring count.
     */
    public int ringCount;
    /**
     * Sonic's megaring count.
     */
    public int megaringCount;
    /**
     * Sonic's amount of lives.
     */
    public int lives;
    private SoundClip spikeHit;
    private SoundClip loseRings;

    /**
     * Adds a Sonic body to the world.
     */
    public Sonic(World world) {
        super(world, SonicShape);
        addImage(imageRight);

        ringCount = 0;
        megaringCount = 0;
        lives = 3;

    }

    private static final BodyImage imageRight =
            new BodyImage("data/sonicright.png", 5f);

    private static final BodyImage imageLeft =
            new BodyImage("data/sonicleft.png", 5f);

    private static final BodyImage imageRunRight =
            new BodyImage("data/sonicrunright.gif", 5f);

    private static final BodyImage imageRunLeft =
            new BodyImage("data/sonicrunleft.gif", 5f);

    private static final BodyImage sonicInjured =
            new BodyImage(("data/sonichurt.png"), 5f);

    /**
     * Makes Sonic look right.
     */
    public void sonicRight(){
        removeAllImages();
        addImage(imageRight);
    }

    /**
     * Makes Sonic look left.
     */
    public void sonicLeft(){
        removeAllImages();
        addImage(imageLeft);
    }

    /**
     * Makes Sonic look right while running.
     */
    public void sonicRunRight(){
        removeAllImages();
        addImage(imageRunRight);
    }

    /**
     * Makes Sonic look left while running..
     */
    public void sonicRunLeft() {
        removeAllImages();
        addImage(imageRunLeft);
    }

    /**
     * Puts Sonic into a brief injured state.
     */
    public void sonicHurt() {
        System.out.println("Hurt!");
        removeAllImages();
        addImage(sonicInjured); // Hurt version of Sonic
        try {
            spikeHit = new SoundClip("data/spikehit.wav");   // Open an audio input stream
            spikeHit.setVolume(0.005); // Set the volume
            spikeHit.play(); // Play the sound
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
        stopWalking();
        setLinearVelocity(new Vec2(0,0));
        applyImpulse(new Vec2(0, 1000));
    }

    /**
     * Spring effect.
     */
    public void sonicSpring(){
        stopWalking();
        setLinearVelocity(new Vec2(0,0));
        applyImpulse(new Vec2(0, 2000));
    }

    /**
     * Adds a ring to Sonic's ring counter.
     */
    public void addRings(){
        ringCount++; // Add a ring
        System.out.println("Rings collected: " + ringCount + "/5");
    }

    /**
     * Removes all of Sonic's rings.
     */
    public void removeRings(){
        ringCount = 0;
        try {
            loseRings = new SoundClip("data/loserings.wav");   // Open an audio input stream
            loseRings.setVolume(0.005); // Set the volume
            loseRings.play(); // Play the sound
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
        System.out.println("You've lost all your rings!");
    }

    /**
     * Removes one of Sonic's lives.
     */
    public void livesDecrement(){
        lives--;
        System.out.println("You've lost a life!");
        if (lives == 0){
            System.exit(0); // Exit the game
        }
    }

    /**
     * Adds a megaring to Sonic's megaring count.
     */
    public void addMegaring(){
        megaringCount++; // Add a megaring to Sonic's counter
        System.out.println("Mega ring collected!");
    }

    /**
     * Returns Sonic's ring count.
     */
    public int getRingCount(){
        return ringCount; // return count
    }


}