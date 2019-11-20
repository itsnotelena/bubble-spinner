package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * BubbleSpinner class.
 * This class handles all the interactions
 * with the Game library.
 */
public class BubbleSpinner extends Game {
    private transient SpriteBatch batch;
    private transient Texture img;
    private transient Game game = this;
    private transient OrthographicCamera camera;
    private transient Texture logo;

    @Override
    public void create() {
        logo = new Texture("assets/logo.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = new SpriteBatch();
        game.setScreen(new LoginScreen(game));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(logo,
                (float) (Gdx.graphics.getWidth() / 2. - logo.getWidth() / 2.),
                (float) (Gdx.graphics.getHeight() / 2. - logo.getHeight() / 2.));
        batch.end();

        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        logo.dispose();
    }
}
