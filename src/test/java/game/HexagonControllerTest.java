package game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import game.ui.GameScreen;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Set;

class HexagonControllerTest {

    @Mock
    private transient BubbleGrid bubbleGrid;

    @Mock
    private transient BubbleFactory bubbleFactory;

    @Mock
    private transient BubbleActor bubbleActor;

    transient HexagonController controller;

    @BeforeEach
    void setUp() {
        controller = new HexagonController(Mockito.mock(Stage.class), 2);
        bubbleGrid = Mockito.mock(BubbleGrid.class);
        bubbleFactory = Mockito.mock(BubbleFactory.class);
        bubbleActor = Mockito.mock(BubbleActor.class);
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
    public void testBubbleMissed() {
        Mockito.when(bubbleGrid.getPossiblePositions()).thenReturn(new HashSet<>());
        controller.setBubbleGrid(bubbleGrid);
        for (int i = 0; i < 3; ++i) {
            controller.bubbleMissed();
        }
        Mockito.verify(bubbleGrid, Mockito.times(1)).getPossiblePositions();
    }

    @Test
    public void testBubbleMissedAddBubble() {
        Set<Pair<Integer, Integer>> set = new HashSet<>();
        set.add(new Pair(1, 1));
        Mockito.when(bubbleGrid.getPossiblePositions()).thenReturn(set);
        Mockito.when(bubbleGrid.getBubble(1, 1)).thenReturn(null);
        Mockito.when(bubbleFactory.createBubbleGivenMap(Mockito.any())).thenReturn(bubbleActor);
        controller.setBubbleGrid(bubbleGrid);
        controller.setBubbleFactory(bubbleFactory);
        for (int i = 0; i < 3; ++i) {
            controller.bubbleMissed();
        }
        Mockito.verify(bubbleGrid, Mockito.times(1)).getPossiblePositions();
        Mockito.verify(bubbleFactory, Mockito.times(1))
                .createBubbleGivenMap(Mockito.any());
    }

    @Test
    public void testBubbleMissedPlaceAlreadyTaken() {
        Set<Pair<Integer, Integer>> set = new HashSet<>();
        set.add(new Pair(1, 1));
        Mockito.when(bubbleGrid.getPossiblePositions()).thenReturn(set);
        Mockito.when(bubbleGrid.getBubble(1, 1)).thenReturn(bubbleActor);
        Mockito.when(bubbleFactory.createBubbleGivenMap(Mockito.any())).thenReturn(bubbleActor);
        controller.setBubbleGrid(bubbleGrid);
        controller.setBubbleFactory(bubbleFactory);
        for (int i = 0; i < 3; ++i) {
            controller.bubbleMissed();
        }
        Mockito.verify(bubbleGrid, Mockito.times(1)).getPossiblePositions();
        Mockito.verify(bubbleFactory, Mockito.never()).createBubbleGivenMap(Mockito.any());
    }

    @Test
    void checkGameStatusNextLevel() {
        this.controller.lostGame = false;
        GameScreen gs = Mockito.mock(GameScreen.class);
        this.controller.checkGameStatus(gs);
        Mockito.verify(gs, Mockito.times(1)).nextLevel();
    }
}