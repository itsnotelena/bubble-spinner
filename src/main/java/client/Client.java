package client;

import config.Config;
import org.springframework.web.client.RestTemplate;
import server.User;

public class Client {

    /**
     * Sends the object user to the db and it verifies the login.
     *
     * @param user Gets user Class from the client
     * @return true if it can login or otherwise
     */
    public boolean authenticate(User user) {
        RestTemplate restTemplate = new RestTemplate();
        Boolean res = restTemplate.postForObject(Config.Api.URL + "/login",
                                    new User(user.getUsername(), null, user.getPassword()),
                                boolean.class);
        return res != null ? res : false;
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

}
