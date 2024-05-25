package game;

import city.cs.engine.*;

/**
 * Spring object which also neighbours the Megaring.
 */
public class MegaringSpring extends StaticBody {

    private static final Shape SpringShape = new PolygonShape(
            -0.02f,0.83f,
            -0.64f,0.72f,
            -1.05f,0.43f,
            -1.35f,-0.15f,
            1.34f,-0.16f,
            1.02f,0.46f,
            0.52f,0.76f

    );

    private static final BodyImage SpringImage =
            new BodyImage("data/spring.png", 3);
    /**
     * Adds a MegaringSpring object to the world.
     */
    public MegaringSpring(World w){
        super(w, SpringShape);
        addImage(SpringImage);
    }
}
