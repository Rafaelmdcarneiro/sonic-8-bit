package game;

import city.cs.engine.Shape;
import city.cs.engine.*;
import org.jbox2d.common.Vec2;
import java.awt.*;

/**
 * Level1 which inherits methods from GameLevel.
 */
public class Level1 extends GameLevel {
    /**
     * The ground in the level.
     */
    public StaticBody ground;
    /**
     * The left wall in the level.
     */
    public Wall wall1;
    /**
     * The right wall in the level.
     */
    public Wall wall2;

    /**
     * Creates a new Level1.
     */
    public Level1(Game game) {
        super();

        // colour transparent for set fill and line colour
        Color transparent = new Color(255, 255, 255, 0);

        // ground shape creation
        Shape groundShape = new BoxShape(50f, 0.25f);

        // ground instance
        this.ground = new StaticBody(this, groundShape);

        ground.setPosition(new Vec2(0f, -22.75f));
        ground.setFillColor(transparent);
        ground.setLineColor(transparent);

        // platform 1
        Shape platform1Shape = new BoxShape(4, 0.5f);
        StaticBody platform1 = new StaticBody(this, platform1Shape);

        platform1.setPosition(new Vec2(8, -15f));
        platform1.setFillColor(transparent);
        platform1.setLineColor(transparent);


        // platform 1 surface
        Shape platform1TopShape = new BoxShape(4, 0.1f);
        Platform platform1Top = new Platform(this, platform1TopShape);

        platform1Top.setPosition(new Vec2(8, -14.5f));
        platform1Top.setFillColor(transparent);
        platform1Top.setLineColor(transparent);


        // platform 2
        Shape platform2Shape = new BoxShape(10, 0.5f);
        StaticBody platform2 = new StaticBody(this, platform2Shape);

        platform2.setPosition(new Vec2(-13f, -7f));
        platform2.setFillColor(transparent);
        platform2.setLineColor(transparent);


        // platform 2 surface
        Shape platform2TopShape = new BoxShape(10, 0.1f);
        Platform platform2Top = new Platform(this, platform2TopShape);

        platform2Top.setPosition(new Vec2(-13f, -6.5f));
        platform2Top.setFillColor(transparent);
        platform2Top.setLineColor(transparent);


        // platform 3
        Shape platform3Shape = new BoxShape(7, 0.5f);
        StaticBody platform3 = new StaticBody(this, platform3Shape);

        platform3.setPosition(new Vec2(15, 0f));
        platform3.setFillColor(transparent);
        platform3.setLineColor(transparent);

        // platform 3 surface
        Shape platform3TopShape = new BoxShape(7, 0.1f);
        Platform platform3Top = new Platform(this, platform3TopShape);

        platform3Top.setPosition(new Vec2(15f, 0.5f));
        platform3Top.setFillColor(transparent);
        platform3Top.setLineColor(transparent);


        // wall creation
        Shape wallShape = new BoxShape(0.25f, 30f);

        // left wall
        this.wall1 = new Wall(this, wallShape);

        wall1.setPosition(new Vec2(-40.5f, 0));
        wall1.setFillColor(transparent);
        wall1.setLineColor(transparent);

        // right wall
        this.wall2 = new Wall(this, wallShape);

        wall2.setPosition(new Vec2(40.5f, 0));
        wall2.setFillColor(transparent);
        wall2.setLineColor(transparent);
    }

    /**
     * Populates the world with some bodies.
     */
    public void populate(Game game){
        super.populate(game);
        // setting Sonic's position
        getSonic().setPosition(new Vec2(-35, -22.5f));

        // spawning the rings
        Ring ring1 = new Ring(this);
        ring1.setPosition(new Vec2(8, -14));
        Ring ring2 = new Ring(this);
        ring2.setPosition(new Vec2(-17, -6));
        Ring ring3 = new Ring(this);
        ring3.setPosition(new Vec2(-9, -6));
        Ring ring4 = new Ring(this);
        ring4.setPosition(new Vec2(18, 1));
        Ring ring5 = new Ring(this);
        ring5.setPosition(new Vec2(12, 1));
    }

    /**
     * Adds a collision listener.
     */
    public void collisionListener(Sonic sonic, Game game) {
        getSonic().addCollisionListener(new SonicCollision(getSonic(), ground,this, this, wall1, wall2, game));
    }

    /**
     * Makes the goalpost visible.
     */
    public void setGoalpostPosition(){
        getGoalpost().setPosition(new Vec2(35, -20));
    }

    /**
     * Returns the current level name.
     */
    public String getLevel(){
        return "Level1";
    }


}