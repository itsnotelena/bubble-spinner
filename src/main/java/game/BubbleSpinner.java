package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * BubbleSpinner class.
 * This class handles all the interactions
 * with the Game library.
 */
public class BubbleSpinner extends Game {
    protected transient SpriteBatch batch;


    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen(new SplashScreen(this));
    }

    @Override
    public void render() {
//        Gdx.gl.glClearColor(1, 1, 1, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//        batch.begin();
//        batch.draw(logo,
//                (float) (Gdx.graphics.getWidth() / 2. - logo.getWidth() / 2.),
//                (float) (Gdx.graphics.getHeight() / 2. - logo.getHeight() / 2.));
//        batch.end();
//
       super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
