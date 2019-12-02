package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.Map;

@RestController
@SpringBootApplication
public class Server {
    private static DbImplement dbImplement;

    public static void main(String[] args) {
        SpringApplication.run(Server.class,args);
        dbImplement = new DbImplement(new DbAdapter());
    }

    @PostMapping(value = "/login")
    public boolean checkLogin(final @RequestBody Map<String, Object> reqBody) {
        String username = (String) reqBody.get("username");
        String email = (String) reqBody.get("email");
        String password = (String) reqBody.get("password");
        if (username == null || password == null) {
            return false;
        }
        try {
            return dbImplement.checkLogin(new User(username, email, password));
        } catch (SQLException e) {
            return false;
        }
    }

}
