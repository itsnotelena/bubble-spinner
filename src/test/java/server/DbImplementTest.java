package server;

import java.io.FileNotFoundException;
import java.sql.SQLException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class DbImplementTest {
    private transient DbAdapter dbAdapter;
    private transient DbImplement dbImplement;

    @BeforeEach
    void setUp() throws FileNotFoundException {
        dbAdapter = new DbAdapter("test");
        dbAdapter.importTables();
        dbImplement = new DbImplement(dbAdapter);
        System.out.println("here");
    }

    @Test
    void insertAndCheckLogin() throws SQLException {
        User a = new User("lalalq","nsg","zal");
        dbImplement.removeUser(a.getUsername(),"users");
        boolean resA = dbImplement.insertUser(a);
        boolean res = dbImplement.checkLogin(a);
        Assertions.assertThat(res).isTrue();
        Assertions.assertThat(resA).isTrue();
        dbAdapter.getConn().close();
    }

}
