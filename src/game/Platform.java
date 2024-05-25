package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;

/**
 * Platform class.
 */
public class Platform extends StaticBody {

    /**
     * Adds a Platform to the world.
     */
    public Platform(World w, Shape s) {
        super(w, s);
    }
}
