package client;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.Badge;
import server.Score;
import server.Server;
import server.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientTest {

    /**
     * Database Test Set up.
     */
    @BeforeEach
    public void before() {
        String[] args = {"testClient"};
        Server.main(args);
        Server.deleteData();
    }

    /**
     * Sets up the environment after each test for next tests to work properly.
     *
     * @throws FileNotFoundException Exception type file not found
     */
    @AfterEach
    public void cleanUp() throws FileNotFoundException {
        Server.deleteData();
        Server.schemaCreate();
        Server.stop();
    }


    @Test
    public void testAuthFail() {
        Assertions.assertThat(new Client().authenticate(new User("tesst",
                null, "test"))).isFalse();
    }


    @Test
    void testAddUser() {
        Client client = new Client();
        User user = new User("jesus", "jesus@amen", "carpediem");
        Assertions.assertThat(client.addUser(user)).isTrue();
    }

    @Test
    void testAddScore() {
        Client client = new Client();
        Score score = new Score("rie", 0, 0);
        Assertions.assertThat(client.addScore(score)).isTrue();
    }


    @Test
    void testAddBadge() {
        Client client = new Client();
        Badge badge = new Badge("sasuke", "LEVEL2");
        Assertions.assertThat(client.addBadge(badge)).isTrue();
        Assertions.assertThat(new Client().authenticate(new User("test", null, "test"))).isFalse();
    }

    @Test
    public void getTop5() {

        User cardi = new User("cardi", "cardi@me", "abc");
        User anitta = new User("anitta", "anitta@me", "123");
        User ladygaga = new User("Lady Gaga", "ladygaga@me", "456");
        User taylorswift = new User("Taylor Swift", "taylor@me", "789");
        User rosalia = new User("Rosalia", "rosalia@me", "000");

        List<User> users = new ArrayList<>();

        users.add(cardi);
        users.add(anitta);
        users.add(ladygaga);
        users.add(taylorswift);
        users.add(rosalia);

        Client client = new Client();

        client.addUser(cardi);
        client.addUser(anitta);
        client.addUser(ladygaga);
        client.addUser(taylorswift);
        client.addUser(rosalia);

        client.addScore(new Score("cardi", 3, 55));
        client.addScore(new Score("anitta", 2, 50));
        client.addScore(new Score("Lady Gaga", 3, 30));
        client.addScore(new Score("Taylor Swift", 3, 25));
        client.addScore(new Score("Rosalia", 3, 24));

        List<Score> result = client.getTop5();

        assertEquals(users.get(0).getUsername(), result.get(0).getUsername());
        assertEquals(users.get(1).getUsername(), result.get(1).getUsername());
        assertEquals(users.get(2).getUsername(), result.get(2).getUsername());
        assertEquals(users.get(3).getUsername(), result.get(3).getUsername());
        assertEquals(users.get(4).getUsername(), result.get(4).getUsername());
    }

    @Test
    void login() {
        User a = new User("bla","uk","uk");
        Assertions.assertThat(new Client().register(a)).isTrue();
        Assertions.assertThat(new Client().authenticate(a)).isTrue();
    }

    @Test
    void loginFail() {
        Assertions.assertThat(new Client().authenticate(new User(null,
                "exit","ever"))).isFalse();
    }

    @Test
    void loginFailAgain() {
        Assertions.assertThat(new Client().authenticate(new User("doesnt",
                "exit",null))).isFalse();
    }

    @Test
    void registerFalse() {
        Assertions.assertThat(new Client().register(new User(null,"s","a"))).isFalse();
        Assertions.assertThat(new Client().register(new User("a",null,"a"))).isFalse();
        Assertions.assertThat(new Client().register(new User("a","s",null))).isFalse();
    }
}