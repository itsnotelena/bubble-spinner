package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import config.Config;

import java.util.ArrayList;
import java.util.Map;


public class BubbleActor extends Image implements Observer {

    private static final int SIZE = Config.Game.BUBBLE_SIZE;
    private transient Circle circle;
    private transient Vector2 movingDirection = new Vector2(0, 0);
    private transient Stage stage;
    public transient int[] gridPos = new int[2];

    private int colorId;

    /**
     * Constructor for the Bubble Actor in the stage.
     * @param texture The Texture object to draw.
     * @param stage Stage the bubble is in.
     */
    public BubbleActor(Texture texture, Stage stage) {
        this(texture,
                stage,
                Gdx.graphics.getWidth() / 2 - SIZE / 2,
                Gdx.graphics.getHeight() - SIZE
        );
    }

    /**
     * Constructor for the Bubble Actor in the stage.
     * @param texture The Texture object to draw.
     * @param stage Stage the bubble is in.
     * @param x Position X of the bubble.
     * @param y Position Y of the bubble.
     */
    public BubbleActor(Texture texture, Stage stage, int x, int y) {
        super(texture);
        this.stage = stage;
        setSize(SIZE, SIZE);
        setOrigin(SIZE / 2, SIZE / 2);
        circle = new Circle(
                getX() + SIZE / 2,
                getY() + SIZE / 2,
                SIZE / 2 - 7
        );
        setPosition(x, y);
    }

    /**
     * Update the bubble position if it's moving or it
     * needs to bounce off the screen.
     */
    @Override
    public void update() {
        if (!movingDirection.isZero()) {
            moveBy(movingDirection.x, movingDirection.y);
        }
        Vector2 pos = vec(0, 0);
        localToStageCoordinates(pos);
        stage.getViewport().project(pos);
        if (pos.x + SIZE > Gdx.graphics.getWidth() || pos.x < 0) {
            bounce();
        }
    }

    public BubbleActor shiftX(boolean positive, float scale) {
        return shiftAxis(positive, scale, true);
    }

    public BubbleActor shiftX(boolean positive) {
        return shiftX(positive, 1);
    }

    public BubbleActor shiftY(boolean positive, float scale) {
        return shiftAxis(positive, scale, false);
    }

    public BubbleActor shiftY(boolean positive) {
        return shiftY(positive, 1);
    }

    public int getColorId() {
        return colorId;
    }

    public void setColorId(int colorId) {
        this.colorId = colorId;
    }

    /**
     * Shift the bubble along one of the two axis.
     * @param positive True to shift right, False to shift left.
     * @param scale The number of times to shift.
     * @param axis True for X axis, False for Y axis.
     */
    public BubbleActor shiftAxis(boolean positive, float scale, boolean axis) {
        if (axis) {
            moveBy((positive ? 1 : -1) * scale * SIZE, 0);
        } else {
            moveBy(0,(positive ? 1 : - 1) * scale * SIZE);
        }
        return this;
    }

    public boolean collide(BubbleActor other) {
        return this.circle.overlaps(other.circle);
    }

    /**
     * Center the bubble at the center of the screen.
     * @return This object.
     */
    public BubbleActor center() {
        setPosition(Gdx.graphics.getWidth() / 2 - SIZE / 2,
                    Gdx.graphics.getHeight() / 2 - SIZE / 2);
        return this;
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        this.circle.setPosition(x + SIZE / 2, y + SIZE / 2);
    }

    @Override
    public void moveBy(float x, float y) {
        super.moveBy(x, y);
        this.circle.x += x;
        this.circle.y += y;
    }

    public void setMovingDirection(Vector2 movingDirection) {
        this.movingDirection = movingDirection;
    }

    public Vector2 getMovingDirection() {
        return this.movingDirection;
    }

    public void stop() {
        setMovingDirection(vec(0, 0));
    }

    public void bounce() {
        movingDirection.x *= -1;
    }

    public boolean outSideScreen() {
        return getY() + 2 * SIZE > Gdx.graphics.getHeight() - SIZE;
    }

    public boolean belowScreen() {
        return getY() < -SIZE;
    }

    public Vector2 vec(float x, float y) {
        return new Vector2(x, y);
    }

    public Vector2 getPosition() {
        return vec(circle.x, circle.y);
    }

    public boolean isMoving() {
        return !movingDirection.isZero();
    }
}
