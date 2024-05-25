package game;

import city.cs.engine.*;
/**
 * Megaring object.
 */
public class Megaring extends Walker {

    private static final Shape megaringShape = new CircleShape(2f);

    private static final BodyImage image =
            new BodyImage("data/rings.gif", 5f);
    /**
     * Adds a Megaring object to the world.
     */
    public Megaring(World w) {
        super(w, megaringShape);
        addImage(image);

    }
}
