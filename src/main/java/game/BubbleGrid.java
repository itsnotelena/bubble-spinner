package game;

import java.util.ArrayList;
import java.util.List;

public class BubbleGrid {
    private final int RADIUS = 100;
    private transient BubbleActor[][] bubbles = new BubbleActor[RADIUS*2][RADIUS*2];

    public BubbleGrid(){}

    public void setBubble(int x, int y, BubbleActor bubbleActor) {
        int dx = x + RADIUS;
        int dy = y + RADIUS;
        if (dx >= 0 && dx < RADIUS && dy >= 0 && dy < RADIUS) {
            this.bubbles[dx][dy] = bubbleActor;
        }
    }

    public BubbleActor getBubble(int x, int y) {
        int dx = x + RADIUS;
        int dy = y + RADIUS;
        if (dx >= 0 && dx < RADIUS && dy >= 0 && dy < RADIUS) {
            return this.bubbles[dx][dy];
        }
        return null;
    }

    public List<BubbleActor> getNeighbours(int x, int y) {
        ArrayList<BubbleActor> list = new ArrayList<>();

        // Start by checking the position on the bottom left of the current bubble and move clockwise around the bubble
        int[] dx = {-1, -1, 0, 1, 1,  0};
        int[] dy = { 0,  1, 1, 1, 0, -1};
        for (int i = 0; i < 6; i++) {
            BubbleActor bub = getBubble(x-1, y);
            if(bub != null){ list.add(bub); }
        }

    }
}
