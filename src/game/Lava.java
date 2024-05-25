package game;

import city.cs.engine.*;

/**
 * Lava object.
 */
public class Lava extends StaticBody {

    private static final Shape lavaShape = new BoxShape(40, 4);

    private static final BodyImage image =
            new BodyImage("data/lava.gif", 80f);

    /**
     * Adds a Lava object to the world.
     */
    public Lava(World w) {
        super(w, lavaShape);
        addImage(image);
    }
}
