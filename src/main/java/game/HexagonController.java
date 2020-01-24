package game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import config.Config;
import game.ui.GameScreen;

import java.util.*;

public class HexagonController {

    private static int THRESHOLD_BUBBLES = 1;
    private transient int result = 0;
    private transient Stage stage;
    private transient List<BubbleActor> bubbles;
    private transient BubbleGrid bubbleGrid;
    private transient BubbleFactory bubbleFactory;
    public transient boolean lostGame;
    private transient int[] mapBubbles;
    private transient int missedBubbles;
    private transient int difficulty;
    private transient HexagonBuilder builder;
    public List<BubbleActor> getBubbles() {
        return bubbles;
    }

    /**
     * Constructor for the HexagonController.
     * @param stage Stage where objects reside.
     */
    public HexagonController(Stage stage, int difficulty) {
        this.mapBubbles = new int[Config.Bubbles.textures.length];
        this.difficulty = difficulty;
        this.bubbleFactory = new BubbleFactory(stage, difficulty);
        this.bubbles = new ArrayList<>();
        this.stage = stage;

    }

    public void initialize(){
        this.bubbleFactory.addAllTextures();
        BubbleActor center = bubbleFactory.createCenterBubble().center();
        stage.addActor(center);
        bubbles.add(center);
        this.bubbleGrid = new BubbleGrid(center.getPosition());
        bubbleGrid.setBubble(0,0, center);

    }

    public void positionBubble(int x, int y) {
        if(bubbleGrid.getBubble(x, y) == null) {
            BubbleActor bub2 = bubbleFactory.createBubble();
            bubbles.add(bub2);
            bubbleGrid.setBubble(x, y, bub2);
            stage.addActor(bub2);
            mapBubbles[bub2.getColorId()]++;
        }
    }

    public void drawGrid() {
        this.bubbleGrid.update_rotation();
        for (int i = 1; i < bubbles.size(); i++) {
            BubbleActor bub = bubbles.get(i);
            Vector2 vec = bubbleGrid.gridToWorld(bub.gridPos[0], bub.gridPos[1]);
            bub.center();
            bub.moveBy(vec.x, vec.y);
        }
    }

    public void setBuilder(HexagonBuilder builder) {
        this.builder = builder;
    }

    public HexagonBuilder getBuilder() {
        return this.builder;
    }


    public void setBubbleFactory(BubbleFactory bubbleFactory) {
        this.bubbleFactory = bubbleFactory;
    }

    private void popSingleBubble(BubbleActor actor) {
        this.bubbles.remove(actor);
        this.bubbleGrid.setBubble(actor.gridPos[0], actor.gridPos[1], null);
        actor.remove();
        mapBubbles[actor.getColorId()]--;
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
        List<BubbleActor> candidates = this.bubbleGrid
                .getNeighbours(hit.gridPos[0], hit.gridPos[1]);

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

    public int getResult() {
        return result;
    }

    /**
     * Check whether a moving bubble collides with the hexagon.
     * @param bubble BubbleActor representing the moving bubble.
     * @return True if a collision is detected, False otherwise.
     */
    public boolean checkCollisions(BubbleActor bubble) {
        for (int i = 0; i < bubbles.size(); ++i) {
            if (bubble.collide(bubbles.get(i))) {
                this.bubbleGrid.apply_torque(bubble.getMovingDirection(), bubble.getPosition());
                bubble.stop();
                bubbles.add(bubble);
                mapBubbles[bubble.getColorId()]++;
                int[] gridPos = bubbleGrid.worldToGrid(bubble
                        .getPosition().sub(this.bubbleGrid.origin));
                this.bubbleGrid.setBubble(gridPos[0], gridPos[1], bubble);
                result += this.popBubbles(bubble);
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
        int three = 3;
        if (poppable.size() >= three) {
            for (int i = 0; i < poppable.size(); i++) {
                BubbleActor actor = poppable.get(i);
                this.popSingleBubble(actor);
            }
        }
        num += popFloatingBubbles();
        int result = formula(num);
        return result;
    }

    @SuppressWarnings(value = "PMD")
    // The warning is a DU anomaly on connectedBubbles, PMD sees it as undefined which is a false
    // positive. A fix would make the complexity from linear to quadratic.
    public int popFloatingBubbles() {
        int num = 0;
        List<BubbleActor> oldBubbles = new ArrayList<>(this.bubbles);
        List<BubbleActor> connectedBubbles = this.bubbleGrid.getConnectedBubbles(0,0);
        for (int i = 0; i < oldBubbles.size(); i++) {
            BubbleActor actor = oldBubbles.get(i);
            if (!connectedBubbles.contains(actor)) {
                popSingleBubble(actor);
                num += 1;
            }
        }
        return num;
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
            return (int)(1.1 * formula(num - 1));
        }
    }

    /**
     * Called when a bubble is missed.
     */
    public void bubbleMissed() {
        final int MAX_MISSED_BUBBLES = 3;
        missedBubbles++;
        result--;
        if (missedBubbles >= MAX_MISSED_BUBBLES) {
            missedBubbles = 0;
            // Add new bubbles to the grid
            Set<Pair<Integer, Integer>> set = this.bubbleGrid.getPossiblePositions();

            for(int i = 0; i < set.size() || i < 5; i++ ) {
                int size = set.size();
                int index = new Random().nextInt(size);
                Object[] pairs = set.toArray();
                Pair<Integer, Integer> randomValue = (Pair<Integer, Integer>) pairs[index];
                positionBubble(randomValue.getLeft(), randomValue.getRight());
                set.remove(randomValue.getLeft());
            }
        }
    }

    /**
     * Get the hash map of bubbles.
     * @return a map for the bubbles contained.
     */
    public int[] getMapBubbles() {
        return mapBubbles;
    }

    /**
     * Check whether you won or lost the game.
     * @param gameScreen GameScreen instance.
     */
    public void checkGameStatus(GameScreen gameScreen) {
        if (lostGame) {
            gameScreen.dispose();
            return;
        }

        int counter = 0;
        for (int i = 0; i < mapBubbles.length; ++i) {
            counter += mapBubbles[i] > 0 ? 1 : 0;
        }
        if (counter <= THRESHOLD_BUBBLES) {
            result += formula(bubbles.size());
            gameScreen.nextLevel();
        }
    }
}
