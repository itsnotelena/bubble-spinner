package game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import game.BubbleSpinner;
import game.BubbleSpinnerController;

import java.util.Date;

public class GameScreen implements Screen {

    private transient BubbleSpinner game;
    private transient Stage stage;
    private transient OrthographicCamera camera;
    private transient BubbleSpinnerController bubbleSpinnerController;
    private transient Date startingTime;
    private transient BitmapFont timerFont;

    /**
     * This is Screen where the game is played.
     * @param game BubbleSpinner instance.
     */
    public GameScreen(BubbleSpinner game) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());

        bubbleSpinnerController = new BubbleSpinnerController(this, stage);

        startingTime = new Date();
        timerFont = new BitmapFont();
        timerFont.setColor(Color.BLACK);
        timerFont.getData().setScale(2);
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

        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new SplashScreen(game));
        }

        if (calculateRemainingTime().equals("00:00")) {
            game.setScreen(new MenuScreen(game));
            dispose();
        }

        bubbleSpinnerController.update();
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
        game.setScreen(new SplashScreen(game));
    }

    /**
     * Calculate the remaining time until the
     * game finishes.
     * @return a String with format minutes:seconds.
     */
    public String calculateRemainingTime() {
        long difference = (new Date().getTime() - startingTime.getTime()) / 1000;
        long remainingTime = 600 - difference;
        long minutes = remainingTime / 60;
        long seconds = remainingTime % 60;
        String timer = (minutes < 10 ? "0" : "") + minutes + ":"
                        + (seconds < 10 ? "0" : "") + seconds;
        return timer;
    }
}
