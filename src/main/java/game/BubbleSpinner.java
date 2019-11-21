package game;

import com.badlogic.gdx.Game;
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
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
