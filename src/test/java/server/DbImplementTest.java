package server;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


public class DbImplementTest {
    private static DbAdapter dbAdapter = new DbAdapter("test");
    private transient DbImplement dbImplement = new DbImplement(dbAdapter);

    @BeforeAll
    static void setUp() throws FileNotFoundException {
        dbAdapter.importTables();
    }

    @Test
    void insertAndCheckLogin() throws SQLException {
        User a = new User("lalalq","nsg","zal");
        dbImplement.removeUser(a.getUsername(),"users");
        boolean resA = dbImplement.insertUser(a);
        boolean res = dbImplement.checkLogin(a);
        Assertions.assertThat(res).isTrue();
        Assertions.assertThat(resA).isTrue();
        System.out.println(dbAdapter.getConn().isClosed());

    }

    @Test
    void searchProperly() throws SQLException {
        Score a = new Score("lalalq",1,1);
        dbImplement.removeUser(a.getUsername(),DbImplement.SCORE);
        boolean resA = dbImplement.insertScore(a);
        Assertions.assertThat(resA).isTrue();
        Assertions.assertThat(dbImplement.searchUser(a.getUsername(),DbImplement.SCORE));
        dbImplement.removeUser(a.getUsername(),"score");
    }

    @Test
    void searchFails() throws SQLException {
        Assertions.assertThat(dbImplement.searchUser("lal", "users")).isFalse();
        Assertions.assertThat(dbImplement.searchUser("lal", DbImplement.SCORE)).isFalse();
    }

}
