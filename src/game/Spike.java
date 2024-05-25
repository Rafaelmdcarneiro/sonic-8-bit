package game;

import city.cs.engine.*;

/**
 * Spike object.
 */
public class Spike extends StaticBody {
    private static final Shape spikeShape = new PolygonShape(
            -0.615f,0.956f,
            -0.841f,-0.964f,
            0.835f,-0.962f,
            0.627f,0.952f
    );
    private static final BodyImage spikes =
            new BodyImage("data/spikes.png", 2);

    /**
     * Adds a Spike object to the world.
     */
    public Spike(World w){
        super(w, spikeShape);
        addImage(spikes);
    }
}
