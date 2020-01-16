package game;

import com.badlogic.gdx.math.Vector2;
import config.Config;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BubbleGrid {
    private static final int RADIUS = 100;
    private transient BubbleActor[][] bubbles = new BubbleActor[RADIUS*2][RADIUS*2];
    private HexagonController parent;

    public BubbleGrid(HexagonController parent){
        this.parent = parent;
    }

    public void setBubble(int x, int y, BubbleActor bubbleActor) {
        int dx = x + RADIUS;
        int dy = y + RADIUS;
        if (dx >= 0 && dx < RADIUS && dy >= 0 && dy < RADIUS) {
            this.bubbles[dx][dy] = bubbleActor;
            bubbleActor.gridPos = new int[]{dx, dy};
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

    public Vector2 gridToWorld(int x, int y) {
        // Center of the structure should be at 0,0
        int offset = Math.abs(y) % 2;
        float colDistance = (float) (Config.Game.BUBBLE_SIZE * Math.sqrt(3) / 2.f);
        float dx = colDistance * x;
        float dy = (float) (y * Config.Game.BUBBLE_SIZE - offset * 0.5 * Config.Game.BUBBLE_SIZE);
        return new Vector2(dx, dy);
    }

    public List<BubbleActor> getNeighbours(int x, int y) {
        ArrayList<BubbleActor> list = new ArrayList<>();

        // Since our grid is hexagonal we have to do a bit of magic
        int offset = y % 2;

        // Start by checking the position on the bottom left of the current bubble and move clockwise around the bubble
        int[] dx = {-1, -1, 0, 1, 1,  0};
        int[] dy = { -offset,  1 - offset, 1, 1 - offset, -offset, -1}; //  fine as long as it works

        // Add all the neighbours that are present
        for (int i = 0; i < dx.length && i < dy.length; i++) {
            BubbleActor bub = getBubble(x + dx[i], y + dy[i]);
            if(bub != null){ list.add(bub); }
        }

        return list;
    }

    public List<BubbleActor> getConnectedBubbles() {
        ArrayList<BubbleActor> visited = new ArrayList<>();
        LinkedList<BubbleActor> next = new LinkedList<>();
        next.add(getBubble(0,0));

        while( !next.isEmpty() ) {
               BubbleActor nextActor = next.pop();
               List<BubbleActor> neighbours = getNeighbours(nextActor.gridPos[0], nextActor.gridPos[1]);
               visited.add(nextActor);
               for (int i = 0; i < neighbours.size(); i++) {
                   BubbleActor actor = neighbours.get(i);
                   if (!visited.contains(actor)) {
                       next.add(actor);
                   }
               }
        }
        return visited;
    }

}
