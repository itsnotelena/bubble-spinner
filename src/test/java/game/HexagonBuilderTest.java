package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class HexagonBuilderTest {

    HexagonBuilder builder;

    @BeforeEach
    void setUp(){

    }

    @Test
    void hardHexagonSetupTest() {
        builder = new HardHexagonBuilder();
        HexagonController hex = Mockito.mock(HexagonController.class);
        builder.setupUpHexagon(hex);
        Mockito.verify(hex, Mockito.times(126)).positionBubble(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    void medHexagonSetupTest() {
        builder = new MediumHexagonBuilder();
        HexagonController hex = Mockito.mock(HexagonController.class);
        builder.setupUpHexagon(hex);
        Mockito.verify(hex, Mockito.times(60)).positionBubble(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    void easyHexagonSetupTest() {
        builder = new EasyHexagonBuilder();
        HexagonController hex = Mockito.mock(HexagonController.class);
        builder.setupUpHexagon(hex);
        Mockito.verify(hex, Mockito.times(18)).positionBubble(Mockito.anyInt(), Mockito.anyInt());
    }
}
