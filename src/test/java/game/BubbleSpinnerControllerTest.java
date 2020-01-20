package game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.mock.input.MockInput;
import com.badlogic.gdx.scenes.scene2d.Stage;
import game.ui.GameScreen;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class BubbleSpinnerControllerTest {

    @Mock
    private transient GameScreen gameScreen;

    @Mock
    private transient Stage stage;

    @Mock
    private transient Shooter shooter;

    @Mock
    private transient HexagonController hexagonController;

    @Mock
    private transient BubbleActor bubble;

    private transient BubbleSpinnerController controller;
    private transient CustomMockInput input;

    @Before
    public void before() {
        gameScreen = Mockito.mock(GameScreen.class);
        stage = Mockito.mock(Stage.class);
        shooter = Mockito.mock(Shooter.class);
        hexagonController = Mockito.mock(HexagonController.class);
        bubble = Mockito.mock(BubbleActor.class);
        Mockito.doNothing().when(hexagonController).initialize();
        Mockito.doNothing().when(hexagonController).drawGrid();
        Mockito.doNothing().when(shooter).initialize(Mockito.any());
        Mockito.when(shooter.current()).thenReturn(bubble);
        controller = new BubbleSpinnerController(gameScreen, stage, 0);
        controller.setHexagonController(hexagonController);
        controller.setShooter(shooter);
        input = new CustomMockInput();
        Gdx.input = input;
    }

    @Test
    public void testCheckShootFalse() {
        controller.initialize();
        controller.checkShoot(bubble);
        Mockito.verify(shooter, Mockito.never()).shootBubble();
    }

    @Test
    public void testShootSpace() {
        input.setKeyPresed(true);
        controller.checkShoot(bubble);
        Mockito.verify(shooter, Mockito.times(1)).shootBubble();
    }

    @Test
    public void testShooterMouse() {
        input.setTouched(true);
        controller.checkShoot(bubble);
        Mockito.verify(shooter, Mockito.times(1)).shootBubble();
    }

    @Test
    public void testUpdateNone() {
        controller.update();
        Mockito.verify(bubble, Mockito.times(1)).update();
        Mockito.verify(gameScreen, Mockito.never()).dispose();
        Mockito.verify(gameScreen, Mockito.never()).nextLevel();
    }

    @Test
    public void testUpdateLostGame() {
        hexagonController.lostGame = true;
        controller.update();
        Mockito.verify(gameScreen, Mockito.times(1)).dispose();
        Mockito.verify(gameScreen, Mockito.never()).nextLevel();
    }

    @Test
    public void testUpdateNextLevel() {
        List<BubbleActor> list = new ArrayList<>();
        list.add(bubble);
        Mockito.when(hexagonController.getBubbles()).thenReturn(list);
        controller.update();
        Mockito.verify(gameScreen, Mockito.never()).dispose();
        Mockito.verify(gameScreen, Mockito.times(1)).nextLevel();
    }

    class CustomMockInput extends MockInput {

        private transient boolean touched = false;
        private transient boolean keyPressed = false;

        public CustomMockInput() {
            super();
        }

        @Override
        public boolean isTouched() {
            return touched;
        }

        public void setTouched(boolean touched) {
            this.touched = touched;
        }

        @Override
        public boolean isKeyJustPressed(int key) {
            return keyPressed;
        }

        public void setKeyPresed(boolean keyPressed) {
            this.keyPressed = keyPressed;
        }
    }
}
