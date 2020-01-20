package game;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class PairTest {

    @Test
    public void testContructor() {
        Pair<Integer, Integer> pair = new Pair<>(1, 1);
        Assertions.assertThat(pair.getLeft()).isEqualTo(1);
        Assertions.assertThat(pair.getRight()).isEqualTo(1);
    }

    @Test
    public void testGetterSetter() {
        Pair<Integer, Integer> pair = new Pair<>(1, 1);
        pair.setLeft(2);
        pair.setRight(3);
        Assertions.assertThat(pair.getLeft()).isEqualTo(2);
        Assertions.assertThat(pair.getRight()).isEqualTo(3);
    }
}
