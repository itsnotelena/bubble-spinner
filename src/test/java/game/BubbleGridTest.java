package game;

import com.badlogic.gdx.math.Vector2;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class BubbleGridTest {

    @Test
    public void addBubbleTest() {
        BubbleActor bub = Mockito.mock(BubbleActor.class);
        BubbleGrid grid = new BubbleGrid(new Vector2(0,0));
        grid.setBubble(0,0, bub);
        BubbleActor bub2 = grid.getBubble(0, 0);
        Assertions.assertThat(bub).isEqualTo(bub2);
    }

    @Test
    public void getBubbleOutOfBounds() {
        BubbleGrid grid = new BubbleGrid(new Vector2(0,0));
        BubbleActor bub = grid.getBubble(1000, 1000);
        Assertions.assertThat(bub).isEqualTo(null);
    }


    @Test
    public void getNeighbours() {
        BubbleActor bub = Mockito.mock(BubbleActor.class);
        BubbleActor bub2 = Mockito.mock(BubbleActor.class);
        BubbleActor bub3 = Mockito.mock(BubbleActor.class);
        BubbleActor bub4 = Mockito.mock(BubbleActor.class);

        BubbleGrid grid = new BubbleGrid(new Vector2(0,0));
        grid.setBubble(0,0, bub);
        grid.setBubble(0, 1, bub2);
        grid.setBubble(1, 0, bub3);
        grid.setBubble(0, -1, bub4);

        List<BubbleActor> list = grid.getNeighbours(0,0);

        Assertions.assertThat(list.contains(bub2));
        Assertions.assertThat(list.contains(bub3));
        Assertions.assertThat(list.contains(bub4));
    }

    @Test
    public void getNeighboursEmpty() {
        BubbleActor bub = Mockito.mock(BubbleActor.class);

        BubbleGrid grid = new BubbleGrid(new Vector2(0,0));
        grid.setBubble(0,0, bub);

        List<BubbleActor> list = grid.getNeighbours(0,0);
        Assertions.assertThat(list.isEmpty());
    }


    @Test
    public void getConnectedBubbles() {
        BubbleActor bub = Mockito.mock(BubbleActor.class);
        BubbleActor bub2 = Mockito.mock(BubbleActor.class);
        BubbleActor bub3 = Mockito.mock(BubbleActor.class);
        BubbleActor bub4 = Mockito.mock(BubbleActor.class);

        BubbleGrid grid = new BubbleGrid(new Vector2(0,0));
        grid.setBubble(0,0, bub);
        grid.setBubble(0, 1, bub2);
        grid.setBubble(3, 0, bub3);
        grid.setBubble(0, -1, bub4);

        List<BubbleActor> list = grid.getConnectedBubbles(0,0);

        Assertions.assertThat(list.contains(bub2));
        Assertions.assertThat(!list.contains(bub3));
        Assertions.assertThat(list.contains(bub4));
    }

    @Test
    public void getConnectedBubblesEmpty() {
        BubbleActor bub = Mockito.mock(BubbleActor.class);

        BubbleGrid grid = new BubbleGrid(new Vector2(0,0));
        grid.setBubble(0,0, bub);

        List<BubbleActor> list = grid.getConnectedBubbles(0,0);
        Assertions.assertThat(list.isEmpty());
    }

    @Test
    public void applyZeroTorqueTest() {
        BubbleGrid grid = new BubbleGrid(new Vector2(0,0));
        grid.apply_torque(new Vector2(0,0), new Vector2(0,0));
        Assertions.assertThat(grid.getDelta_theta()).isEqualTo(0);
    }

    @Test
    public void applyTorqueTest() {
        BubbleGrid grid = new BubbleGrid(new Vector2(0,0));
        grid.apply_torque(new Vector2(0,1), new Vector2(1,0));
        Assertions.assertThat(grid.getDelta_theta()).isEqualTo(0.004f);
    }

    @Test
    public void updateRotation() {
        BubbleGrid grid = new BubbleGrid(new Vector2(0,0));
        grid.setDelta_theta(1);
        grid.update_rotation();
        Assertions.assertThat(grid.getDelta_theta()).isEqualTo(0.97f);
    }

    @Test
    public void updateRotationTest() {
        BubbleGrid grid = new BubbleGrid(new Vector2(0,0));
        grid.update_rotation();
        Assertions.assertThat(grid.getDelta_theta()).isEqualTo(0);
    }

    @Test
    public void gridToWorld() {
        BubbleGrid grid = new BubbleGrid(new Vector2(0,0));
        Assertions.assertThat(grid.worldToGrid(grid.gridToWorld(10, 10)))
                .isEqualTo(new int[]{10, 10});
    }

    @Test
    public void setBubbleNull() {
        BubbleGrid grid = new BubbleGrid(new Vector2(0,0));
        grid.setBubble(0,0, null);
        Assertions.assertThat(grid.getBubble(0,0)).isEqualTo(null);
    }
}
