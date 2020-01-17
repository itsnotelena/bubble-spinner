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


public class ClientTest {

    /**
     * Database Test Set up.
     */
    @BeforeEach
    public void before() {
        String[] args = {"test"};
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
                null, "tesst"))).isFalse();
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
        Client client = new Client();
        client.register(new User("cardi","",""));
        client.register(new User("anitta","",""));
        client.register(new User("Lady Gaga","",""));
        client.register(new User("Taylor Swift","",""));
        client.register(new User("Rosalia","",""));

        client.addScore(new Score("cardi", 3, 55));
        client.addScore(new Score("anitta", 2, 50));
        client.addScore(new Score("Lady Gaga", 3, 30));
        client.addScore(new Score("Taylor Swift", 3, 25));
        client.addScore(new Score("Rosalia", 3, 24));

        List<Score> result = client.getTop5();

        Assertions.assertThat("cardi".equals(result.get(0).getUsername())).isTrue();
        Assertions.assertThat("anitta".equals(result.get(1).getUsername())).isTrue();
        Assertions.assertThat("Lady Gaga".equals(result.get(2).getUsername())).isTrue();
        Assertions.assertThat("Taylor Swift".equals(result.get(3).getUsername())).isTrue();
        Assertions.assertThat("Rosalia".equals(result.get(4).getUsername())).isTrue();
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

    @Test
    void getBadges() {

        String maye = "maye";

        List<Badge> result = new ArrayList<>();
        Badge bla = new Badge(maye,"a");
        Badge blaa = new Badge(maye,"b");
        Badge blaaa = new Badge(maye,"c");

        result.add(bla);
        result.add(blaa);
        result.add(blaa);
        result.add(blaaa);

        Client client = new Client();

        assert client.addBadge(bla);
        assert client.addBadge(blaa);
        assert client.addBadge(blaaa);

        Badge[] output = client.getBadges(new User(maye,"",""));
        for (int i = 0; i < output.length; i++) {
            Assertions.assertThat(output[i]).isIn(result);
        }
    }
}