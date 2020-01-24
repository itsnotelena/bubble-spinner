package game;

import com.badlogic.gdx.math.Vector2;
import config.Config;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class BubbleGrid {
    private static final int RADIUS = 100;
    private transient BubbleActor[][] bubbles = new BubbleActor[RADIUS * 2][RADIUS * 2];
    public transient Vector2 origin;
    private transient  float theta = 0;
    private transient float deltaTheta = 0;

    /**
     * Constructor for the bubble grid.
     * @param origin the origin of the center of the structure in screen coordinates
     */
    public BubbleGrid(Vector2 origin) {
        this.origin = origin;
    }

    /**
     * Calculate the torque of the incoming bubble on the structure,
     * apply the torque to the deltaTheta.
     * @param moveDirection the direction at which the incoming bubble is moving
     * @param strikePosition the position in screen space, where the bubble hit.
     */
    public void apply_torque(Vector2 moveDirection, Vector2 strikePosition) {
        Vector2 originToHit = strikePosition.sub(origin);
        float torque = originToHit.crs(moveDirection) / 250;
        deltaTheta += torque;
    }

    /**
     * Updates the rotation of the structure based on the rotation speed.
     */
    public void update_rotation() {
        double zeroone = 0.1;
        if (Math.abs(deltaTheta) > zeroone) {
            deltaTheta *= 0.97f;
        } else {
            deltaTheta = 0;
        }
        theta += deltaTheta;
    }

    /**
     * Sets a bubble to the grid, overwrites the bubble if needed.
     * @param x the x coordinate in the grid
     * @param y the y coordinate in the grid
     * @param bubbleActor the bubble Actor to add to the structure
     */
    public void setBubble(int x, int y, BubbleActor bubbleActor) {
        int dx = x + RADIUS;
        int dy = y + RADIUS;
        if (dx >= 0 && dx < RADIUS * 2 && dy >= 0 && dy < RADIUS * 2) {
            this.bubbles[dx][dy] = bubbleActor;
            if (bubbleActor != null) {
                bubbleActor.gridPos = new int[]{x, y};
            }
        }
    }

    /**
     * Gets a bubble actor from the grid using the coordinates given.
     * @param x the x coordinate on the grid
     * @param y the y coordinate on the grid
     * @return the bubble at the place in the grid.
     */
    public BubbleActor getBubble(int x, int y) {
        int dx = x + RADIUS;
        int dy = y + RADIUS;
        if (dx >= 0 && dx < RADIUS * 2 && dy >= 0 && dy < RADIUS * 2) {
            return this.bubbles[dx][dy];
        }
        return null;
    }

    /**
     * Converts a point on the grid, to a point on the screen.
     * @param x the coordinate on the grid
     * @param y the coordinate on the grid
     * @return the coordinate in screen space.
     */
    public Vector2 gridToWorld(int x, int y) {
        // Center of the structure should be at 0,0
        int offset = Math.abs(x) % 2;
        float colDistance = (float) (Config.Game.BUBBLE_SIZE * Math.sqrt(3) / 2.f);
        float dx = colDistance * x;
        float dy = (float) (y * Config.Game.BUBBLE_SIZE - offset * 0.5 * Config.Game.BUBBLE_SIZE);
        Vector2 vec = new Vector2(dx, dy).rotate(theta);
        return vec;
    }

    /**
     * Converts a point on the grid, to a point on the screen.
     * @param vec the coordinates in screen space
     * @return the coordinate in grid space.
     */
    public int[] worldToGrid(Vector2 vec) {
        vec = vec.rotate(-theta); // Remove the rotation
        float colDistance = (float) (Config.Game.BUBBLE_SIZE * Math.sqrt(3) / 2.f);
        int x = Math.round((vec.x / colDistance));
        int offset = Math.abs(x) % 2;
        int y = Math.round((float) (vec.y + offset * 0.5
                * Config.Game.BUBBLE_SIZE) / (float) Config.Game.BUBBLE_SIZE);
        return new int[]{x,y};
    }

    /**
     * Gets the neighbours of the given grid coordinate.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return A list of bubbles.
     */
    public List<BubbleActor> getNeighbours(int x, int y) {
        ArrayList<BubbleActor> list = new ArrayList<>();

        // Since our grid is hexagonal we have to do a bit of magic
        int offset = Math.abs(x) % 2;

        // Start by checking the position on the bottom left of
        // the current bubble and move clockwise around the bubble
        int[] dx = {-1, -1, 0, 1, 1,  0};
        int[] dy = { -offset,  1 - offset, 1, 1 - offset, -offset, -1}; //  fine as long as it works

        // Add all the neighbours that are present
        for (int i = 0; i < dx.length && i < dy.length; i++) {
            BubbleActor bub = getBubble(x + dx[i], y + dy[i]);
            if (bub != null) {
                list.add(bub);
            }
        }

        return list;
    }

    /**
     * Get the bubbles that are connected to the given coordinate.
     * @param x the x coordinate to start off from.
     * @param y the y coordinate to start off from.
     * @return a list of neighbours.
     */
    public List<BubbleActor> getConnectedBubbles(int x, int y) {
        ArrayList<BubbleActor> visited = new ArrayList<>();
        LinkedList<BubbleActor> next = new LinkedList<>();
        next.add(getBubble(x,y));

        while (!next.isEmpty()) {
            BubbleActor nextActor = next.pop();
            List<BubbleActor> neighbours = getNeighbours(nextActor.gridPos[0],
                    nextActor.gridPos[1]);
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

    /**
     * Get a set of all possible locations where bubbles can be added.
     * @return the set of bubbles.
     */
    public Set<Pair<Integer, Integer>> getPossiblePositions() {
        HashSet<Pair<Integer, Integer>> hm = new HashSet<>();
        for (int i = 0; i < RADIUS * 2; i++) {
            for (int j = 0; j < RADIUS * 2; j++) {
                BubbleActor bub = getBubble(i, j);
                if (bub == null && getNeighbours(i, j).size() > 0) {
                    hm.add(new Pair<>(i, j));
                }
            }
        }
        return hm;
    }

    public float getTheta() {
        return theta;
    }

    public void setTheta(float theta) {
        this.theta = theta;
    }

    public float getDeltaTheta() {
        return deltaTheta;
    }

    public void setDeltaTheta(float deltaTheta) {
        this.deltaTheta = deltaTheta;
    }

}
