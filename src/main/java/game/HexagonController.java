package game;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

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
        //Max is the Difficulty of the Game
        this.bubbleFactory.addAllTextures(4);
        this.bubbles = new ArrayList<>();
        BubbleActor center = bubbleFactory.next().center();
        stage.addActor(center);

        int bubTotal = 18;

        for (int i = 0; i < bubTotal; i++) {
            BubbleActor bub = bubbleFactory.next();
            bubbles.add(bub);
            stage.addActor(bub);
        }

        int lastneigh = 7;
        int temp;

        for (int k = 0; k < 6; k++) {
            int whichneigh = k;
            this.bubbles.get(k).neighbours.add(center);
            temp = ((whichneigh - 1) % 6 + 6) % 6;
            System.out.println(temp);
            this.bubbles.get(k).neighbours.add(this.bubbles.get(temp));
            this.bubbles.get(temp).neighbours.add(this.bubbles.get(k));
            temp = ((whichneigh + 1) % 6 + 6) % 6;
            this.bubbles.get(k).neighbours.add(this.bubbles.get(temp));
            this.bubbles.get(temp).neighbours.add(this.bubbles.get(k));
            temp = (lastneigh % 18 + 18) % 18;
            this.bubbles.get(k).neighbours.add(this.bubbles.get(temp));
            this.bubbles.get(temp).neighbours.add(this.bubbles.get(k));
            temp = ((temp + 1) % 18 + 18) % 18;
            this.bubbles.get(k).neighbours.add(this.bubbles.get(temp));
            this.bubbles.get(temp).neighbours.add(this.bubbles.get(k));
            temp = ((temp + 1) % 18 + 18) % 18;
            this.bubbles.get(k).neighbours.add(this.bubbles.get(temp));
            this.bubbles.get(temp).neighbours.add(this.bubbles.get(k));
        }

        File file = new File("C:\\Users\\Ana\\Documents\\bubble2"
                + "\\template\\src\\main\\java\\config\\hexagon_easy.txt");
        Scanner sc = null;
        try {
            sc = new Scanner(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        sc.useDelimiter(", |\n|\r");
        sc.useLocale(Locale.US);
        for (BubbleActor bubble: bubbles) {
            if (!(sc.hasNext())) {
                break;
            }
            double x = sc.nextDouble();
            double y = sc.nextDouble();
            bubble.center().shiftX(true, (float)x);
            bubble.shiftY(true, (float)y);
            sc.next();
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
}
