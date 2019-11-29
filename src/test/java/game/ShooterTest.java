package game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.mockito.Mockito;


public class ShooterTest {

    private static HeadlessApplication app;

    @Mock
    private transient Stage stage;

    @Mock
    private transient Viewport viewport;

    @Mock
    private transient BubbleFactory bubbleFactory;

    @Mock
    private transient BubbleActor bubbleActor;

    /**
     * Setup the GDX headless application.
     */
    @BeforeEach
    public void before() {
        stage = Mockito.mock(Stage.class);
        viewport = Mockito.mock(Viewport.class);
        bubbleFactory = Mockito.mock(BubbleFactory.class);
        bubbleActor = Mockito.mock(BubbleActor.class);
        Mockito.doNothing().when(bubbleFactory).addTexture(Mockito.anyString());
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        app = new HeadlessApplication(new ApplicationListener() {
            @Override
            public void create() {

            }

            @Override
            public void resize(int width, int height) {

            }

            @Override
            public void render() {

            }

            @Override
            public void pause() {

            }

            @Override
            public void resume() {

            }

            @Override
            public void dispose() {

            }
        }, config);
    }

    @Test
    public void testShooterConstructor() {
        AtomicReference<BubbleActor> pollResult = new AtomicReference<>(bubbleActor);
        AtomicReference<BubbleActor> peekResult = new AtomicReference<>(bubbleActor);
        AtomicBoolean done = new AtomicBoolean(false);
        Gdx.app.postRunnable(() -> {
            Shooter shooter = new Shooter(stage);
            shooter.setBubbleFactory(bubbleFactory);
            pollResult.set(shooter.poll());
            peekResult.set(shooter.current());
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Assertions.assertThat(pollResult.get()).isNull();
        Assertions.assertThat(peekResult.get()).isNull();
    }

    @Test
    public void testRefill() {
        AtomicReference<BubbleActor> result = new AtomicReference<>(null);
        AtomicBoolean done = new AtomicBoolean(false);
        Gdx.app.postRunnable(() -> {
            Shooter shooter = new Shooter(stage);
            shooter.setBubbleFactory(bubbleFactory);
            Mockito.when(bubbleFactory.next()).thenReturn(bubbleActor);
            shooter.refill();
            result.set(shooter.current());
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Assertions.assertThat(result.get()).isEqualTo(bubbleActor);
    }

    @Test
    public void testInitialize() throws InterruptedException {
        AtomicBoolean done = new AtomicBoolean(false);
        Gdx.app.postRunnable(() -> {
            Shooter shooter = new Shooter(stage);
            shooter.setBubbleFactory(bubbleFactory);
            Mockito.when(bubbleFactory.next()).thenReturn(bubbleActor);
            shooter.initialize();
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Mockito.verify(bubbleActor, Mockito.times(5))
                .shiftX(Mockito.anyBoolean(), Mockito.anyInt());
        Mockito.verify(stage, Mockito.times(5))
                .addActor(Mockito.any(BubbleActor.class));
    }

    @Test
    public void testShiftBubbles() {
        AtomicBoolean done = new AtomicBoolean(false);
        Gdx.app.postRunnable(() -> {
            Shooter shooter = new Shooter(stage);
            shooter.setBubbleFactory(bubbleFactory);
            Mockito.when(bubbleFactory.next()).thenReturn(bubbleActor);
            shooter.initialize();
            shooter.poll();
            shooter.shiftBubbles();
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Mockito.verify(bubbleActor, Mockito.times(4))
                .shiftX(false);
        Mockito.verify(bubbleActor, Mockito.times(6))
                .shiftX(Mockito.anyBoolean(), Mockito.anyInt());
    }

    @Test
    public void testShootBubble() {
        AtomicBoolean done = new AtomicBoolean(false);
        AtomicReference<BubbleActor> current = new AtomicReference<>(null);
        Gdx.app.postRunnable(() -> {
            Mockito.when(stage.getViewport()).thenReturn(viewport);
            Shooter shooter = new Shooter(stage);
            shooter.setBubbleFactory(bubbleFactory);
            Mockito.when(bubbleFactory.next()).thenReturn(bubbleActor);
            shooter.initialize();
            shooter.shootBubble();
            current.set(shooter.current());
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Mockito.verify(current.get()).setMovingDirection(Mockito.any(Vector2.class));
    }

    @AfterEach
    public void after() {
        app.exit();
    }

}
