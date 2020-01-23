package game;

import com.badlogic.gdx.math.Vector2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

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
}
