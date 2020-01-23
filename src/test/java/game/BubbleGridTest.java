package game;

import com.badlogic.gdx.math.Vector2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class BubbleGridTest {

    @Test
    public void addBubbleTest () {
        BubbleActor bub = Mockito.mock(BubbleActor.class);
        BubbleGrid grid = new BubbleGrid(new Vector2(0,0));
        grid.setBubble(0,0, bub);
        BubbleActor bub2 = grid.getBubble(0, 0);
        Assertions.assertThat(bub).isEqualTo(bub2);
    }
}
