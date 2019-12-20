package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import game.ui.GameScreen;

import java.io.FileNotFoundException;

public class BubbleSpinnerController {

    transient GameScreen gameScreen;
    transient Stage stage;
    transient Shooter shooter;
    transient HexagonController hexagonController;

    /**
     * Constructor for the Controller of the Bubble Spinner game.
     * @param gameScreen GameScreen the game is currently played in.
     * @param stage Stage where all objects reside.
     */
    public BubbleSpinnerController(GameScreen gameScreen, Stage stage)
                                        throws FileNotFoundException {
        this.gameScreen = gameScreen;
        this.stage = stage;
        this.shooter = new Shooter(stage);
        this.hexagonController = new HexagonController(stage);
        this.shooter.initialize();
    }

    /**
     * This function is called each frame to refresh the
     * information the controller has about the game.
     */
    public void update() {
        BubbleActor bubble = shooter.current();

        checkShoot(bubble);

        bubble.update();

        if (hexagonController.checkCollisions(bubble)
            || bubble.belowScreen()) {
            shooter.poll();
            shooter.shiftBubbles();
            if (bubble.belowScreen()) {
                bubble.remove();
            }
        }

        if (hexagonController.lostGame) {
            gameScreen.dispose();
        }
    }

    /**
     * The function checks whether the bubble can be
     * shot from the shooter.
     * @param bubble is the first Bubble to be shot.
     */
    public void checkShoot(BubbleActor bubble) {
        if (Gdx.input.isTouched() && !bubble.isMoving()) {
            shooter.shootBubble();
        }
    }
}
