package game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * BubbleSpinner class.
 * This class handles all the interactions
 * with the Game library.
 */
public class BubbleSpinner extends Game {
    private transient SpriteBatch batch;
    private transient Texture img;
    private transient Game game = this;

    @Override
    public void create() {
        game.setScreen(new LoginScreen(game));

    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
    }
}
