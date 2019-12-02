package client;

import config.Config;
import org.springframework.web.client.RestTemplate;
import server.User;

public class Client {

    public boolean authenticate(String username, String password) {
        RestTemplate restTemplate = new RestTemplate();
        Boolean res = restTemplate.postForObject(Config.Api.URL + "/login",
                                    new User(username, null, password),
                                boolean.class);
        return res != null ? res : false;
    }

}
