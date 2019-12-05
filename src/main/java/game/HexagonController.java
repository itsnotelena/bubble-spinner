package game;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.List;

public class HexagonController {

    private transient List<BubbleActor> bubbles;
    private transient BubbleFactory bubbleFactory;
    public transient boolean lostGame;

    /**
     * Constructor for the HexagonController.
     * @param stage Stage where objects reside.
     */
    public HexagonController(Stage stage) {
        /*
         * TODO
         * Change this to create the correct structure.
         */
        this.bubbleFactory = new BubbleFactory(stage);
        this.bubbleFactory.addAllTextures();
        this.bubbles = new ArrayList<>();
        BubbleActor b1 = bubbleFactory.next().center();
        b1.shiftX(true, 0);
        BubbleActor b2 = bubbleFactory.next().center();
        b2.shiftX(true);
        BubbleActor b3 = bubbleFactory.next().center();
        b3.shiftX(false);
        bubbles.add(b1);
        bubbles.add(b2);
        bubbles.add(b3);
        stage.addActor(b1);
        stage.addActor(b2);
        stage.addActor(b3);
    }

    /**
     * Check whether a moving bubble collides with the hexagon.
     * @param bubble BubbleActor representing the moving bubble.
     * @return True if a collision is detected, False otherwise.
     */
    public boolean checkCollisions(BubbleActor bubble) {
        for (int i = 0; i < bubbles.size(); ++i) {
            if (bubble.collide(bubbles.get(i))) {
                bubble.stop();
                bubbles.add(bubble);
                return true;
            }
            if (bubbles.get(i).outSideScreen()) {
                lostGame = true;
            }
        }
        return false;
    }
}
