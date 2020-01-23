package game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import config.Config;

public class AimingArrow {

    private transient ShapeRenderer shapeRenderer;
    private transient Stage stage;
    private transient Camera camera;

    /**
     * Constructor.
     * @param stage Stage instance.
     * @param camera OrthographicCamera instance.
     */
    public AimingArrow(Stage stage, Camera camera) {
        this.shapeRenderer = new ShapeRenderer();
        this.stage = stage;
        this.camera = camera;
    }

    /**
     * Draw an arrow based on the current
     * position of the mouse.
     */
    public void draw() {
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
