package game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import game.ui.GameScreen;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


public class BotControllerTest {

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

    private transient BotController controller;

    /**
     * Setup method.
     */
    @BeforeEach
    public void before() {
        gameScreen = Mockito.mock(GameScreen.class);
        stage = Mockito.mock(Stage.class);
        shooter = Mockito.mock(Shooter.class);
        hexagonController = Mockito.mock(HexagonController.class);
        bubble = Mockito.mock(BubbleActor.class);
        Mockito.doNothing().when(hexagonController);
        Mockito.doNothing().when(hexagonController).drawGrid();
        Mockito.doNothing().when(shooter).initialize(Mockito.any());
        Mockito.when(shooter.current()).thenReturn(bubble);
        controller = new BotController(gameScreen, stage, 0);
        controller.setHexagonController(hexagonController);
        controller.setShooter(shooter);
    }

    @Test
    public void testInitialize() {
        Mockito.when(hexagonController.getBubbles()).thenReturn(new ArrayList<>());

        Mockito.when(hexagonController.getBuilder()).thenReturn(new EasyHexagonBuilder());
        controller.initialize();
        Mockito.verify(hexagonController, Mockito.times(1)).getBubbles();
    }

    @Test
    public void testCalculateBounds() {
        List<BubbleActor> bubbles = new ArrayList<>();
        bubbles.add(bubble);
        Mockito.when(hexagonController.getBubbles()).thenReturn(bubbles);
        Mockito.when(bubble.getPosition()).thenReturn(new Vector2(50, 50));
        Mockito.when(hexagonController.getBuilder()).thenReturn(new EasyHexagonBuilder());
        controller.initialize();
        Mockito.verify(hexagonController, Mockito.times(1)).getBubbles();
        Mockito.verify(bubble, Mockito.times(1)).getPosition();
    }

    @Test
    public void testShootCorrect() {
        controller.setBounds(new int[] { 0, 100, 0, 100 });
        Mockito.when(shooter.current()).thenReturn(bubble);
        Mockito.when(bubble.isMoving()).thenReturn(false);
        Mockito.doNothing().when(shooter)
                .shootBubbleScreenCoords(Mockito.anyInt(), Mockito.anyInt());
        controller.update();
        Mockito.verify(shooter, Mockito.times(1))
                .shootBubbleScreenCoords(Mockito.anyInt(), Mockito.anyInt());
    }

    @Test
    public void testShootMovingBubble() {
        controller.setShooter(shooter);
        Mockito.when(shooter.current()).thenReturn(bubble);
        Mockito.when(bubble.isMoving()).thenReturn(true);
        controller.update();
        Mockito.verify(shooter, Mockito.never())
                    .shootBubbleScreenCoords(Mockito.anyInt(), Mockito.anyInt());
    }
}
