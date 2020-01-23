package game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import config.Config;
import game.ui.GameScreen;

import java.util.List;
import java.util.Random;

public class BotController extends BubbleSpinnerController {

    private transient int[] bounds;

    /**
     * Constructor for the Controller of the Bubble Spinner game.
     *
     * @param gameScreen GameScreen the game is currently played in.
     * @param stage      Stage where all objects reside.
     */
    public BotController(GameScreen gameScreen, Stage stage, int difficulty) {
        super(gameScreen, stage, difficulty);
    }

    public void initialize() {
        calculateBounds();
    }

    @Override
    public void checkShoot(BubbleActor bubble) {
        if (!bubble.isMoving()) {
            Random rnd = new Random();
            shooter.shootBubbleScreenCoords(rnd.nextInt(bounds[1] - bounds[0]) + bounds[0],
                                            rnd.nextInt(bounds[3] - bounds[2]) + bounds[2]);
        }
    }

    /**
     * For now calculate the minimum and maximum bounds of the bubbles
     * in the hexagon and shoot based on that.
     */
    private void calculateBounds() {
        bounds = new int[] {    Integer.MAX_VALUE, Integer.MIN_VALUE,
                                Integer.MAX_VALUE, Integer.MIN_VALUE };
        List<BubbleActor> bubbles = hexagonController.getBubbles();
        for (int i = 0; i < bubbles.size(); ++i) {
            Vector2 bubblePosition = bubbles.get(i).getPosition();
            bounds[0] = Math.min(bounds[0], (int)bubblePosition.x);
            bounds[1] = Math.max(bounds[1], (int)bubblePosition.x);
            bounds[2] = Math.min(bounds[2], (int)bubblePosition.y);
            bounds[3] = Math.max(bounds[3], (int)bubblePosition.y);
        }
        bounds[1] += Config.Game.BUBBLE_SIZE;
        bounds[3] += Config.Game.BUBBLE_SIZE;
    }

    /**
     * Set custom bounds for the bot to shoot.
     */
    public void setBounds(int[] bounds) {
        this.bounds = bounds;
    }
}
