package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class HexagonController {

    private transient Stage stage;
    private transient List<BubbleActor> bubbles;
    private transient BubbleGrid bubbleGrid;
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
        /*
         * TODO
         * Change this to create the correct structure.
         */
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

        int lastneigh = 7;
        int temp;

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
        int num2 = 0;
        for (BubbleActor a: bubbles) {
            int num = 0;
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
                        a.neighbours.add(b);
                        num++;
                    }
                }
            }
            num2++;
        }
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

    /**
     * Method calculates score based on how many
     * bubbles have been popped.
     * @param hitter is the bubble shot under the user's command
     * @return the score due to the hit
     */
    public int calculateScore(BubbleActor hitter) {
        int num = 0;
        int three = 3;
        hitter.getPosition();
        //for(BubbleActor hit: bubbles){
        //if(hit.collide(hitter)){
        //hit.getY();
        //num = num + bubblePop(hit, 0);
        //}
        //}

        if (num >= three) {
            num = formula(num);
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
        if (num == three) {
            return 5;
        } else {
            return (int)(1.5 * formula(num - 1));
        }
    }
}
