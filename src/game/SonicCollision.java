package game;

import city.cs.engine.CollisionEvent;
import city.cs.engine.CollisionListener;
import city.cs.engine.SoundClip;
import city.cs.engine.StaticBody;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/**
 * Sonic's collision conditions and actions.
 */
public class SonicCollision implements CollisionListener {

    private Sonic sonic;
    private StaticBody ground;
    private GameLevel level;
    private Wall leftWall;
    private Wall rightWall;
    private Level1 level1;
    private Level2 level2;
    private Level3 level3;
    private Megaring megaring;
    private Spring megaringspring;
    private Game game;

    /**
     * Constructor if SonicCollision is called from Level1.
     */
    public SonicCollision(Sonic s, StaticBody g, GameLevel l, Level1 l1, Wall lw, Wall rw, Game game) {
        this.sonic = s;
        this.ground = g;
        this.level = l;
        this.level1 = l1;
        this.leftWall = lw;
        this.rightWall = rw;
        this.game = game;
    }

    /**
     * Constructor if SonicCollision is called from Level2.
     */
    public SonicCollision(Sonic s, StaticBody g, GameLevel l, Level2 l2, Wall lw, Wall rw, Game game) {
        this.sonic = s;
        this.ground = g;
        this.level = l;
        this.level2 = l2;
        this.leftWall = lw;
        this.rightWall = rw;
        this.game = game;
    }

    /**
     * Constructor if SonicCollision is called from Level3
     * - an addition of a megaring and its neighbouring spring.
     */
    public SonicCollision(Sonic s, StaticBody g, GameLevel l, Level3 l3, Wall lw, Wall rw, Megaring mr, Spring mrs, Game game) {
        this.sonic = s;
        this.ground = g;
        this.level = l;
        this.level3 = l3;
        this.leftWall = lw;
        this.rightWall = rw;
        this.megaring = mr;
        this.megaringspring = mrs;
        this.game = game;
    }

    /**
     * Collision conditions and actions if a collision occurs.
     */
    @Override
    public void collide(CollisionEvent e) {
        if (e.getOtherBody() instanceof Ring) {
            // add rings to Sonic's counter
            sonic.addRings();
            // destroy the ring after pick up
            e.getOtherBody().destroy();
            // play the ring collect sound
            try {
                SoundClip ringSound = new SoundClip("data/ringcollect.wav");   // Open an audio input stream
                ringSound.setVolume(0.005); // Set the volume
                ringSound.play(); // Play the sound
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException a) {
                System.out.println(a);
            }
            if (sonic.ringCount == 5) {
                if (level instanceof Level1) {
                    // show the goalpost in level 1
                    level1.setGoalpostPosition();
                }
                if (level instanceof Level2) {
                    // show the goalpost in level 2
                    level2.setGoalpostPosition();
                }
                if (level instanceof Level3) {
                    if (sonic.megaringCount == 1) {
                        // show the goalpost in level 3
                        level3.setGoalpostPosition();
                    }
                    else {
                        // show the megaring in level 3
                        level3.setMegaringPosition();
                    }
                }
            }
        }
        if (e.getOtherBody() instanceof Megaring) {
            // add the megaring to Sonic's counter
            sonic.addMegaring();
            // destroy the ring after pick up
            e.getOtherBody().destroy();
            // play the ring collect sound
            try {
                SoundClip ringSound = new SoundClip("data/ringcollect.wav");   // Open an audio input stream
                ringSound.setVolume(0.005); // Set the volume
                ringSound.play(); // Play the sound
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException a) {
                System.out.println(a);
            }
            if (sonic.megaringCount == 1) {
                level3.setMegaringSpringPosition();
                level3.setGoalpostPosition();
            }
        }
        if (e.getOtherBody() instanceof Lava) {
            System.out.println("You died!");
            // Close the game
            System.exit(0);
        }
        if (e.getOtherBody() instanceof Spike) {
            sonic.sonicHurt();
            if (sonic.ringCount > 0) {
                // hide the goal
                level.hideGoalpost();
                // ring scatter effect
                level.scatterRings();
                // set Sonic's ring counter to 0
                sonic.removeRings();
            }
            else {
                // take away one of Sonic's lives
                sonic.livesDecrement();
            }
        }
        if (e.getOtherBody() instanceof Spike) {
            if (level instanceof Level3) {
                // hide the megaring
                level3.hideMegaring();
            }
        }
        if (e.getOtherBody() instanceof Spring) {
            // spring effect
            sonic.sonicSpring();
        }
        if (e.getOtherBody() instanceof MegaringSpring) {
            // spring effect
            sonic.sonicSpring();
        }
        if (e.getOtherBody() == ground || e.getOtherBody() instanceof Platform) {
            System.out.println("Collision!");
            sonic.onFloor = true;
            if (!sonic.movementKeyPressed) {
                sonic.setLinearVelocity(new Vec2(0, 0));
                sonic.stopWalking();
                if (sonic.isLookingRight) {
                    // Sonic looks right
                    sonic.sonicRight();
                } else {
                    // Sonic looks left
                    sonic.sonicLeft();
                }
            }
        }
        if (e.getOtherBody() == leftWall) {
            // Sonic stops walking
            sonic.stopWalking();
            sonic.setLinearVelocity(new Vec2(0, 0));
            // bounce effect
            sonic.applyImpulse(new Vec2(500, 0));
        }
        if (e.getOtherBody() == rightWall) {
            // Sonic stops walking
            sonic.stopWalking();
            sonic.setLinearVelocity(new Vec2(0, 0));
            // bounce effect
            sonic.applyImpulse(new Vec2(-500, 0));
        }
        if (e.getOtherBody() == level.getGoalpost() && sonic.ringCount >= 5) {
            // go to next level
            game.goToNextLevel();
        }

    }
}