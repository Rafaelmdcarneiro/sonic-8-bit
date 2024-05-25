package game;

import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;

/**
 * Wall class.
 */
public class Wall extends StaticBody {
    /**
     * Adds a Wall to the world.
     */
    public Wall(World w, Shape s) {
        super(w, s);
    }
}
