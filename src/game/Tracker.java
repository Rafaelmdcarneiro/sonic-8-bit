package game;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import org.jbox2d.common.Vec2;

public class Tracker implements StepListener {
    private GameView view;
    private Sonic sonic;
    public Tracker(GameView view, Sonic sonic) {
        this.view = view;
        this.sonic = sonic;
    }
    public void preStep(StepEvent e) {}
    public void postStep(StepEvent e) {
        view.setCentre(new Vec2(sonic.getPosition()));
    }
}