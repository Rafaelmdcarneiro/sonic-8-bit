package game;

import city.cs.engine.*;

/**
 * Ring object.
 */
public class Ring extends Walker {

    private static final Shape ringsShape = new CircleShape(1.5f);

    private static final BodyImage image =
            new BodyImage("data/rings.gif", 1.5f);

    /**
     * Adds a Ring object to the world.
     */
    public Ring(World w) {
        super(w, ringsShape);
        addImage(image);

    }
}
