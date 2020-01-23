package game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import game.BotController;
import game.BubbleSpinner;
import game.BubbleSpinnerController;
import game.GameSettings;

public class GameScreen implements Screen {

    protected transient BubbleSpinner game;
    private transient Stage stage;
    private transient OrthographicCamera camera;
    private transient BubbleSpinnerController bubbleSpinnerController;
    private transient PauseMenu pauseMenu;
    private transient GameSettings gameSettings;
    private transient GameMenu gameMenu;
    private transient AimingArrow aimingArrow;

    /**
     * This is Screen where the game is played.
     * @param game BubbleSpinner instance.
     */
    public GameScreen(BubbleSpinner game, GameSettings gameSettings) {
        this.game = game;
        this.gameSettings = gameSettings;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());

        if (gameSettings.isComputerPlayer()) {
            bubbleSpinnerController = new BotController(this, stage,
                                    gameSettings.getDifficulty());
        } else {
            bubbleSpinnerController = new BubbleSpinnerController(this, stage,
                                    gameSettings.getDifficulty());
        }
        bubbleSpinnerController.initialize();

        gameMenu = new GameMenu(game.batch, gameSettings, this);
        gameMenu.initialize();

        aimingArrow = new AimingArrow(stage, camera);

        pauseMenu = new PauseMenu(game, gameMenu);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        gameMenu.update();

        stage.act();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameMenu.togglePause();
        }

        if (!gameMenu.isPaused()) {
            bubbleSpinnerController.update();
            aimingArrow.draw();
        } else {
            pauseMenu.act();
            pauseMenu.draw();
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        if (gameSettings.isComputerPlayer()) {
            game.setScreen(new GameScreen(game, gameSettings));
        } else {
            game.setScreen(new LoseScreen(game));
        }
    }

    /**
     * If the game is won move to the next level.
     */
    public void nextLevel() {
        gameSettings.incrementLevel();
        if (gameSettings.isComputerPlayer()) {
            game.setScreen(new GameScreen(game, gameSettings));
        } else {
            game.setScreen(new WinScreen(game, gameSettings, bubbleSpinnerController.getResult()));
        }
    }

    public GameSettings getGameSettings() {
        return gameSettings;
    }

    public void setGameSettings(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }
}