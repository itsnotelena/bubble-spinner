package game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import config.Config;
import game.BubbleSpinner;
import game.BubbleSpinnerController;

public class GameScreen implements Screen {

    private transient BubbleSpinner game;
    private transient Stage stage;
    private transient OrthographicCamera camera;
    private transient BubbleSpinnerController bubbleSpinnerController;
    private transient long startingTime;
    private transient BitmapFont timerFont;
    private transient PauseMenu pauseMenu;
    private transient boolean paused;

    /**
     * This is Screen where the game is played.
     * @param game BubbleSpinner instance.
     */
    public GameScreen(BubbleSpinner game) {
        this.game = game;

        this.paused = false;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());

        Skin skin = new Skin(Gdx.files.internal("assets/uiskin.json"));


        bubbleSpinnerController = new BubbleSpinnerController(this, stage);

        startingTime = System.currentTimeMillis();
        timerFont = new BitmapFont();
        timerFont.setColor(Color.BLACK);
        timerFont.getData().setScale(2);
        this.pauseMenu = new PauseMenu(this, skin);
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

        game.batch.begin();
        timerFont.draw(game.batch,
                calculateRemainingTime(),
                Gdx.graphics.getHeight() / 8,
                7 * Gdx.graphics.getHeight() / 8
        );
        game.batch.end();

        stage.act();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.togglePause();
        }

        if (calculateRemainingTime().equals("00:00")) {
            dispose();
        }

        if(!paused){bubbleSpinnerController.update();}else{
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
        game.setScreen(new MenuScreen(game));
    }

    /**
     * Calculate the remaining time until the
     * game finishes.
     * @return a String with format minutes:seconds.
     */
    public String calculateRemainingTime() {
        long difference = (System.currentTimeMillis() - startingTime) / 1000;
        long remainingTime = Config.Game.GAME_TIME - difference;
        long minutes = remainingTime / 60;
        long seconds = remainingTime % 60;
        return new StringBuilder()
                .append((minutes < 10 ? '0' : ""))
                .append(minutes)
                .append(':')
                .append((seconds < 10 ? '0' : ""))
                .append(seconds)
                .toString();
    }

    public void togglePause(){
        this.paused = !this.paused;
    }
}
