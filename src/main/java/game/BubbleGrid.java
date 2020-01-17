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
    public Vector2 origin;

    public BubbleGrid(Vector2 origin){
        this.origin = origin;
    }

    public void setBubble(int x, int y, BubbleActor bubbleActor) {
        int dx = x + RADIUS;
        int dy = y + RADIUS;
        if ( dx >= 0 && dx < RADIUS*2 && dy >= 0 && dy < RADIUS*2) {
            this.bubbles[dx][dy] = bubbleActor;
            if(bubbleActor != null){ bubbleActor.gridPos = new int[]{x, y}; }
        }
    }

    public BubbleActor getBubble(int x, int y) {
        int dx = x + RADIUS;
        int dy = y + RADIUS;
        if (dx >= 0 && dx < RADIUS*2 && dy >= 0 && dy < RADIUS*2) {
            return this.bubbles[dx][dy];
        }
        return null;
    }

    public Vector2 gridToWorld(int x, int y) {
        // Center of the structure should be at 0,0
        int offset = Math.abs(x) % 2;
        float colDistance = (float) (Config.Game.BUBBLE_SIZE * Math.sqrt(3) / 2.f);
        float dx = colDistance * x;
        float dy = (float) (y * Config.Game.BUBBLE_SIZE - offset * 0.5 * Config.Game.BUBBLE_SIZE);
        return new Vector2(dx, dy);
    }

    // Takes an offset from the center and returns the grid coordinates
    public int[] worldToGrid(Vector2 vec) {
        float colDistance = (float) (Config.Game.BUBBLE_SIZE * Math.sqrt(3) / 2.f);
        int x = Math.round((vec.x / colDistance));
        int offset = Math.abs(x) % 2;
        int y = Math.round((float) (vec.y + offset * 0.5 * Config.Game.BUBBLE_SIZE) / (float) Config.Game.BUBBLE_SIZE);
        return new int[]{x,y};
    }

    public List<BubbleActor> getNeighbours(int x, int y) {
        ArrayList<BubbleActor> list = new ArrayList<>();

        // Since our grid is hexagonal we have to do a bit of magic
        int offset = Math.abs(x) % 2;

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

    public List<BubbleActor> getConnectedBubbles(int x, int y) {
        ArrayList<BubbleActor> visited = new ArrayList<>();
        LinkedList<BubbleActor> next = new LinkedList<>();
        next.add(getBubble(x,y));

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
