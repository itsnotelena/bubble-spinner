package game;

import client.Client;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import game.ui.SplashScreen;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;
import server.Badge;
import server.BadgesEnum;
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
        Server.stop();
        Gdx.app.exit();
    }

    /**
     * gets the user badges as a texture list.
     * @return list of textures
     */
    @SuppressWarnings("PMD")
    //Suppressed error for UR anomaly error for the variable in the for loop.
    public List<Pair<Image,Label>> getThisUserBadge() {
        Badge[] badge = new Client().getBadges(user);
        System.out.println(badge.length);
        List<Pair<Image,Label>> result = new ArrayList<>();
        for (Badge name : badge) {
            Image img = new Image(new Texture(Gdx.files
                    .internal("assets/Badges/"
                            + name.getAward().getText()
                            + ".png")));
            img.setSize(3,1);
            Label currentLabel = new Label(name.getAward().getText(),
                    new Skin(Gdx.files.internal("assets/uiskin.json")),
                    "default");
            currentLabel.setColor(0.5f,0.5f,1,1);
            result.add(new Pair(img,currentLabel));
        }
        return result;
    }
    
}
