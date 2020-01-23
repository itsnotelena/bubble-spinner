package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import game.ui.GameScreen;

public class BubbleSpinnerController {

    transient GameScreen gameScreen;
    transient Stage stage;
    transient Shooter shooter;
    transient HexagonController hexagonController;
    private static int THRESHOLD_BUBBLES = 1;

    /**
     * Constructor for the Controller of the Bubble Spinner game.
     * @param gameScreen GameScreen the game is currently played in.
     * @param stage Stage where all objects reside.
     */
    public BubbleSpinnerController(GameScreen gameScreen, Stage stage, int difficulty) {
        this.gameScreen = gameScreen;
        this.stage = stage;
        this.shooter = new Shooter(stage, difficulty);
        this.hexagonController = new HexagonController(stage, difficulty);
    }

    /**
     * Create the shooter and hexagon objects in the game.
     */
    public void initialize() {
        this.hexagonController.initialize();
        this.hexagonController.drawGrid();
        this.shooter.initialize(hexagonController.getMapBubbles());
    }

    /**
     * This function is called each frame to refresh the
     * information the controller has about the game.
     */
    public void update() {
        BubbleActor bubble = shooter.current();
        checkShoot(bubble);
        bubble.update();

        hexagonController.drawGrid();

        if (hexagonController.checkCollisions(bubble)
            || bubble.belowScreen() || bubble.aboveScreen()) {
            shooter.poll();
            shooter.shiftBubbles(hexagonController.getMapBubbles());
            if (bubble.belowScreen() || bubble.aboveScreen()) {
                bubble.remove();
                hexagonController.bubbleMissed();
            }
        }

        if (hexagonController.lostGame) {
            gameScreen.dispose();
        }

        if (hexagonController.getBubbles().size() == THRESHOLD_BUBBLES) {
            gameScreen.nextLevel();
        }
    }

    /**
     * The function checks whether the bubble can be
     * shot from the shooter.
     * @param bubble is the first Bubble to be shot.
     */
    public void checkShoot(BubbleActor bubble) {
        if ((Gdx.input.isTouched() || Gdx.input.isKeyJustPressed(Input.Keys.SPACE))
                    && !bubble.isMoving()) {
            shooter.shootBubble();
        }
    }

    /**
     * Get the game score.
     * @return Int with the result.
     */
    public int getResult() {
        return hexagonController.getResult();
    }

    /**
     * Set custom hexagon.
     * @param hexagonController HexagonController object.
     */
    public void setHexagonController(HexagonController hexagonController) {
        this.hexagonController = hexagonController;
    }

    /**
     * Set custom shooter.
     * @param shooter Shooter object.
     */
    public void setShooter(Shooter shooter) {
        this.shooter = shooter;
    }

}
