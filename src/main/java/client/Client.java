package client;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import config.Config;
import game.Pair;
import game.ui.UserInterfaceFactory;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import server.Badge;
import server.Score;
import server.User;


public class Client {

    /**
     * Allows the user to authenticate.
     * @param user User object containing the details.
     * @return boolean.
     */
    public boolean authenticate(User user) {
        RestTemplate restTemplate = new RestTemplate();
        Boolean res = restTemplate.postForObject(Config.Api.URL + "/login",
                                    new User(user.getUsername(), null, user.getPassword()),
                                boolean.class);
        return res;
    }

    /**
     * Add score for a user to the database.
     * @param score is an object containing the score.
     * @return true if successful, false otherwise.
     */
    public boolean addScore(Score score) {
        RestTemplate restTemplate = new RestTemplate();
        Boolean user = restTemplate.postForObject(Config.Api.URL + "/addScore", score,
                                                boolean.class);
        return user;
    }

    /**
     * Add a new user to the database.
     * @param user is the new User object.
     * @return true if successful, false otherwise.
     */
    public boolean addUser(User user) {
        RestTemplate restTemplate = new RestTemplate();
        Boolean result = restTemplate.postForObject(Config.Api.URL + "/addUser", user,
                                                boolean.class);
        return result;
    }

    /**
     * Add a new badge to the database.
     * @param badge is the new Badge object.
     * @return true if successful, false otherwise.
     */
    public boolean addBadge(Badge badge) {
        RestTemplate restTemplate = new RestTemplate();
        Boolean result = restTemplate.postForObject(Config.Api.URL + "/addBadge", badge,
                                                boolean.class);
        return result;
    }

    /**
     * Get the Top 5 User's scores.
     * @return usernames.
     */
    public List<Score> getTop5() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<Score>> users =
                restTemplate.exchange(Config.Api.URL + "/top5",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>(){});
        return users.getBody();
    }

    /**
     * Get the badges of the user.
     * @return badges.
     */
    public Badge[] getBadges(User user) {
        return new RestTemplate().getForObject(Config.Api.URL
                        + "/getBadges" + "?username="
                        + user.getUsername(),Badge[].class);
    }

    /**
     * sends a user object to the db and checks if it
     * can be registered.
     *
     * @param user Gets user Class from the client
     * @return true if it was registered or otherwise
     */
    public boolean register(User user) {
        RestTemplate restTemplate = new RestTemplate();
        Boolean res = restTemplate.postForObject(Config.Api.URL + "/register",
                user,
                boolean.class);
        return res;
    }

    /**
     * Request the score of a specific user.
     * @param user User object.
     * @return Score object.
     */
    public Score getSCore(User user) {
        return new RestTemplate().postForObject(Config.Api.URL + "/getUserScore",
                                user,
                                Score.class);
    }

    /**
     * gets the user badges as a texture list.
     * @return list of textures
     */
    public List<Pair<Image, Label>> getUserBadge(User user) {
        Badge[] badge = new Client().getBadges(user);
        List<Pair<Image,Label>> result = new ArrayList<>();
        parseBadges(badge, result);
        return result;
    }

    private void parseBadges(Badge[] badge, List<Pair<Image, Label>> result) {
        for (int i = 0; i < badge.length; ++i) {
            Image img = getImageFromBadgeName(badge[i].getAward().getText());
            img.setSize(3,1);
            Label currentLabel = UserInterfaceFactory.createLabel(badge[i].getAward().getText(),
                    UserInterfaceFactory.LabelColor.BadgeText);
            result.add(new Pair(img,currentLabel));
        }
    }

    private Image getImageFromBadgeName(String name) {
        return new Image(new Texture(Gdx.files.internal("assets/Badges/" + name + ".png")));
    }
}
