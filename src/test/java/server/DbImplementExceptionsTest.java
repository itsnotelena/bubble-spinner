package server;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

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
    public void setUp() throws SQLException, FileNotFoundException {
        dbImplement = new DbImplement(Mockito.mock(DbAdapter.class));
        Mockito.when(dbImplement.getDbAdapter().getConn())
                .thenReturn(Mockito.mock(Connection.class));
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

    @Test
    void errorGettingTop5() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn()).thenThrow(new SQLException());

        List<User> list = dbImplement.getTop5Score();
        Assertions.assertThat(list.isEmpty()).isTrue();
    }

    @Test
    void errorGettingScoreByUsername() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn()).thenThrow(new SQLException());

        Assertions.assertThatThrownBy(() -> dbImplement.getScoreByUser("lol"))
                .isInstanceOf(SQLException.class);
    }

    @Test
    void errorRemovingUser() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn()).thenThrow(new SQLException());

        Assertions.assertThat(dbImplement.removeFromBadge("a")).isFalse();
    }

    @Test
    void errorGettingUserByUsername() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn()).thenThrow(new SQLException());

        Assertions.assertThatThrownBy(() -> dbImplement.getUserByUsername("baba"))
                .isInstanceOf(SQLException.class);
    }

    @Test
    void exceptionByGettingTopXScores() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn()).thenThrow(new SQLException());

        List<User> list = dbImplement.getTop5Score();
        Assertions.assertThatThrownBy(() -> dbImplement.getDbAdapter().getConn().close())
                .isInstanceOf(SQLException.class);
        Assertions.assertThat(list.isEmpty()).isTrue();
    }

    @Test
    void notGettingUserByUsername() throws SQLException, FileNotFoundException {
        dbImplement = new DbImplement(new DbAdapter(new String[]{"Test"}));

        dbImplement.removeFromUser("baka");
        Optional<User> optional = dbImplement.getUserByUsername("baka");
        Assertions.assertThat(optional.isEmpty()).isTrue();

        dbImplement = new DbImplement(Mockito.mock(DbAdapter.class));
        Mockito.when(dbImplement.getDbAdapter().getConn())
                .thenReturn(Mockito.mock(Connection.class));
    }

    @Test
    void emptyScoreByGettingScoreByUser() throws SQLException, FileNotFoundException {
        dbImplement = new DbImplement(new DbAdapter(new String[] {"Test"}));

        dbImplement.removeFromUser("naruto");
        Optional<Score> optional = dbImplement.getScoreByUser("naruto");
        Assertions.assertThat(optional.isEmpty()).isTrue();

        dbImplement = new DbImplement(Mockito.mock(DbAdapter.class));
        Mockito.when(dbImplement.getDbAdapter().getConn())
                .thenReturn(Mockito.mock(Connection.class));
    }

    @Test
    void emptyBadgesByGettingBadgesByUser() throws SQLException, FileNotFoundException {
        dbImplement = new DbImplement(new DbAdapter(new String[] {"Test"}));

        dbImplement.removeFromUser("elena");
        Optional<Badge> optional = dbImplement.getBadgeByUser("elena");
        Assertions.assertThat(optional.isEmpty()).isTrue();

        dbImplement = new DbImplement(Mockito.mock(DbAdapter.class));
        Mockito.when(dbImplement.getDbAdapter().getConn())
                .thenReturn(Mockito.mock(Connection.class)); 
    }

    @Test
    void errorGettingBadgeByUsername() throws SQLException {
        Mockito.when(dbImplement.getDbAdapter().getConn()).thenThrow(new SQLException());

        Assertions.assertThatThrownBy(() -> dbImplement.getBadgeByUser("lol"))
                .isInstanceOf(SQLException.class);
    }
}
