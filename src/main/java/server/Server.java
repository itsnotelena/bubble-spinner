package server;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import java.util.List;
import java.util.Map;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Server {
    private static DbImplement dbImplement;
    private static DbAdapter dbAdapter;
    private static ConfigurableApplicationContext ctx;


    /**
     * start the Server.
     * @param args String[] to use
     */
    public static void main(String[] args) {
        final var len = 1;
        if (args.length > len) {
            dbAdapter = new DbAdapter(args[0]);
        } else {
            dbAdapter = new DbAdapter("database");
        }
        dbImplement = new DbImplement(dbAdapter);
        dbImplement.initialize();
        ctx = SpringApplication.run(Server.class,args);

    }

    public static void schemaCreate() {
        try {
            dbImplement.getDbAdapter().importTables();
        } catch (FileNotFoundException e) {
            e.printStackTrace ();
        }
    }

    public static void deleteData() {
        try {
            dbImplement.getDbAdapter().clearData();
        } catch (SQLException e) {
            e.printStackTrace ();
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
        String password = (String) reqBody.get("password");
        if (username == null || password == null) {
            return false;
        }
        try {
            return dbImplement.checkLogin(new User(username, null, password));
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

    /**
     * Get the five top user's scores.
     * @return 5 top scores.
     */
    @GetMapping(value = "/top5")
    public List<User> getTop5() {
        return dbImplement.getTop5Score();
    }

    @PostMapping(value = "/addScore")
    public boolean addScore(final @RequestBody Score score) {
        try {
            return dbImplement.insertScore(score);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping(value = "/addUser")
    public boolean addUser(final @RequestBody User user) {
        try {
            return dbImplement.insertUser (user);
        } catch (SQLException e) {
            e.printStackTrace ();
            return false;
        }
    }

    /**
     * Remove User from the userTable inside the database.
     *
     * @param reqBody get the string from the user connection
     * @return true if user is removed false if not
     */
    @PostMapping(value = "/removeUser")
    public boolean removeUserFromUserTable(final @RequestBody String reqBody) {
        String user = reqBody;
        return dbImplement.removeFromUser(user);
    }

    public static void stop() {
        ctx.close();
    }
}
