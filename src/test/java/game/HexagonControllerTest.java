package game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import game.ui.GameScreen;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;



class HexagonControllerTest {

    transient HexagonController controller;


    @BeforeEach
    void setUp() {
        controller = new HexagonController(Mockito.mock(Stage.class), 2);
    }

    @Test
    void formula1() {
        Assertions.assertThat(controller.formula(3)).isEqualTo(5);
    }

    @Test
    void formula2() {
        Assertions.assertThat(controller.formula(4)).isEqualTo(5);
    }

    @Test
    void formula3() {
        Assertions.assertThat(controller.formula(2)).isEqualTo(0);
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