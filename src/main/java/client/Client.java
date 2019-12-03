package client;

import config.Config;
import org.springframework.web.client.RestTemplate;
import server.User;

public class Client {

    /**
     * Authenticate the user by sending an HTTP POST request.
     * @param username String containing the username.
     * @param password String containing the password.
     * @return True if the credentials are correct, false otherwise.
     */
    public boolean authenticate(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        Boolean res = restTemplate.postForObject(Config.Api.URL + "/login",
                                    new User(username, null, password),
                                boolean.class);
        return res != null ? res : false;
    }

}
