package game;

import client.Client;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import game.ui.SplashScreen;

import java.util.List;
import server.Server;
import server.User;


/**
 * BubbleSpinner class.
 * This class handles all the interactions
 * with the Game library.
 */
public class BubbleSpinner extends Game {
    public transient SpriteBatch batch;
    private transient User user;
    private transient Client client;

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
        client = new Client();
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        Server.stop();
        Gdx.app.exit();
    }

    /**
     * gets the user badges as a texture list.
     * @return list of textures
     */
    public List<Pair<Image,Label>> getThisUserBadge() {
        return client.getUserBadge(getUser());
    }
    
}
