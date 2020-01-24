package game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import game.ui.GameScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.assertj.core.api.Assertions;
import org.mockito.Mockito;

import java.util.ArrayList;


class HexagonControllerTest {

    HexagonController controller;


    @BeforeEach
    void setUp() {
        controller = new HexagonController(Mockito.mock(Stage.class), 0);
    }

//    @Test
//    void positionBubble() {
//        BubbleFactory fac = Mockito.mock(BubbleFactory.class);
//        Texture tex = Mockito.mock(Texture.class);
//        controller.setBubbleFactory(fac);
//        Mockito.when(fac.createBubble()).thenReturn(new BubbleActor(tex, null , 0, 1));
//
//        controller.positionBubble(0,1);
//        Assertions.assertThat(controller.getBubbles().get(1).getPosition().x).isEqualTo(0);
//        Assertions.assertThat(controller.getBubbles().get(1).getPosition().y).isEqualTo(1);
//    }

    @Test
    void drawGrid() {
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
        Assertions.assertThat(controller.formula(3)).isEqualTo(5);
    }

    @Test
    void formula2() {
        Assertions.assertThat(controller.formula(4)).isEqualTo(7);
    }

    @Test
    void formula3() {
        Assertions.assertThat(controller.formula(2)).isEqualTo(0);
    }

    @Test
    void bubbleMissed() {

    }

    @Test
    void checkGameStatusLost() {
        this.controller.lostGame = true;
        GameScreen gs = Mockito.mock(GameScreen.class);
        this.controller.checkGameStatus(gs);
        Mockito.verify(gs, Mockito.times(1)).dispose();
    }

    @Test
    void checkGameStatusNextLevel() {
        this.controller.lostGame = false;
        GameScreen gs = Mockito.mock(GameScreen.class);
        this.controller.checkGameStatus(gs);
        Mockito.verify(gs, Mockito.times(1)).nextLevel();
    }
}