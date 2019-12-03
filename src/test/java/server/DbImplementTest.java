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
        dbImplement.removeFromScore(a.getUsername());
        boolean resA = dbImplement.insertScore(a);
        Assertions.assertThat(resA).isTrue();
        Assertions.assertThat(dbImplement.removeFromScore(a.getUsername()));
        dbImplement.removeFromScore(a.getUsername());
    }

    @Test
    void searchFails() {
        Assertions.assertThat(dbImplement.searchInUsers("lal")).isFalse();
        Assertions.assertThat(dbImplement.searchInScore("lal")).isFalse();
    }

}
