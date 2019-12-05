package game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import game.ui.SplashScreen;
import server.User;

/**
 * BubbleSpinner class.
 * This class handles all the interactions
 * with the Game library.
 */
public class BubbleSpinner extends Game {
    public transient SpriteBatch batch;
    private transient User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
