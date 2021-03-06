package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import config.Config;
import game.ui.GameScreen;

public class BubbleSpinnerController {

    transient GameScreen gameScreen;
    transient Stage stage;
    transient Shooter shooter;
    transient HexagonController hexagonController;
    transient int difficulty;

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
        this.difficulty = difficulty;
    }

    private void difficultyLevel(int difficulty) {
        if (difficulty == Config.Difficulty.easy) {
            this.hexagonController.setBuilder(new EasyHexagonStrategy());
        } else if (difficulty == Config.Difficulty.med) {
            this.hexagonController.setBuilder(new MediumHexagonStrategy());
        } else {
            this.hexagonController.setBuilder(new HardHexagonStrategy());
        }
        this.hexagonController.getBuilder().setupUpHexagon(this.hexagonController);
    }

    /**
     * Initialize the hexagon and shooter.
     */
    public void initialize() {

        this.hexagonController.initialize();

        this.hexagonController.drawGrid();
        this.difficultyLevel(difficulty);

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

        checkCurrentBubble(bubble);

        hexagonController.checkGameStatus(gameScreen);
    }

    private void checkCurrentBubble(BubbleActor bubble) {
        if (hexagonController.checkCollisions(bubble)
                || bubble.belowScreen() || bubble.aboveScreen()) {
            shooter.poll();
            shooter.shiftBubbles(hexagonController.getMapBubbles());
            if (bubble.belowScreen() || bubble.aboveScreen()) {
                bubble.remove();
                hexagonController.bubbleMissed();
            }
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

    /**
     * Set difficulty of the game.
     * @param difficulty 0-1-2 for Easy-Medium-Hard.
     */
    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

}
