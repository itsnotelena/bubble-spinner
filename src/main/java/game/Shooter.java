package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.Stack;

public class Shooter {

    private transient Stack<BubbleActor> available;
    private transient BubbleFactory bubbleFactory;
    private transient Stage stage;

    /**
     * Constructor for the Shooter which is at the
     * top of the screen.
     * @param stage Stage where all objects reside.
     */
    public Shooter(Stage stage, int difficulty) {
        this.stage = stage;
        this.available = new Stack<>();
        this.bubbleFactory = new BubbleFactory(stage, difficulty);
    }

    public Shooter(Stage stage) {
        this(stage, 0);
    }

    /**
     * This function creates the bubbles at the top
     * of the screen and adds them to the Stage.
     */
    public void initialize(int[] mapBubbles) {
        this.bubbleFactory.addAllTextures();
        for (int i = 4; i >= 0; --i) {
            BubbleActor bubble = bubbleFactory.createBubbleGivenMap(mapBubbles);
            if (bubble != null) {
                available.push(bubble.shiftX(true, i));
                stage.addActor(current());
            }
        }
    }

    /**
     * After the bubble has been shot shift the other
     * ones to the left and add a new one at the end.
     */
    @SuppressWarnings(value = "PMD")
    // The PMD warning is an anomaly on the variable removed which is only used
    // by the function to keep track of the number of removed bubbles.
    public void shiftBubbles(int[] mapBubbles) {
        if (available.size() == 0) {
            initialize(mapBubbles);
            return;
        }

        int removed = 0;
        Stack<BubbleActor> stack = new Stack<>();
        while (!available.isEmpty()) {
            BubbleActor current = poll();
            if (mapBubbles[current.getColorId()] > 0) {
                stack.push(current.shiftX(false, 1 + removed));
            } else {
                removed++;
                current.remove();
            }
        }
        while (!stack.isEmpty()) {
            available.push(stack.pop());
        }
        if (available.size() == 0) {
            initialize(mapBubbles);
        }
    }

    public BubbleActor poll() {
        return available.empty() ? null : available.pop();
    }

    public BubbleActor current() {
        return available.empty() ? null : available.peek();
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
