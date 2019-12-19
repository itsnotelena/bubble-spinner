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
    public HexagonController(Stage stage, int difficulty) {
        /*
         * TODO
         * Change this to create the correct structure.
         */
        this.bubbleFactory = new BubbleFactory(stage);
        //Max is the Difficulty of the Game
        this.bubbleFactory.addAllTextures(4);
        this.bubbles = new ArrayList<>();
        this.bubbles.add(bubbleFactory.next().center());
        stage.addActor(this.bubbles.get(0));
        BubbleActor center = this.bubbles.get(0);

        int bubTotal = 18;

        if(difficulty>0){
            bubTotal = 36;
        }

        if(difficulty>1){
            bubTotal = 50;
        }

        for(int i=0;i<bubTotal;i++) {
            BubbleActor bub = bubbleFactory.next();
            bubbles.set(i, bub);
            stage.addActor(bub);
        }

        int lastneigh = 7;
        int temp;

        for(int k=0; k<6; k++){
            int whichneigh=k;
            BubbleActor curr = this.bubbles.get(k);
            curr.neighbours.add(center);
            temp = (whichneigh-1)%6;
            curr.neighbours.add(this.bubbles.get(temp));
            this.bubbles.get(temp).neighbours.add(curr);
            temp = (whichneigh+1)%6;
            curr.neighbours.add(this.bubbles.get(temp));
            this.bubbles.get(temp).neighbours.add(curr);
            temp = lastneigh%18;
            curr.neighbours.add(this.bubbles.get(temp));
            this.bubbles.get(temp).neighbours.add(curr);
            temp = (temp+1)%18;
            curr.neighbours.add(this.bubbles.get(temp));
            this.bubbles.get(temp).neighbours.add(curr);
            temp = (temp+1)%18;
            curr.neighbours.add(this.bubbles.get(temp));
            this.bubbles.get(temp).neighbours.add(curr);
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
