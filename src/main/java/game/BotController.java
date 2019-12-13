package game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import game.ui.GameScreen;

import java.util.Random;

public class BotController extends BubbleSpinnerController {

    /**
     * Constructor for the Controller of the Bubble Spinner game.
     *
     * @param gameScreen GameScreen the game is currently played in.
     * @param stage      Stage where all objects reside.
     */
    public BotController(GameScreen gameScreen, Stage stage) {
        super(gameScreen, stage);
    }

    @Override
    public void checkShoot(BubbleActor bubble) {
        if (!bubble.isMoving()) {
            Random rnd = new Random();
            shooter.shootBubbleScreenCoords(rnd.nextInt(400) + 400,
                                            rnd.nextInt(300) + 350);
        }
    }
}
