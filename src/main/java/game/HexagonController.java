package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class HexagonController {

    private transient Stage stage;
    private transient List<BubbleActor> bubbles;
    private transient BubbleFactory bubbleFactory;
    public transient boolean lostGame;

    public List<BubbleActor> getBubbles() {
        return bubbles;
    }

    public void setBubbles(List<BubbleActor> bubbles) {
        this.bubbles = bubbles;
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
        BubbleActor center = bubbleFactory.createBubble().center();
        stage.addActor(center);
        this.stage = stage;

        int bubTotal = 18;

        for (int i = 0; i < bubTotal; i++) {
            BubbleActor bub = bubbleFactory.createBubble();
            bubbles.add(bub);
            stage.addActor(bub);
        }

        File file = new File("assets/hexagon_easy.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
            sc.useLocale(Locale.US);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (BubbleActor bubble: bubbles) {
            if (!(sc.hasNextFloat())) {
                break;
            }
            float x = sc.nextFloat();
            float y = sc.nextFloat();
            bubble.center().shiftX(true, x);
            bubble.shiftY(true, y);
            //sc.next();
        }
        for (BubbleActor a: bubbles) {
            for (BubbleActor b: bubbles) {
                if (!a.collide(b)) {
                    float xa = a.getX();
                    float ya = a.getY();
                    float xb = b.getX();
                    float yb = b.getY();
                    float lenx = (float) Math.pow((xa - xb), 2);
                    float leny = (float) Math.pow((ya - yb), 2);
                    float len = (float) Math.sqrt(lenx + leny);
                    if (len <= 120.0 && len >= 65.0) {
                        a.getNeighbours().add(b);
                    }
                }
            }
        }
    }

    /**
    * Recursive method to find how many neighbouring
    * bubbles will be popped after hit.
    * @param hit bubble whose neighbours we check for
    *            matching color id.
    * @return number of bubbles
    */
    public int bubblePop(BubbleActor hit, int counter, ArrayList<BubbleActor> visited) {
        int three = 3;
        ArrayList<BubbleActor> candidates = hit.getNeighbours();
        for (int i = 0; i < candidates.size(); i++) {
            BubbleActor candidate = candidates.get(i);
            if (candidate.getColorId() == hit.getColorId() && !(visited.contains(candidate))) {
                counter++;
                visited.add(candidate);
                counter += bubblePop(candidate, counter, visited);
                System.out.println(counter);
            }
        }
        if (counter >= three) {
            for (int j = 0; j < visited.size();  j++) {
                visited.get(j).remove();
            }
            hit.remove();
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
                BubbleActor hit = bubbles.get(i);
                hit.getNeighbours().add(bubble);
                bubble.getNeighbours().add(hit);
                bubble.stop();
                bubbles.add(bubble);
                this.calculateScore(bubble);
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
    public int calculateScore(BubbleActor hitter) {
        int num = 0;
        for (int i = 0; i < bubbles.size(); i++) {
            BubbleActor hit = bubbles.get(i);
            if (hit.collide(hitter)) {
                hit.getY();
                num += bubblePop(hitter, 0, new ArrayList<BubbleActor>());
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
