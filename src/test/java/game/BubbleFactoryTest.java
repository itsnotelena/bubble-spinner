package game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.Mock;
import org.mockito.Mockito;


public class BubbleFactoryTest {

    private static HeadlessApplication app;

    @Mock
    private transient Stage stage;

    @Mock
    private transient Texture texture;

    /**
     * Setup the GDX headless application.
     */
    @BeforeEach
    public void before() {
        stage = Mockito.mock(Stage.class);
        texture = Mockito.mock(Texture.class);
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
    public void testBubbleFactoryNext() {
        AtomicReference<BubbleActor> next = new AtomicReference<>();
        AtomicBoolean done = new AtomicBoolean(false);
        Gdx.app.postRunnable(() -> {
            BubbleFactory bubbleFactory = new BubbleFactory(stage);
            bubbleFactory.addTexture(texture);
            next.set(bubbleFactory.createBubble());
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Assertions.assertThat(next.get()).isInstanceOf(BubbleActor.class);
    }

    @Test
    public void testCreateBubbleGivenMapEmpty() {
        AtomicBoolean done = new AtomicBoolean(false);
        AtomicReference<BubbleActor> actor = new AtomicReference<>();
        Gdx.app.postRunnable(() -> {
            BubbleFactory bubbleFactory = new BubbleFactory(stage);
            bubbleFactory.addTexture(texture);
            actor.set(bubbleFactory.createBubbleGivenMap(new int[] { 0, 0 }));
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Assertions.assertThat(actor.get()).isNull();
    }

    @Test
    public void testCreateBubbleGivenMap() {
        AtomicBoolean done = new AtomicBoolean(false);
        AtomicReference<BubbleActor> actor = new AtomicReference<>();
        Gdx.app.postRunnable(() -> {
            BubbleFactory bubbleFactory = new BubbleFactory(stage);
            bubbleFactory.addTexture(texture);
            actor.set(bubbleFactory.createBubbleGivenMap(new int[] { 2, 0 }));
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Assertions.assertThat(actor.get()).isInstanceOf(BubbleActor.class);
        Assertions.assertThat(actor.get().getColorId()).isEqualTo(0);
    }

    @AfterEach
    public void after() {
        app.exit();
    }
}