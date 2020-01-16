package server;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
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
        final var len = 0;
        if (args.length > len) {
            dbAdapter = new DbAdapter(args[0]);
        } else {
            dbAdapter = new DbAdapter("database");
        }
        dbImplement = new DbImplement(dbAdapter);
        dbImplement.initialize();
        ctx = SpringApplication.run(Server.class,args);

    }


    /**
     * Initialize the database schema.
     */
    public static void schemaCreate() throws FileNotFoundException {
        dbImplement.getDbAdapter().importTables();
    }

    /**
     * Clear the data from the database.
     */
    public static void deleteData() {
        try {
            dbImplement.getDbAdapter().clearData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Check user credentials.
     * @param reqBody get the string from the user connection
     * @return true if it can login or otherwise
     */
    @PostMapping(value = "/login")
    public boolean checkLogin(final @RequestBody Map<String, Object> reqBody) {
        try {
            return dbImplement.checkLogin(new User((String) reqBody.get("username"),
                    null, (String) reqBody.get("password")));
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
    public List<Score> getTop5() {
        return dbImplement.getTop5Score();
    }


    /**
     * Get the badges of the users.
     * @param username is the username
     * @return list of badges for this client.
     */
    @GetMapping(value = "/getBadges")
    public Badge[] getBadges(final @RequestParam String username) {
        try {
            ArrayList<Badge> result = dbImplement.getBadgeByUser(username);
            return result.toArray(new Badge[result.size()]);
        } catch (SQLException e) {
            return new Badge[0];
        }
    }

    /**
     * Add a new score to the database.
     * @param score is the Score object.
     * @return true if successful, false otherwise.
     */
    @PostMapping(value = "/addScore")
    public boolean addScore(final @RequestBody Score score) {
        try {
            return dbImplement.insertScore(score);
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Add a new badge to the database.
     * @param badge is the Badge object.
     * @return true if successful, false otherwise.
     */
    @PostMapping(value = "/addBadge")
    public boolean addBadge(final @RequestBody Badge badge) {
        try {
            return dbImplement.insertBadge(badge);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Add user api endpoint.
     * @param user User object containing its information.
     * @return true if successful, false otherwise.
     */
    @PostMapping(value = "/addUser")
    public boolean addUser(final @RequestBody User user) {
        try {
            return dbImplement.insertUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void stop() {
        ctx.close();
    }
}
