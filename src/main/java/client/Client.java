package client;

import config.Config;
import org.springframework.web.client.RestTemplate;
import server.User;

public class Client {

    public boolean authenticate(User u) {
        RestTemplate restTemplate = new RestTemplate();
        Boolean res = restTemplate.postForObject(Config.Api.URL + "/login",
                                    new User(u.getUsername(), null, u.getPassword()),
                                boolean.class);
        return res != null ? res : false;
    }

    public boolean register(User user) {
        RestTemplate restTemplate = new RestTemplate();
        Boolean res = restTemplate.postForObject(Config.Api.URL + "/register",
                user,
                boolean.class);
        return res != null ? res : false;
    }

}
