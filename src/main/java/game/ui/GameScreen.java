package game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import config.Config;
import game.BubbleSpinner;
import game.BubbleSpinnerController;

public class GameScreen implements Screen   {

    private transient BubbleSpinner game;
    private transient Stage stage;
    private transient OrthographicCamera camera;
    private transient BubbleSpinnerController bubbleSpinnerController;
    private transient long startingTime;
    private transient BitmapFont timerFont;
    private transient ShapeRenderer shapeRenderer;

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

        startingTime = System.currentTimeMillis();
        timerFont = new BitmapFont();
        timerFont.setColor(Color.BLACK);
        timerFont.getData().setScale(2);

        shapeRenderer = new ShapeRenderer();
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
        drawArrow();

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

    /**
     * Draw an arrow based on the current
     * position of the mouse.
     */
    private void drawArrow() {
        final int bubbleSize = 64;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(Color.BLACK);

        // Calculate Stage Coordinates for mouse
        Vector2 pos = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        stage.screenToStageCoordinates(pos);

        Vector2 intersection = new Vector2(-1, -1);
        if (pos.x < Gdx.graphics.getWidth() / 2) {                      // Check left or right side
            Intersector.intersectLines(Gdx.graphics.getWidth() / 2, // Bubble center X
                    Gdx.graphics.getHeight() - bubbleSize / 2,      // Bubble center Y
                    pos.x,                                              // Mouse position X
                    pos.y,                                              // Mouse position Y
                    bubbleSize / 2,                                 // Left Origin + half bubble X
                    0,                                              // Left Origin Y
                    bubbleSize / 2,                                 // Left wall + half bubble X
                    Gdx.graphics.getHeight(),                           // Left wall Height
                    intersection                                        // Intersection vector
            );
        } else {
            Intersector.intersectLines(Gdx.graphics.getWidth() / 2, // Bubble center X
                    Gdx.graphics.getHeight() - bubbleSize / 2,      // Bubble center Y
                    pos.x,                                              // Mouse position X
                    pos.y,                                              // Mouse position Y
                    1280 - (bubbleSize / 2),                        // Right Origin - half bubble X
                    0,                                              // Right Origin Y
                    1280 - (bubbleSize / 2),                        // Right wall - half bubble X
                    Gdx.graphics.getHeight(),                           // Right wall Height
                    intersection                                        // Intersection vector
            );
        }
        shapeRenderer.line(Gdx.graphics.getWidth() / 2,             // Bubble center X
                Gdx.graphics.getHeight() - bubbleSize / 2,          // Bubble center Y, to:
                intersection.x,                                        // Intersection X
                intersection.y                                         // Intersection Y
        );

        float m = -1 * (intersection.y - (Gdx.graphics.getHeight() - (bubbleSize / 2)))  // Delta Y
                    / (intersection.x - (Gdx.graphics.getWidth() / 2));                  // Delta X

        if (pos.x < Gdx.graphics.getWidth() / 2) {              // Check left or right side
            float y = m * 1280 + intersection.y;                // Calculate y intersection
            shapeRenderer.line(intersection.x,                  // Intersection X
                    intersection.y,                             // Intersection Y
                    1280,                                   // Right wall X
                    y                                           // Right wall Y
            );
        } else {
            float q = intersection.y - m * intersection.x;      // Calculate q intersection
            shapeRenderer.line(intersection.x,                  // Intersection X
                    intersection.y,                             // Intersection Y
                    0,                                      // Left wall X
                    q                                           // Left wall Y
            );
        }

        shapeRenderer.end();
    }
}
