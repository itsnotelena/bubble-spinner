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
import game.BotController;
import game.BubbleSpinner;
import game.BubbleSpinnerController;

public class GameScreen implements Screen {

    private transient BubbleSpinner game;
    private transient Stage stage;
    private transient OrthographicCamera camera;
    private transient BubbleSpinnerController bubbleSpinnerController;
    private transient long startingTime;
    private transient BitmapFont timerFont;
    private transient ShapeRenderer shapeRenderer;
    private transient TutorialHelpBox tutorialHelpBox;

    /**
     * This is Screen where the game is played.
     * @param game BubbleSpinner instance.
     */
    public GameScreen(BubbleSpinner game, boolean computer) {
        this.game = game;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());

        if (computer) {
            bubbleSpinnerController = new BotController(this, stage);
            tutorialHelpBox = new TutorialHelpBox(game.tutorialBatch);
        } else {
            bubbleSpinnerController = new BubbleSpinnerController(this, stage);
        }

        startingTime = System.currentTimeMillis();
        timerFont = new BitmapFont();
        timerFont.setColor(Color.BLACK);
        timerFont.getData().setScale(2);

        shapeRenderer = new ShapeRenderer();
    }

    public GameScreen(BubbleSpinner game) {
        this(game, false);
    }

    public GameScreen(BubbleSpinner game, boolean computer, TutorialHelpBox state) {
        this(game, computer);
        this.tutorialHelpBox = state;
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
            game.setScreen(new MenuScreen(game));
        }

        if (calculateRemainingTime().equals("00:00")) {
            dispose();
        }

        bubbleSpinnerController.update();

        if (bubbleSpinnerController instanceof BotController) {
            tutorialHelpBox.update();
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
        if (bubbleSpinnerController instanceof BotController) {
            game.setScreen(new GameScreen(game, true, tutorialHelpBox));
        } else {
            game.setScreen(new MenuScreen(game));
        }
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
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.setColor(Color.BLACK);

        // Calculate Stage Coordinates for mouse
        Vector2 mousePosition = new Vector2(Gdx.input.getX(), Gdx.input.getY());
        stage.screenToStageCoordinates(mousePosition);

        Vector2 bubblePosition = new Vector2(Gdx.graphics.getWidth() / 2,
                Gdx.graphics.getHeight() - Config.Game.BUBBLE_SIZE / 2);

        intersectWithWall(bubblePosition, mousePosition,
                mousePosition.x < Gdx.graphics.getWidth() / 2);

        shapeRenderer.end();
    }

    /**
     * Calculate the intersection between the direction of the ball
     * and the walls.
     * @param bubblePosition Vector2 containing the position of the top bubble.
     * @param mousePosition Vector2 containing the position of the mouse.
     * @param leftSide true for left Wall, false for right Wall.
     */
    private void intersectWithWall(Vector2 bubblePosition, Vector2 mousePosition,
                                   boolean leftSide) {
        Vector2 intersection = new Vector2(-1, -1);
        Vector2 wallOrigin = new Vector2(Config.Game.BUBBLE_SIZE / 2, 0);
        Vector2 wallDirection = new Vector2(Config.Game.BUBBLE_SIZE / 2, Gdx.graphics.getHeight());

        if (!leftSide) {
            wallOrigin.x = Gdx.graphics.getWidth() - wallOrigin.x;
            wallDirection.x = Gdx.graphics.getWidth() - wallDirection.x;
        }

        Intersector.intersectLines(bubblePosition, mousePosition,
                                    wallOrigin, wallDirection, intersection);
        if (intersection.equals(new Vector2(-1, -1))) {
            intersection.set(new Vector2(bubblePosition.x, 0));
        } else {
            calculateReflection(intersection, bubblePosition, leftSide);
        }
        shapeRenderer.line(bubblePosition, intersection);
    }

    /**
     * Calculate the reflection from the wall to the other side of the screen.
     * @param intersection Vector2 with the intersection point.
     * @param leftSide true for left Wall, false for right Wall.
     */
    private void calculateReflection(Vector2 intersection, Vector2 bubblePosition,
                                     boolean leftSide) {
        // Calculate slope of the line
        float m = -1 * (intersection.y - bubblePosition.y) / (intersection.x - bubblePosition.x);

        Vector2 reflection;
        if (leftSide) {
            reflection = new Vector2(Gdx.graphics.getWidth(),
                    m * Gdx.graphics.getWidth() + intersection.y);
        } else {
            reflection = new Vector2(0, intersection.y - m * intersection.x);
        }
        shapeRenderer.line(intersection, reflection);
    }
}
