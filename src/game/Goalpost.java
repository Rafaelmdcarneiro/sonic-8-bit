package game;

import city.cs.engine.*;

/**
 * Goalpost object.
 */
public class Goalpost extends StaticBody {

    private static final Shape goalpostShape = new PolygonShape(
            -0.01f,3.49f,
            -0.87f,2.94f,
            -0.87f,-3.49f,
            0.84f,-3.48f,
            0.86f,2.75f
    );
    private static final BodyImage goalpost =
            new BodyImage("data/goalpost.png", 7f);

    /**
     * Adds a Goalpost object to the world.
     */
    public Goalpost(World w) {
        super(w, goalpostShape);
        addImage(goalpost);
    }
}
