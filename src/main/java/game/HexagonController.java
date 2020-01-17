package game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;
import java.util.List;

public class HexagonController {

    private transient Stage stage;
    private transient List<BubbleActor> bubbles;
    private transient BubbleGrid bubbleGrid;
    private transient BubbleFactory bubbleFactory;
    public transient boolean lostGame;

    public List<BubbleActor> getBubbles() {
        return bubbles;
    }

    /**
     * Constructor for the HexagonController.
     * @param stage Stage where objects reside.
     */
    public HexagonController(Stage stage) {

        this.bubbleFactory = new BubbleFactory(stage);
        //Max is the Difficulty of the Game
        this.bubbleFactory.addAllTextures(4);
        this.bubbles = new ArrayList<>();
        BubbleActor center = bubbleFactory.createCenterBubble().center();
        stage.addActor(center);
        bubbles.add(center);
        this.bubbleGrid = new BubbleGrid(center.getPosition());
        bubbleGrid.setBubble(0,0, center);

        this.stage = stage;
        BubbleActor bub = bubbleFactory.createBubble();
        bubbles.add(bub);
        bubbleGrid.setBubble(-1,0,bub);
        stage.addActor(bub);

        BubbleActor bub2 = bubbleFactory.createBubble();
        bubbles.add(bub2);
        bubbleGrid.setBubble(-1,1,bub2);
        stage.addActor(bub2);

        bub2 = bubbleFactory.createBubble();
        bubbles.add(bub2);
        bubbleGrid.setBubble(0,1,bub2);
        stage.addActor(bub2);
        drawGrid();
    }

    private void drawGrid(){
        for(BubbleActor bub : bubbles) {
            Vector2 vec = bubbleGrid.gridToWorld(bub.gridPos[0], bub.gridPos[1]);
            bub.center();
            bub.moveBy(vec.x, vec.y);
        }
    }

    private void popSingleBubble(BubbleActor actor) {
        this.bubbles.remove(actor);
        this.bubbleGrid.setBubble(actor.gridPos[0], actor.gridPos[1], null);
        stage.getActors().removeValue(actor, true);
        actor.remove();
    }

    /**
    * Recursive method to find how many neighbouring
    * bubbles will be popped after hit.
    * @param hit bubble whose neighbours we check for
    *            matching color id.
    * @return number of bubbles
    */
    public int getPoppable(BubbleActor hit, int counter, List<BubbleActor> visited) {
        visited.add(hit); // Add the bubbleActor to the list of visited bubbles
        List<BubbleActor> candidates = this.bubbleGrid.getNeighbours(hit.gridPos[0], hit.gridPos[1]);

        // Loop over the neighbours of the current bubble
        for (int i = 0; i < candidates.size(); i++) {
            BubbleActor candidate = candidates.get(i);
            if (candidate.getColorId() == hit.getColorId() && !(visited.contains(candidate))) {
                counter++;
                counter += getPoppable(candidate, counter, visited);
            }
        }
        return counter;
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
                int[] gridPos = bubbleGrid.worldToGrid(bubble.getPosition().sub(this.bubbleGrid.origin));
                this.bubbleGrid.setBubble(gridPos[0], gridPos[1], bubble);
                this.popBubbles(bubble);
                drawGrid();
                return true;
            }
            if (bubbles.get(i).outSideScreen()) {
                lostGame = true;
            }
        }
        return false;
    }

    /**
     * Method calculates score based on how many
     * bubbles have been popped.
     * @param hitter is the bubble shot under the user's command
     * @return the score due to the hit
     */
    public int popBubbles(BubbleActor hitter) {
        List<BubbleActor> poppable = new ArrayList<>();
        int num = getPoppable(hitter, 0, poppable);
        if(poppable.size() >= 3){
            for ( BubbleActor actor : poppable)
            {
                this.popSingleBubble(actor);
            }
        }
        int result = formula(num);
        return result;
    }

    /**
     * Helper method to help with
     * score calculation.
     * @param num is number of bubbles popped
     */
    public int formula(int num) {
        int three = 3;
        if (num < three) {
            return 0;
        }
        if (num == three) {
            return 5;
        } else {
            return (int)(1.5 * formula(num - 1));
        }
    }
}
