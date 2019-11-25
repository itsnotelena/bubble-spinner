package server;

import java.sql.Connection;
import java.sql.SQLException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class DbImplementExceptionsTest {

    private transient DbImplement dbImplement;

    /**
     * SetUp proper environment before each test.
     *
     * @throws SQLException throws SQL Exception in case
     *                      something goes from the connection class
     */
    @BeforeEach
    public void setUp() throws SQLException {
        dbImplement = new DbImplement(Mockito.mock(DbAdapter.class));
        Mockito.when(dbImplement.getDbAdapter().getConn())
                .thenReturn(Mockito.mock(Connection.class));
    }

    @Test
    void insertNullUser() {
        Assertions.assertThatThrownBy(() -> dbImplement.insertUser(null))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void checkNullUser() {
        Assertions.assertThatThrownBy(() -> dbImplement.checkLogin(null))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    void catchSqlExceptionCheckLogin() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn().prepareStatement(Mockito.any()))
                .thenThrow(Mockito.mock(SQLException.class));

        Assertions.assertThatThrownBy(() -> dbImplement.checkLogin(new User("LOL", "aaa", "aas")))
                .isInstanceOf(SQLException.class);
    }

    @Test
    void catchSqlExceptionInsertUser() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn().prepareStatement(Mockito.any()))
                .thenThrow(Mockito.mock(SQLException.class));

        Assertions.assertThatThrownBy(() -> dbImplement.insertUser(new User("LOL", "aaa", "aas")))
                .isInstanceOf(SQLException.class);
    }
}
