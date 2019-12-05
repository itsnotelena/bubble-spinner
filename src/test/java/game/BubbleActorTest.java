package game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.backends.headless.mock.graphics.MockGraphics;
import com.badlogic.gdx.graphics.Texture;
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

public class BubbleActorTest {

    private static HeadlessApplication app;

    @Mock
    private transient Texture texture;

    @Mock
    private transient Stage stage;

    @Mock
    private transient Viewport viewport;

    /**
     * Setup the GDX headless application.
     */
    @BeforeEach
    public void before() {
        texture = Mockito.mock(Texture.class);
        stage = Mockito.mock(Stage.class);
        viewport = Mockito.mock(Viewport.class);
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
    public void testBubbleActorConstructor() {
        AtomicReference<Vector2> pos = new AtomicReference<>(new Vector2(0, 0));
        AtomicBoolean done = new AtomicBoolean(false);
        Gdx.app.postRunnable(() -> {
            Gdx.graphics = new GraphicsWrapper();
            BubbleActor bubbleActor = new BubbleActor(texture, stage);
            pos.set(bubbleActor.getPosition());
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Assertions.assertThat(pos.get()).isEqualTo(new Vector2(50, 68));
    }

    @Test
    public void testShiftXY() {
        AtomicBoolean done = new AtomicBoolean(false);
        AtomicReference<Vector2> firstPos = new AtomicReference<>(new Vector2(0,0));
        AtomicReference<Vector2> secondPos = new AtomicReference<>(new Vector2(0,0));
        AtomicReference<Vector2> thirdPos = new AtomicReference<>(new Vector2(0,0));
        AtomicReference<Vector2> fourthPos = new AtomicReference<>(new Vector2(0,0));
        Gdx.app.postRunnable(() -> {
            Gdx.graphics = new GraphicsWrapper();
            BubbleActor bubbleActor = new BubbleActor(texture, stage);
            bubbleActor.shiftX(true);
            firstPos.set(bubbleActor.getPosition());
            bubbleActor.shiftX(false);
            secondPos.set(bubbleActor.getPosition());
            bubbleActor.shiftY(true);
            thirdPos.set(bubbleActor.getPosition());
            bubbleActor.shiftY(false);
            fourthPos.set(bubbleActor.getPosition());
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Assertions.assertThat(firstPos.get()).isEqualTo(new Vector2(114, 68));
        Assertions.assertThat(secondPos.get()).isEqualTo(new Vector2(50, 68));
        Assertions.assertThat(thirdPos.get()).isEqualTo(new Vector2(50, 132));
        Assertions.assertThat(fourthPos.get()).isEqualTo(new Vector2(50, 68));
    }

    @Test
    public void testCenter() {
        AtomicReference<Vector2> pos = new AtomicReference<>(new Vector2(0, 0));
        AtomicBoolean done = new AtomicBoolean(false);
        Gdx.app.postRunnable(() -> {
            Gdx.graphics = new GraphicsWrapper();
            BubbleActor bubbleActor = new BubbleActor(texture, stage);
            pos.set(bubbleActor.center().getPosition());
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Assertions.assertThat(pos.get()).isEqualTo(new Vector2(50, 50));
    }

    @Test
    public void testSetPosition() {
        AtomicReference<Vector2> pos = new AtomicReference<>(new Vector2(0, 0));
        AtomicBoolean done = new AtomicBoolean(false);
        Gdx.app.postRunnable(() -> {
            Gdx.graphics = new GraphicsWrapper();
            BubbleActor bubbleActor = new BubbleActor(texture, stage);
            bubbleActor.setPosition(0, 0);
            pos.set(bubbleActor.getPosition());
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Assertions.assertThat(pos.get()).isEqualTo(new Vector2(32, 32));
    }

    @Test
    public void testMoveBy() {
        AtomicReference<Vector2> pos = new AtomicReference<>(new Vector2(0, 0));
        AtomicBoolean done = new AtomicBoolean(false);
        Gdx.app.postRunnable(() -> {
            Gdx.graphics = new GraphicsWrapper();
            BubbleActor bubbleActor = new BubbleActor(texture, stage);
            bubbleActor.moveBy(12, 14);
            pos.set(bubbleActor.getPosition());
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Assertions.assertThat(pos.get()).isEqualTo(new Vector2(62, 82));
    }

    @Test
    public void testCollideItSelf() {
        AtomicBoolean collided = new AtomicBoolean(false);
        AtomicBoolean done = new AtomicBoolean(false);
        Gdx.app.postRunnable(() -> {
            Gdx.graphics = new GraphicsWrapper();
            BubbleActor bubbleActor = new BubbleActor(texture, stage);
            collided.set(bubbleActor.collide(bubbleActor));
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Assertions.assertThat(collided).isTrue();
    }

    @Test
    public void testCollideOther() {
        AtomicBoolean collided = new AtomicBoolean(false);
        AtomicBoolean done = new AtomicBoolean(true);
        Gdx.app.postRunnable(() -> {
            Gdx.graphics = new GraphicsWrapper();
            BubbleActor bubbleActor = new BubbleActor(texture, stage, 100, 100);
            BubbleActor bubbleActorOther = new BubbleActor(texture, stage, 0, 0);
            collided.set(bubbleActor.collide(bubbleActorOther));
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Assertions.assertThat(collided.get()).isFalse();
    }

    @Test
    public void testMovingAndStop() {
        AtomicBoolean done = new AtomicBoolean(false);
        AtomicReference<Vector2> firstPos = new AtomicReference<>(new Vector2(0,0));
        AtomicReference<Vector2> secondPos = new AtomicReference<>(new Vector2(0,0));
        AtomicReference<Vector2> thirdPos = new AtomicReference<>(new Vector2(0,0));
        Gdx.app.postRunnable(() -> {
            Gdx.graphics = new GraphicsWrapper();
            Mockito.when(stage.getViewport()).thenReturn(viewport);
            BubbleActor bubbleActor = new BubbleActor(texture,
                    stage);
            firstPos.set(bubbleActor.getPosition());
            bubbleActor.setMovingDirection(new Vector2(1, 1));
            bubbleActor.update();
            secondPos.set(bubbleActor.getPosition());
            bubbleActor.stop();
            bubbleActor.update();
            thirdPos.set(bubbleActor.getPosition());
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Assertions.assertThat(firstPos.get()).isEqualTo(new Vector2(50, 68));
        Assertions.assertThat(secondPos.get()).isEqualTo(new Vector2(51, 69));
        Assertions.assertThat(thirdPos.get()).isEqualTo(new Vector2(51, 69));
    }

    @Test
    public void testVec() {
        AtomicReference<Vector2> pos = new AtomicReference<>(new Vector2(10, 10));
        AtomicBoolean done = new AtomicBoolean(false);
        Gdx.app.postRunnable(() -> {
            Gdx.graphics = new GraphicsWrapper();
            pos.set(new BubbleActor(texture, stage).vec(0, 0));
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Assertions.assertThat(pos.get()).isEqualTo(new Vector2(0, 0));
    }

    @Test
    public void testBounceAndUpdate() {
        AtomicReference<Vector2> pos = new AtomicReference<>(new Vector2(0, 0));
        AtomicBoolean done = new AtomicBoolean(false);
        Gdx.app.postRunnable(() -> {
            Gdx.graphics = new GraphicsWrapper();
            Mockito.when(stage.getViewport()).thenReturn(viewport);
            BubbleActor bubbleActor = new BubbleActor(texture, stage);
            bubbleActor.bounce();
            bubbleActor.setMovingDirection(new Vector2(2, 2));
            bubbleActor.update();
            pos.set(bubbleActor.getPosition());
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Assertions.assertThat(pos.get()).isEqualTo(new Vector2(52, 70));
    }

    @Test
    public void testOutsideScreen() {
        AtomicBoolean outside = new AtomicBoolean(false);
        AtomicBoolean done = new AtomicBoolean(false);
        Gdx.app.postRunnable(() -> {
            Gdx.graphics = new GraphicsWrapper();
            BubbleActor bubbleActor = new BubbleActor(texture, stage);
            bubbleActor.setPosition(50, 200);
            outside.set(bubbleActor.outSideScreen());
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Assertions.assertThat(outside.get()).isTrue();
    }

    @Test
    public void testInsideScreen() {
        AtomicBoolean outside = new AtomicBoolean(true);
        AtomicBoolean done = new AtomicBoolean(false);
        Gdx.app.postRunnable(() -> {
            Gdx.graphics = new GraphicsWrapper();
            BubbleActor bubbleActor = new BubbleActor(texture, stage, -100, -100);
            outside.set(bubbleActor.outSideScreen());
            done.set(true);
        });
        while (!done.get()) {
            assert true;
        }
        Assertions.assertThat(outside.get()).isFalse();
    }

    @AfterEach
    public void after() {
        app.exit();
    }


    class GraphicsWrapper extends MockGraphics {
        @Override
        public int getWidth() {
            return 100;
        }

        @Override
        public int getHeight() {
            return 100;
        }
    }
}
