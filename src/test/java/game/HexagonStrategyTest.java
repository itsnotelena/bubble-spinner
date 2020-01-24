package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class HexagonStrategyTest {

    transient HexagonStrategy builder;

    @BeforeEach
    void setUp(){

    }

    @Test
    void hardHexagonSetupTest() {
        builder = new HardHexagonStrategy();
        HexagonController hex = Mockito.mock(HexagonController.class);
        builder.setupUpHexagon(hex);
        Mockito.verify(hex, Mockito.times(126)).positionBubble(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    void medHexagonSetupTest() {
        builder = new MediumHexagonStrategy();
        HexagonController hex = Mockito.mock(HexagonController.class);
        builder.setupUpHexagon(hex);
        Mockito.verify(hex, Mockito.times(60)).positionBubble(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    void easyHexagonSetupTest() {
        builder = new EasyHexagonStrategy();
        HexagonController hex = Mockito.mock(HexagonController.class);
        builder.setupUpHexagon(hex);
        Mockito.verify(hex, Mockito.times(18)).positionBubble(Mockito.anyInt(), Mockito.anyInt());
    }
}
