package server;

import java.sql.SQLException;

import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Server {
    private static DbImplement dbImplement;

    /**
     * start the Server.
     * @param args String[] to use
     */
    public static void main(String[] args) {
        SpringApplication.run(Server.class,args);
        final var len = 1;
        if (args.length == len) {
            dbImplement = new DbImplement(new DbAdapter(args[0]));
        } else {
            dbImplement = new DbImplement(new DbAdapter());
        }
    }

    /**
     * Check user credentials.
     * @param reqBody get the string from the user connection
     * @return true if it can login or otherwise
     */
    @PostMapping(value = "/login")
    public boolean checkLogin(final @RequestBody Map<String, Object> reqBody) {
        String username = (String) reqBody.get("username");
        String email = (String) reqBody.get("email");
        String password = (String) reqBody.get("password");
        if (username == null || password == null || email == null) {
            return false;
        }
        try {
            return dbImplement.checkLogin(new User(username, email, password));
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Add A user to the database using the details sent from the client.
     * @param reqBody get the string from the user connection
     * @return true if it is added or otherwise
     */
    @PostMapping(value = "/register")
    public boolean register(final @RequestBody Map<String, Object> reqBody) {
        String username = (String) reqBody.get("username");
        String email = (String) reqBody.get("email");
        String password = (String) reqBody.get("password");
        if (username == null || password == null || email == null) {
            return false;
        }
        try {
            return dbImplement.insertUser(new User(username, email, password));
        } catch (SQLException e) {
            return false;
        }
    }
}
