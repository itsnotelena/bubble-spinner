package client;

import config.Config;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
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
        return res != null ? res : false;
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
     * Get the Top 5 User's scores.
     * @return usernames.
     */
    public List<User> getTop5() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<User>> users =
                restTemplate.exchange(Config.Api.URL + "/top5",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<>(){});
        return users.getBody();
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
        return res != null ? res : false;
    }

    /**
     * Remove the User from the database.
     * @param username username to be removed
     * @return true if removed or otherwise
     */
    public boolean removeUser(String username) {
        RestTemplate restTemplate = new RestTemplate();
        Boolean res = restTemplate.postForObject(Config.Api.URL  + "/removeUser",
                username,
                boolean.class);
        return res != null ? res : false;
    }

}
