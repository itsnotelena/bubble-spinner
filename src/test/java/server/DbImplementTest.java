package server;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class DbImplementTest {
    private static DbAdapter dbAdapter = new DbAdapter("test");
    private transient DbImplement dbImplement = new DbImplement(dbAdapter);

    public DbImplementTest() {
    }

    @BeforeAll
    static void setUp() throws FileNotFoundException {
        dbAdapter.importTables();
    }

    @AfterEach
    void clean() throws SQLException {
        dbAdapter.clearData();
    }

    @Test
    void insertAndCheckLogin() throws SQLException {
        User a = new User("lalalq","nsg","zal");
        dbImplement.removeFromUser(a.getUsername());
        boolean resA = dbImplement.insertUser(a);
        boolean res = dbImplement.checkLogin(a);
        Assertions.assertThat(res).isTrue();
        Assertions.assertThat(resA).isTrue();
        System.out.println(dbAdapter.getConn().isClosed());

    }

    @Test
    void searchProperly() throws SQLException {
        Score a = new Score("lalalq",1,1);
        boolean resA = dbImplement.insertScore(a);
        Assertions.assertThat(resA).isTrue();
        Assertions.assertThat(dbImplement.removeFromScore(a.getUsername()));
        dbImplement.removeFromScore(a.getUsername());
    }

    @Test
    void searchFails() {
        Assertions.assertThat(dbImplement.searchInUsers("lal")).isFalse();
        Assertions.assertThat(dbImplement.searchInScore("lal")).isFalse();
        Assertions.assertThat(dbImplement.searchInGame("lal")).isFalse();
    }

    @Test
    void getScoreByUsername() throws SQLException {
        Score a = new Score("cardi", 3, 55);
        dbImplement.removeFromScore(a.getUsername());
        boolean resA = dbImplement.insertScore(a);
        Assertions.assertThat(resA).isTrue();

        Optional<Score> scoreOptional = dbImplement.getScoreByUser(a.getUsername());
        Assertions.assertThat(scoreOptional.isPresent()).isTrue();
        Assertions.assertThat(scoreOptional.get()).isEqualTo(a);
    }

    @Test
    void getBadgesByUsername() throws SQLException {
        Badge beyhive = new Badge("beyonce", "LEVEL1");
        dbImplement.removeFromBadge(beyhive.getUsername());
        boolean resA = dbImplement.insertBadge(beyhive);
        Assertions.assertThat(resA).isTrue();

        Optional<Badge> badgeOptional = dbImplement.getBadgeByUser(beyhive.getUsername());
        Assertions.assertThat(badgeOptional.isPresent()).isTrue();
        Assertions.assertThat(badgeOptional.get()).isEqualTo(beyhive);
    }

    @Test
    void getTop5Score() throws SQLException {
        Score one = new Score("cardi", 3, 55);
        Score two = new Score("anitta", 4, 31);
        Score three = new Score("Lady Gaga", 2, 30);
        Score four = new Score("Taylor Swift", 2, 30);
        Score five = new Score("Rosalia", 2, 29);

        dbImplement.removeFromScore(one.getUsername());
        dbImplement.removeFromScore(two.getUsername());
        dbImplement.removeFromScore(three.getUsername());
        dbImplement.removeFromScore(four.getUsername());
        dbImplement.removeFromScore(five.getUsername());

        User cardi = new User("cardi", "cardi@me", "abc");
        User anitta = new User("anitta", "anitta@me", "123");
        User ladygaga = new User("Lady Gaga", "ladygaga@me", "456");
        User taylorswift = new User("Taylor Swift", "taylor@me", "789");
        User rosalia = new User("Rosalia", "rosalia@me", "000");

        dbImplement.insertUser(cardi);
        dbImplement.insertUser(anitta);
        dbImplement.insertUser(ladygaga);
        dbImplement.insertUser(taylorswift);
        dbImplement.insertUser(rosalia);

        dbImplement.insertScore(one);
        dbImplement.insertScore(two);
        dbImplement.insertScore(three);
        dbImplement.insertScore(four);
        dbImplement.insertScore(five);


        List<Score> scoreOptional = dbImplement.getTop5Score();
        List<User> list = new ArrayList<>();
        list.add(dbImplement.getUserByUsername(one.getUsername()).get());
        list.add(dbImplement.getUserByUsername(two.getUsername()).get());
        list.add(dbImplement.getUserByUsername(three.getUsername()).get());
        list.add(dbImplement.getUserByUsername(four.getUsername()).get());
        list.add(dbImplement.getUserByUsername(five.getUsername()).get());

        assertEquals(scoreOptional.size(), 5);
        for(int i = 0; i < scoreOptional.size(); i++){
            assertEquals( scoreOptional.get(i).getUsername() , list.get(i).getUsername());
        }
    }

    @Test
    void alreadyExists() throws SQLException {
        User a = new User("h","h","h");
        Game b = new Game("a",1,1);
        Score c = new Score("a",1,1);
        Badge d = new Badge("a","b");
        dbImplement.insertUser(a);
        dbImplement.insertGame(b);
        dbImplement.insertScore(c);
        dbImplement.insertBadge(d);
        Assertions.assertThat(dbImplement.insertUser(a)).isFalse();
        Assertions.assertThat(dbImplement.insertGame(b)).isFalse();
        Assertions.assertThat(dbImplement.insertScore(c)).isFalse();
        Assertions.assertThat(dbImplement.insertBadge(d)).isFalse();

    }

}
