package client;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.VisibleForTesting;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import server.Badge;
import server.Score;
import server.Server;
import server.User;

public class ClientTest {

    /**
     * Database Test Set up.
     */
    @BeforeAll
    public static void before() {
        String[] args = {"testClient"};
        Server.main(args);
        Server.schemaCreate();
        Server.deleteData();
    }

    @AfterEach
    public void cleanUp() {
        Server.deleteData();
        Server.schemaCreate();
    }


    @Test
    public void testAuthFail() {
        Assertions.assertThat(new Client().authenticate(new User("test", null, "test"))).isFalse();
    }

    @Test
    void testAddUser() {
        Client client = new Client();
        User user = new User("jesus", "jesus@amen", "carpediem");
        Assertions.assertThat(client.addUser(user)).isTrue();
    }

    /*@Test
    void testAddScore() {
        Client client = new Client();
        Score score = new Score("rie", 0, 0);
        Assertions.assertThat(client.addScore(score)).isTrue();
    }
    **/

    /*@Test
    void testAddBadge() {
        Client client = new Client();
        Badge badge = new Badge("sasuke", "LEVEL2");
        Assertions.assertThat(client.addBadge(badge)).isTrue();
        }
    **/

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

        List<User> result = client.getTop5();

        Assertions.assertThat(users.get(0).equals(result.get(0))).isTrue();
        Assertions.assertThat(users.get(1).equals(result.get(1))).isTrue();
        Assertions.assertThat(users.get(2).equals(result.get(2))).isTrue();
        Assertions.assertThat(users.get(3).equals(result.get(3))).isTrue();
        Assertions.assertThat(users.get(4).equals(result.get(4))).isTrue();

        Assertions.assertThat(result).isEqualTo(users);
    }

   /** @Test
    void getBadges() {
        User billie = new User("billie", "billie@me", "badguy");
        List<User> users = new ArrayList<>();
        users.add(billie);

        Client client = new Client();
        client.addUser(billie);

        client.addBadge(new Badge("billie", "LEVEL3"));

        List<Badge> result = client.getBadges();

        Assertions.assertThat(users.get(0).equals(result.get(0))).isTrue();
        Assertions.assertThat(result).isEqualTo(users);
    }
    **/

    @Test
    void login() {
        User a = new User("bla","uk","uk");
        Assertions.assertThat(new Client().register(a)).isTrue();
        Assertions.assertThat(new Client().authenticate(a)).isTrue();
        new Client().removeUser("bla");
    }

    @Test
    void loginFail() {
        Assertions.assertThat(new Client().authenticate(new User("doesnt",
                "exit","ever"))).isFalse();
    }
}