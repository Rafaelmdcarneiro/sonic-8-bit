package game;

import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

import org.jbox2d.common.Vec2;

/**
 * Controller for the Sonic body.
 */
public class SonicController implements KeyListener {

    private static float WALKING_SPEED = 15;

    private Sonic sonic;

    /**
     * Creates an instance of the controller for the Sonic body.
     */
    public SonicController(Sonic s) {
        sonic = s;
    }

    /**
     * Actions on when a key is typed.
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Actions on when a specific key is pressed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // other key commands omitted

        if (code == KeyEvent.VK_D) {
            sonic.movementKeyPressed = true; // Sets the boolean to true
            sonic.startWalking(WALKING_SPEED); // Sonic starts walking
            sonic.sonicRunRight(); // Sonic looks right while running
            sonic.isLookingRight = true; // Sets the boolean to true
        }
        if (code == KeyEvent.VK_A) {
            sonic.movementKeyPressed = true; // Sets the boolean to true
            sonic.startWalking(-WALKING_SPEED); // Sonic starts walking
            sonic.sonicRunLeft(); // Sonic looks right while running
            sonic.isLookingRight = false; // Sets the boolean to true
        }
        if (code == KeyEvent.VK_W) {
            sonic.jump(20); // Sonic jumps
            sonic.onFloor = false; // Sets the boolean to false
            sonic.setGravityScale(2); // Set the gravity to 2
        }
    }

    /**
     * Actions on when a specific key is released.
     */
    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_D) {
            sonic.movementKeyPressed = false; // Sets the boolean to false
            if (sonic.onFloor) {
                // Sonic stops walking
                sonic.setLinearVelocity(new Vec2(0, 0));
                sonic.stopWalking();
                sonic.sonicRight(); // Sonic looks right
            }
        }
        if (code == KeyEvent.VK_A) {
            sonic.movementKeyPressed = false; // Sets the boolean to false
            if (sonic.onFloor) {
                // Sonic stops walking
                sonic.setLinearVelocity(new Vec2(0, 0));
                sonic.stopWalking();
                sonic.sonicLeft(); // Sonic looks right
            }
        }
    }

    /**
     * Updates the controller with a new Sonic body.
     */
    public void updateSonic(Sonic s) {
        this.sonic = s;
    }

}

