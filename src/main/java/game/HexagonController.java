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
        //Max is the Difficulty of the Game
        this.bubbleFactory.addAllTextures(4);
        this.bubbles = new ArrayList<>();
        BubbleActor center = bubbleFactory.next().center();
        stage.addActor(center);

        int bubTotal = 18;

        for(int i=0;i<bubTotal;i++) {
            BubbleActor bub = bubbleFactory.next();
            bubbles.add(bub);
            stage.addActor(bub);
        }

        int lastneigh = 7;
        int temp;

        BubbleActor test = this.bubbles.get(1);

        for(int k=0; k<6; k++){
            int whichneigh=k;
            //BubbleActor curr = this.bubbles.get(k);
            this.bubbles.get(k).neighbours.add(center);
            temp = ((whichneigh-1)%6 + 6)%6; //((value % mod + mod) % mod)
            System.out.println(temp);
            this.bubbles.get(k).neighbours.add(this.bubbles.get(temp));
            this.bubbles.get(temp).neighbours.add(this.bubbles.get(k));
            temp = ((whichneigh+1)%6 + 6)%6;
            this.bubbles.get(k).neighbours.add(this.bubbles.get(temp));
            this.bubbles.get(temp).neighbours.add(this.bubbles.get(k));
            temp = (lastneigh%18 + 18)%18;
            this.bubbles.get(k).neighbours.add(this.bubbles.get(temp));
            this.bubbles.get(temp).neighbours.add(this.bubbles.get(k));
            temp = ((temp+1)%18 + 18)%18;
            this.bubbles.get(k).neighbours.add(this.bubbles.get(temp));
            this.bubbles.get(temp).neighbours.add(this.bubbles.get(k));
            temp = ((temp+1)%18 + 18)%18;
            this.bubbles.get(k).neighbours.add(this.bubbles.get(temp));
            this.bubbles.get(temp).neighbours.add(this.bubbles.get(k));
        }
        //0
        this.bubbles.get(0).setRelativeTo(center, Math.PI/2, true);

        //1
        this.bubbles.get(1).setRelativeTo(center, Math.PI/6, false);

        //2
        this.bubbles.get(2).setRelativeTo(center, Math.PI*11/6, false);

        //3
        this.bubbles.get(3).setRelativeTo(center, Math.PI*3/2 , true);

        //4
        this.bubbles.get(4).setRelativeTo(center, Math.PI*7/6, false);

        //5
        this.bubbles.get(5).setRelativeTo(center, Math.PI*5/6, false);
//
//        //6
//        this.bubbles.get(6).setRelativeTo(this.bubbles.get(0), 1.5708);
//
//        //7
//        this.bubbles.get(7).setRelativeTo(this.bubbles.get(0), 0.523599);
//
//        //8
//        this.bubbles.get(8).setRelativeTo(this.bubbles.get(1), 0.523599);
//
//        //9
//        this.bubbles.get(9).setRelativeTo(this.bubbles.get(1), 5.75959);
//
//        //10
//        this.bubbles.get(10).setRelativeTo(this.bubbles.get(2), 5.75959);
//
//        //11
//        this.bubbles.get(11).setRelativeTo(this.bubbles.get(2), 4.71239);
//
//        //12
//        this.bubbles.get(12).setRelativeTo(this.bubbles.get(3), 4.71239);
//
//        //13
//        this.bubbles.get(13).setRelativeTo(this.bubbles.get(3), 3.66519);
//
//        //14
//        this.bubbles.get(14).setRelativeTo(this.bubbles.get(4), 3.66519);
//
//        //15
//        this.bubbles.get(15).setRelativeTo(this.bubbles.get(4), 2.61799);
//
//        //16
//        this.bubbles.get(16).setRelativeTo(this.bubbles.get(5), 2.61799);
//
//        //17
//        this.bubbles.get(17).setRelativeTo(this.bubbles.get(5), 1.5708);
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
