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

        File file = new File("assets\\hexagon_easy.txt");
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
        int num2 = 0;
        for(BubbleActor a: bubbles){
            int num = 0;
            for(BubbleActor b: bubbles){

                if(!a.collide(b)) {
                    float xa = a.getX();
                    float ya = a.getY();
                    float xb = b.getX();
                    float yb = b.getY();
                    float lenx = (float) Math.pow((xa - xb), 2);
                    float leny = (float) Math.pow((ya - yb), 2);
                    float len = (float) Math.sqrt(lenx + leny);
                    if(len<=120.0 && len>=65.0){
                        a.neighbours.add(b);
                        System.out.println("Added neighbour num "+ num);
                        num++;

                    }

                }
            }
            System.out.println("Done with bubble "+num2);
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
     * Recursive method to find how many neighbouring
     * bubbles will be popped after hit.
     * @return number of bubbles
     */
    public int bubblePop(){
        //TO DO
        return 0;
    }

    /**
     * Method calculates score based on how many
     * bubbles have been popped.
     * @return the score due to the
     */
    public int calculateScore(){
        //TO DO
        return 0;
    }
}
