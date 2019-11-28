package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class BubbleSpinnerController {

    private transient GameScreen gameScreen;
    private transient Stage stage;
    private transient Shooter shooter;
    private transient HexagonController hexagonController;

    /**
     * Constructor for the Controller of the Bubble Spinner game.
     * @param gameScreen GameScreen the game is currently played in.
     * @param stage Stage where all objects reside.
     */
    public BubbleSpinnerController(GameScreen gameScreen, Stage stage) {
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

        if (Gdx.input.isTouched()) {
            shooter.shootBubble();
        }

        bubble.update();

        if (hexagonController.checkCollisions(bubble)) {
            shooter.poll();
            shooter.shiftBubbles();
        }

        if (hexagonController.lostGame) {
            gameScreen.dispose();
        }
    }
}
