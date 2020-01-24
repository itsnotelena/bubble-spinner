package game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.mockito.Mockito;


class HexagonControllerTest {

    HexagonController controller;

    @BeforeEach
    void setUp() {
        controller = new HexagonController(Mockito.mock(Stage.class), 0);
    }

    @Test
    void getBubbles() {
    }

    @Test
    void positionBubble() {
    }

    @Test
    void drawGrid() {
    }

    @Test
    void setBuilder() {
    }

    @Test
    void getBuilder() {
    }

    @Test
    void getPoppable() {
    }

    @Test
    void getResult() {
    }

    @Test
    void checkCollisions() {
    }

    @Test
    void popBubbles() {
    }

    @Test
    void popFloatingBubbles() {
    }

    @Test
    void formula1() {

    }

    @Test
    void bubbleMissed() {
    }

    @Test
    void getMapBubbles() {
    }

    @Test
    void checkGameStatus() {

    }
}