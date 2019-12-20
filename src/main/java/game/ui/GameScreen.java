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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import config.Config;
import game.BotController;
import game.BubbleSpinner;
import game.BubbleSpinnerController;
import game.Timer;

public class GameScreen implements Screen {

    protected transient BubbleSpinner game;
    private transient Stage stage;
    private transient OrthographicCamera camera;
    private transient BubbleSpinnerController bubbleSpinnerController;
    private transient BitmapFont timerFont;
    private transient PauseMenu pauseMenu;
    private transient boolean paused;
    private transient ShapeRenderer shapeRenderer;
    private transient Timer timer;

    /**
     * This is Screen where the game is played.
     * @param game BubbleSpinner instance.
     */
    public GameScreen(BubbleSpinner game, boolean computer) {
        this.game = game;

        this.paused = false;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        stage = new Stage(new ScreenViewport());

        if (computer) {
            bubbleSpinnerController = new BotController(this, stage);
        } else {
            bubbleSpinnerController = new BubbleSpinnerController(this, stage);
        }


        Skin skin = new Skin(Gdx.files.internal("assets/uiskin.json"));

        timer = new Timer();
        timerFont = new BitmapFont();
        timerFont.setColor(Color.BLACK);
        timerFont.getData().setScale(2);

        shapeRenderer = new ShapeRenderer();

        this.pauseMenu = new PauseMenu(this, skin);
    }

    public GameScreen(BubbleSpinner game) {
        this(game, false);
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
                timer.calculateRemainingTime(),
                Gdx.graphics.getHeight() / 8,
                7 * Gdx.graphics.getHeight() / 8
        );

        game.batch.end();

        stage.act();
        stage.draw();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            this.togglePause();
        }

        if (timer.isOver()) {
            dispose();
        }

        if(!paused){
            bubbleSpinnerController.update();
            drawArrow();
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
        timer.pause();
    }

    @Override
    public void resume() {
        timer.resume();
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
        if (bubbleSpinnerController instanceof BotController) {
            game.setScreen(new GameScreen(game, true));
        } else {
            game.setScreen(new MenuScreen(game));
        }
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

    public void togglePause(){
        this.paused = !this.paused;

        if (this.paused){
            timer.pause();
        } else {
            timer.resume();
        }
    }
}