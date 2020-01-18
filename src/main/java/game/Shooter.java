package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class Shooter {

    private transient Deque<BubbleActor> available;
    private transient BubbleFactory bubbleFactory;
    private static final int MIN_BUBBLES = 7;

    private transient Stage stage;

    /**
     * Constructor for the Shooter which is at the
     * top of the screen.
     * @param stage Stage where all objects reside.
     */
    public Shooter(Stage stage, int difficulty) {
        this.stage = stage;
        this.available = new LinkedList<>();
        this.bubbleFactory = new BubbleFactory(stage, difficulty);
    }

    /**
     * This function creates the bubbles at the top
     * of the screen and adds them to the Stage.
     */
    public void initialize() {
        //max is the Difficulty of the level
        this.bubbleFactory.addAllTextures();
        refill();
        Stack<BubbleActor> stack = new Stack<>();
        assert available.size() > 5;
        for (int i = 0; i < 5; ++i) {
            current().shiftX(true, i);
            stage.addActor(current());
            stack.push(available.poll());
        }
        while (!stack.isEmpty()) {
            available.addFirst(stack.pop());
        }
    }

    /**
     * If the queue is running short in bubbles
     * refill them.
     */
    public void refill() {
        if (available.size() > MIN_BUBBLES) {
            return;
        }

        for (int i = 0; i < 2 * MIN_BUBBLES; ++i) {
            available.add(bubbleFactory.createBubble());
        }
    }

    /**
     * After the bubble has been shot shift the other
     * ones to the left and add a new one at the end.
     */
    public void shiftBubbles() {
        refill();
        Stack<BubbleActor> stack = new Stack<>();
        for (int i = 0; i < 4; ++i) {
            current().shiftX(false);
            stack.push(poll());
        }
        current().shiftX(true, 4);
        stage.addActor(current());
        while (!stack.isEmpty()) {
            available.addFirst(stack.pop());
        }
    }

    public BubbleActor poll() {
        return available.poll();
    }

    public BubbleActor current() {
        return available.peek();
    }

    /**
     * Shoot the first bubble in the direction pointed
     * by the mouse position.
     */
    public void shootBubble() {
        shootBubbleScreenCoords(Gdx.input.getX(), Gdx.input.getY());
    }

    /**
     * Shoot the first bubble given screen coordinates.
     * @param x Axis of the new position.
     * @param y Ordinate of the new position.
     */
    public void shootBubbleScreenCoords(int x, int y) {
        Vector2 newPos = new Vector2(
                x - current().getWidth() / 2,
                Gdx.graphics.getHeight() - y - current().getHeight() / 2);
        Vector2 bubblePos = new Vector2(0, 0);
        current().localToStageCoordinates(bubblePos);
        stage.getViewport().project(bubblePos);

        Vector2 dir = newPos.sub(bubblePos).nor();
        current().setMovingDirection(new Vector2(8 * dir.x, 8 * dir.y));
    }

    public void setBubbleFactory(BubbleFactory bubbleFactory) {
        this.bubbleFactory = bubbleFactory;
    }
}
